package com.example.project_economic.impl;

import com.example.project_economic.entity.DiscountEntity;
import com.example.project_economic.entity.ProductEntity;
import com.example.project_economic.repository.DiscountRepository;
import com.example.project_economic.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

@Service
public class DiscountImpl implements DiscountService {
    @Autowired
    private DiscountRepository discountRepository;
    @Override
    public DiscountEntity findByName(String name){
        Optional<DiscountEntity> optional = discountRepository.findByName(name);
        if(optional.isPresent()){
            DiscountEntity discountEntity = optional.get();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate expiryDate = LocalDate.parse(discountEntity.getExpiryDate(), dateTimeFormatter);
            if(LocalDate.now().isBefore(expiryDate)) return discountEntity;
            else return new DiscountEntity();
        }
        else return null;
    }
}
