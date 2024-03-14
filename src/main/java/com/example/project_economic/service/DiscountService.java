package com.example.project_economic.service;

import com.example.project_economic.entity.DiscountEntity;
import org.springframework.stereotype.Service;

@Service
public interface DiscountService {
    DiscountEntity findByName(String name);
}
