package com.example.project_economic.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cart_items")
public class CartItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String size;
    private String color;
    private int quantity;
    public Long totalInCartItem(){
        return this.quantity*this.getProduct().getSalePrice();
    }
}
