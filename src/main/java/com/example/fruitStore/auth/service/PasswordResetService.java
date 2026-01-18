package com.example.fruitStore.auth.service;

import com.example.fruitStore.auth.dto.ForgotPasswordRequest;
import com.example.fruitStore.auth.dto.ResetPasswordRequest;

public interface PasswordResetService {
    void forgotPassword(ForgotPasswordRequest request);
    void resetPassword(ResetPasswordRequest request);
}

