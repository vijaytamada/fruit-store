package com.example.fruitStore.fruit.repository;
import com.example.fruitStore.fruit.entity.Fruit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FruitRepository extends JpaRepository<Fruit, Long> {
    Optional<Fruit> findByName(String name);
    boolean existsByName(String name);
}

