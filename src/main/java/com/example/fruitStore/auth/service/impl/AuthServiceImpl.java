package com.example.fruitStore.auth.service.impl;

import com.example.fruitStore.auth.dto.AuthResponse;
import com.example.fruitStore.auth.dto.LoginRequest;
import com.example.fruitStore.auth.dto.RegisterRequest;
import com.example.fruitStore.auth.service.AuthService;
import com.example.fruitStore.config.exception.UserAlreadyExistsException;
import com.example.fruitStore.security.JwtService;
import com.example.fruitStore.user.entity.Role;
import com.example.fruitStore.user.entity.User;
import com.example.fruitStore.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public void register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("User already exists");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .role(Role.USER) // default role
                .build();

        userRepository.save(user);
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // if auth is successful -> generate token
        String token = jwtService.generateToken(request.getEmail());
        return new AuthResponse(token);
    }
}
