package com.secure.notes.service;

import com.secure.notes.dtos.UserDTO;
import com.secure.notes.model.AppRole;
import com.secure.notes.model.PasswordResetToken;
import com.secure.notes.model.Role;
import com.secure.notes.model.User;
import com.secure.notes.respositories.PasswordResetTokenReposiroty;
import com.secure.notes.respositories.RoleRepository;
import com.secure.notes.respositories.UserRepository;
import com.secure.notes.util.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class  UserServiceImpl implements IUserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordResetTokenReposiroty passwordResetTokenReposiroty;

    @Autowired
    EmailService emailService;

    @Value("${frontend.url}")
    private String frontendUrl;

    @Override
    public void updateUserRole(Long userId, String roleName) {
        User user = userRepository.findById(userId).orElseThrow(()
                -> new RuntimeException("User not found"));
        AppRole appRole = AppRole.valueOf(roleName);
        Role role = roleRepository.findByRoleName(appRole)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);
        userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDTO getUserById(Long id) {
//        return userRepository.findById(id).orElseThrow();
        User user = userRepository.findById(id).orElseThrow();
        return convertToDto(user);
    }

    @Override
    public User findByUsername(String username) {
        Optional<User> user = userRepository.findByUserName(username);
        return user.orElseThrow(()-> new RuntimeException("User Not found with username: "+ username));
    }
    private UserDTO convertToDto(User user) {
        return new UserDTO(
                user.getUserId(),
                user.getUserName(),
                user.getEmail(),
                user.isAccountNonLocked(),
                user.isAccountNonExpired(),
                user.isCredentialsNonExpired(),
                user.isEnabled(),
                user.getCredentialsExpiryDate(),
                user.getAccountExpiryDate(),
                user.getTwoFactorSecret(),
                user.isTwoFactorEnabled(),
                user.getSignUpMethod(),
                user.getRole(),
                user.getCreatedDate(),
                user.getUpdatedDate()
        );
    }

    @Override
    public void updateAccountLockStatus(Long userId, boolean lock)
    {
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found "));
        user.setAccountNonLocked(!lock);
        userRepository.save(user);
    }

    @Override
    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }

    @Override
    public void updateAccountExpiryStatus(Long userId, boolean expire)
    {
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        user.setAccountNonExpired(!expire);
        userRepository.save(user);
    }

    @Override
    public void updateAccountEnabledStatus(Long userId, boolean enabled){
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found"));
        user.setEnabled(enabled);
        userRepository.save(user);
    }

    @Override
    public void updateCredentialsExpiryStatus(Long userId, boolean expire){
        User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found"));
        user.setCredentialsNonExpired(!expire);
        userRepository.save(user);
    }

    @Override
    public void updatePassword(Long userId, String password) {
        try {
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found "));
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
        }catch (Exception e){
            throw new RuntimeException("Failed to update password");
        }
    }

    @Override
    public void generatePasswordResetToken(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not found "));

        String token = UUID.randomUUID().toString();
        Instant expiryDate = Instant.now().plus(24, ChronoUnit.HOURS);
        PasswordResetToken resetToken = new PasswordResetToken(token ,expiryDate ,user);
        passwordResetTokenReposiroty.save(resetToken);

        String restUrl = frontendUrl+"/reset-password?token="+token;
        //sent email to user
        emailService.sendPasswordResetEmail(user.getEmail(),restUrl);

    }



}
