package com.example.fruitStore.auth.service.impl;

import com.example.fruitStore.auth.dto.ForgotPasswordRequest;
import com.example.fruitStore.auth.dto.ResetPasswordRequest;
import com.example.fruitStore.auth.entity.PasswordResetToken;
import com.example.fruitStore.auth.repository.PasswordResetTokenRepository;
import com.example.fruitStore.auth.service.PasswordResetService;
import com.example.fruitStore.mail.EmailService;
import com.example.fruitStore.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.HexFormat;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Value("${app.reset-password.base-url:http://localhost:3000/reset-password}")
    private String resetBaseUrl;

    @Transactional
    @Override
    public void forgotPassword(ForgotPasswordRequest request) {

        var userOpt = userRepository.findByEmail(request.getEmail());

        // ✅ Always return success from controller
        if (userOpt.isEmpty()) {
            return;
        }

        var user = userOpt.get();

        // ✅ delete old tokens
        tokenRepository.deleteAllByUser(user);

        // ✅ generate raw token
        String rawToken = UUID.randomUUID().toString().replace("-", "")
                + UUID.randomUUID().toString().replace("-", "");

        // ✅ store hash only
        String tokenHash = sha256(rawToken);

        PasswordResetToken token = PasswordResetToken.builder()
                .tokenHash(tokenHash)
                .user(user)
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .used(false)
                .build();

        tokenRepository.save(token);

        String resetLink = resetBaseUrl + "?token=" + rawToken;
        emailService.sendPasswordResetEmail(user.getEmail(), resetLink);
    }

    @Transactional
    @Override
    public void resetPassword(ResetPasswordRequest request) {

        String tokenHash = sha256(request.getToken());

        PasswordResetToken token = tokenRepository.findByTokenHash(tokenHash)
                .orElseThrow(() -> new RuntimeException("Invalid reset token"));

        if (token.isUsed()) {
            throw new RuntimeException("Reset token already used");
        }

        if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Reset token expired");
        }

        var user = token.getUser();

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        token.setUsed(true);
        tokenRepository.save(token);
    }

    private String sha256(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(value.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hashBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error hashing token");
        }
    }
}
