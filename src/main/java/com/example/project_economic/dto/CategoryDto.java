package com.example.project_economic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDto {
    private Long categoryId;
    private String categoryName;
    private Long numberOfProduct;
}
