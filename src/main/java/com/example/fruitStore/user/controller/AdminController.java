package com.example.fruitStore.user.controller;

import com.example.fruitStore.user.dto.UserResponse;
import com.example.fruitStore.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @PutMapping("/users/{id}/promote-to-manager")
    public ResponseEntity<String> promoteToManager(@PathVariable Long id) {
        userService.promoteToManager(id);
        return ResponseEntity.ok("User promoted to MANAGER");
    }

    @PutMapping("/users/{id}/demote-to-user")
    public ResponseEntity<String> demoteToUser(@PathVariable Long id) {
        userService.demoteToUser(id);
        return ResponseEntity.ok("Manager demoted to USER");
    }

    // ✅ ADMIN delete USER or MANAGER
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUserByAdmin(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    // Fetch all users - managers
    @GetMapping("/managers")
    public ResponseEntity<List<UserResponse>> getAllManagers() {
        return ResponseEntity.ok(userService.getAllManagers());
    }
}

