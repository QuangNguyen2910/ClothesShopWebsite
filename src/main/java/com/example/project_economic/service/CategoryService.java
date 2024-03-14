package com.example.project_economic.service;

import com.example.project_economic.dto.CategoryDto;
import com.example.project_economic.entity.CategoryEntity;

import java.util.List;

public interface CategoryService {
    //admin
    List<CategoryEntity>findAll();
    CategoryEntity save(CategoryEntity categoryEntity);
    CategoryEntity findById(Long id);
    CategoryEntity update(CategoryEntity categoryEntity,Long id);
    void deleteById(Long id);
    void enableById(Long id);
    List<CategoryEntity>findAllByActived();
    //customer
    List<CategoryDto>getCategoryAndProduct();
}
