package com.example.fruitStore.fruit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FruitResponse {
    private Long id;
    private String name;
    private Integer quantity;
}

