package com.example.project_economic.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "history_cards")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoryCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    private Boolean Received=false;

    private String BoughtAt;

    private Integer quantity;
    private String size;
    private String color;
    private Long discount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;



}
