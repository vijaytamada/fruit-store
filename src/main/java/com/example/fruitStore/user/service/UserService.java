package com.example.fruitStore.user.service;

import com.example.fruitStore.user.dto.ChangePasswordRequest;
import com.example.fruitStore.user.dto.UserResponse;

import java.util.List;

public interface UserService {
    void promoteToManager(Long userId);
    void demoteToUser(Long userId);
    void deleteUserByAdmin(Long userId);
    void deleteUserByManager(Long userId);
    UserResponse getMyProfile(String email);
    void deleteMyProfile(String email);
    List<UserResponse> getAllManagers();
    void changeMyPassword(String email, ChangePasswordRequest request);
}
