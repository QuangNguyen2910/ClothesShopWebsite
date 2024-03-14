package com.example.project_economic.repository;

import com.example.project_economic.entity.DiscountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiscountRepository extends JpaRepository<DiscountEntity,Long> {
    Optional<DiscountEntity> findByName(String name);
}
