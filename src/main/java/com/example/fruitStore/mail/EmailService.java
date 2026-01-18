package com.example.fruitStore.mail;

public interface EmailService {
    void sendPasswordResetEmail(String toEmail, String resetLink);
}
