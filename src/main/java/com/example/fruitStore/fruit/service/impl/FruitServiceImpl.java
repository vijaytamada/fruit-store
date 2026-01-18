package com.example.fruitStore.fruit.service.impl;

import com.example.fruitStore.fruit.dto.FruitRequest;
import com.example.fruitStore.fruit.dto.FruitResponse;
import com.example.fruitStore.fruit.entity.Fruit;
import com.example.fruitStore.fruit.repository.FruitRepository;
import com.example.fruitStore.fruit.service.FruitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FruitServiceImpl implements FruitService {

    private final FruitRepository fruitRepository;

    @Override
    public FruitResponse createFruit(FruitRequest request) {

        if (fruitRepository.existsByName(request.getName())) {
            throw new RuntimeException("Fruit already exists");
        }

        Fruit fruit = Fruit.builder()
                .name(request.getName())
                .quantity(request.getQuantity())
                .build();

        Fruit saved = fruitRepository.save(fruit);

        return new FruitResponse(saved.getId(), saved.getName(), saved.getQuantity());
    }

    @Override
    public List<FruitResponse> getAllFruits() {
        return fruitRepository.findAll()
                .stream()
                .map(f -> new FruitResponse(f.getId(), f.getName(), f.getQuantity()))
                .toList();
    }

    @Override
    public FruitResponse updateFruit(Long id, FruitRequest request) {

        Fruit fruit = fruitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fruit not found"));

        fruit.setName(request.getName());
        fruit.setQuantity(request.getQuantity());

        Fruit saved = fruitRepository.save(fruit);

        return new FruitResponse(saved.getId(), saved.getName(), saved.getQuantity());
    }

    @Override
    public void deleteFruit(Long id) {
        Fruit fruit = fruitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fruit not found"));

        fruitRepository.delete(fruit);
    }
}
