package com.example.fruitStore.auth.repository;

import com.example.fruitStore.auth.entity.PasswordResetToken;
import com.example.fruitStore.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByTokenHash(String tokenHash);
    void deleteAllByUser(User user);
}

