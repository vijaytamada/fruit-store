package com.example.fruitStore.user.service.impl;

import com.example.fruitStore.user.dto.UserResponse;
import com.example.fruitStore.user.entity.Role;
import com.example.fruitStore.user.entity.User;
import com.example.fruitStore.user.repository.UserRepository;
import com.example.fruitStore.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.fruitStore.user.dto.ChangePasswordRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void promoteToManager(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() == Role.ADMIN) {
            throw new RuntimeException("Admin role cannot be changed");
        }

        user.setRole(Role.MANAGER);
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void demoteToUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() == Role.ADMIN) {
            throw new RuntimeException("Admin role cannot be changed");
        }

        user.setRole(Role.USER);
        userRepository.save(user);
    }

    // ✅ ADMIN can delete USER or MANAGER (not ADMIN)
    @Transactional
    @Override
    public void deleteUserByAdmin(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() == Role.ADMIN) {
            throw new RuntimeException("Admin cannot be deleted!");
        }

        userRepository.delete(user);
    }

    // ✅ MANAGER can delete only USER
    @Transactional
    @Override
    public void deleteUserByManager(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() != Role.USER) {
            throw new RuntimeException("Manager can delete only normal users!");
        }

        userRepository.delete(user);
    }

    @Override
    public UserResponse getMyProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserResponse(user.getId(), user.getEmail(), user.getRole());
    }

    @Override
    @Transactional
    public void deleteMyProfile(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getRole() == Role.ADMIN) {
            throw new RuntimeException("Admin cannot delete own profile");
        }

        userRepository.delete(user);
    }

    @Override
    public List<UserResponse> getAllManagers() {
        return userRepository.findAllByRole(Role.MANAGER)
                .stream()
                .map(u -> new UserResponse(u.getId(), u.getEmail(), u.getRole()))
                .toList();
    }

    @Override
    @Transactional
    public void changeMyPassword(String email, ChangePasswordRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // check old password
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }

        // update new password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}
