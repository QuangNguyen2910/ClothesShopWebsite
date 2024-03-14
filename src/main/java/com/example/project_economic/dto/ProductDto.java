package com.example.project_economic.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private Long costPrice;
    private Long salePrice;
    private Integer currentQuantity;
    private Integer likes;
    private String image;
    private Long  categoryId;
    private Boolean is_deteted;
    private Boolean is_actived;
    private Long sellerId;
}
