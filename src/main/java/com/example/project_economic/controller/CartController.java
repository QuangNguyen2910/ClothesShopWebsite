package com.example.project_economic.controller;

import com.example.project_economic.entity.CartItemEntity;
import com.example.project_economic.response.CartItemResponse;
import com.example.project_economic.service.CartItemService;
import com.example.project_economic.service.ProductService;
import com.example.project_economic.service.UserService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/shoppingcarts")
public class CartController {
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private UserService userService;

    @PostMapping("/update/{cardId}/{quantity}")
    public String updateCard(@PathVariable Long cardId
            , @PathVariable Integer quantity){
        CartItemEntity cartItemEntity= this.cartItemService.updateCard(cardId,quantity);
        Long total=cartItemEntity.totalInCartItem();
        return String.valueOf(total);
    }
    @DeleteMapping("/delete/{cartId}")
    public String deleteCart(@PathVariable Long cartId){
        this.cartItemService.deleteCart(cartId);
        return "delete success "+cartId;
    }
    @PostMapping("/add/{productId}/{quantity}/{userId}")
    public Long addProductToCart(
            @PathVariable Long productId,
            @PathVariable Integer quantity,
            @PathVariable Long userId
    ){
        CartItemEntity cartItemEntity=this.cartItemService.addProduct(productId,quantity,userId, "", "");
        List<CartItemResponse>cartItemEntities=this.cartItemService.listCartItem(userId);
        return this.cartItemService.countCart(userId);
    }
    @PostMapping("/add1/{productId}/{quantity}/{userId}")
    public Long addProductToCart(
            @PathVariable Long productId,
            @PathVariable Integer quantity,
            @PathVariable Long userId,
            @RequestParam String size,
            @RequestParam String color,
            Model model
    ){
        System.out.println(size);
        CartItemEntity cartItemEntity=this.cartItemService.addProduct(productId,quantity,userId, size, color);
        List<CartItemResponse>cartItemEntities=this.cartItemService.listCartItem(userId);
        model.addAttribute("cartItems",cartItemEntities);
        return this.cartItemService.countCart(userId);
    }
}
