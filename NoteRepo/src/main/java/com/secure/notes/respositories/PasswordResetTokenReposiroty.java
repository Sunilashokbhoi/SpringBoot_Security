package com.secure.notes.respositories;

import com.secure.notes.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenReposiroty extends JpaRepository<PasswordResetToken,Long> {
}
