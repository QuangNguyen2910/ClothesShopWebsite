package com.example.project_economic.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;
import java.util.Set;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    private Long costPrice;
    private Long salePrice;
    private Integer currentQuantity;
    private Integer Likes;
    private String image;
    private String image_type;

    @Lob
    @Column(name = "data",columnDefinition = "LONGBLOB")
    private byte[] data;

    @ManyToOne(fetch =FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id",referencedColumnName = "category_id")
    private CategoryEntity categoryEntity;

    @ManyToOne(fetch =FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "seller_id",referencedColumnName = "id")
    private UserEntity userEntity;

    private Boolean is_deteted;
    private Boolean is_actived;
    }
