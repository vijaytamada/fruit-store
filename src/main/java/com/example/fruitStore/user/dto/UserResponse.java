package com.example.fruitStore.user.dto;

import com.example.fruitStore.user.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String email;
    private Role role;
}
