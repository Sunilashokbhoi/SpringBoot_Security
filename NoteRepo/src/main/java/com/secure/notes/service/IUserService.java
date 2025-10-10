package com.secure.notes.service;

import com.secure.notes.dtos.UserDTO;
import com.secure.notes.model.User;

import java.util.List;

public interface IUserService {
    void updateUserRole(Long userId,String roleName);

    List<User> getALlUsers();

    UserDTO getUserById(Long id);
}
