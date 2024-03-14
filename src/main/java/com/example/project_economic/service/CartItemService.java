package com.example.project_economic.service;


import com.example.project_economic.entity.CartItemEntity;
import com.example.project_economic.response.CartItemResponse;

import java.util.List;

public interface CartItemService {
    public List<CartItemResponse>listCartItem(Long userId);
    public CartItemEntity updateCard(Long cardId,Integer quantity);
    public CartItemEntity addProduct(Long productId, Integer quantity, Long userId, String size, String color);

    public void deleteCart(Long cartId);
    Long countCart(Long userId);

    public boolean findCartByProductId(Long productId,Long userId);

    void deleteAllCartByUserId(Long userId);
}
