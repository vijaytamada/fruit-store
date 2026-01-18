package com.example.fruitStore.auth.service;

import com.example.fruitStore.auth.dto.AuthResponse;
import com.example.fruitStore.auth.dto.LoginRequest;
import com.example.fruitStore.auth.dto.RegisterRequest;

public interface AuthService {
    void register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
