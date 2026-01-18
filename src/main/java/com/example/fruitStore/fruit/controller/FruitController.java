package com.example.fruitStore.fruit.controller;

import com.example.fruitStore.fruit.dto.FruitRequest;
import com.example.fruitStore.fruit.dto.FruitResponse;
import com.example.fruitStore.fruit.service.FruitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fruits")
@RequiredArgsConstructor
public class FruitController {

    private final FruitService fruitService;

    // ✅ USER, MANAGER, ADMIN
    @GetMapping
    public ResponseEntity<List<FruitResponse>> getAllFruits() {
        return ResponseEntity.ok(fruitService.getAllFruits());
    }

    // ✅ MANAGER, ADMIN
    @PostMapping
    public ResponseEntity<FruitResponse> createFruit(@Valid @RequestBody FruitRequest request) {
        return ResponseEntity.ok(fruitService.createFruit(request));
    }

    // ✅ MANAGER, ADMIN
    @PutMapping("/{id}")
    public ResponseEntity<FruitResponse> updateFruit(@PathVariable Long id,
                                                     @Valid @RequestBody FruitRequest request) {
        return ResponseEntity.ok(fruitService.updateFruit(id, request));
    }

    // ✅ MANAGER, ADMIN
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFruit(@PathVariable Long id) {
        fruitService.deleteFruit(id);
        return ResponseEntity.ok("Fruit deleted successfully");
    }
}

