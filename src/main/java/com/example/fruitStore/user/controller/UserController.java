package com.example.fruitStore.user.controller;

import com.example.fruitStore.user.dto.UserResponse;
import com.example.fruitStore.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.example.fruitStore.user.dto.ChangePasswordRequest;
import jakarta.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    // ✅ Get my profile
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMyProfile(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(userService.getMyProfile(email));
    }

    // ✅ Delete my profile
    @DeleteMapping("/me")
    public ResponseEntity<String> deleteMyProfile(Authentication authentication) {
        String email = authentication.getName();
        userService.deleteMyProfile(email);
        return ResponseEntity.ok("Profile deleted successfully");
    }

    @PutMapping("/me/password")
    public ResponseEntity<String> changePassword(Authentication authentication,
                                                 @Valid @RequestBody ChangePasswordRequest request) {
        String email = authentication.getName();
        userService.changeMyPassword(email, request);
        return ResponseEntity.ok("Password updated successfully");
    }
}
