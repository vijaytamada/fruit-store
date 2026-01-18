package com.example.fruitStore.fruit.service;

import com.example.fruitStore.fruit.dto.FruitRequest;
import com.example.fruitStore.fruit.dto.FruitResponse;

import java.util.List;

public interface FruitService {
    FruitResponse createFruit(FruitRequest request);
    List<FruitResponse> getAllFruits();
    FruitResponse updateFruit(Long id, FruitRequest request);
    void deleteFruit(Long id);
}

