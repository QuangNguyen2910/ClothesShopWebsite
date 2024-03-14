package com.example.project_economic.controller;

import com.example.project_economic.entity.CartItemEntity;
import com.example.project_economic.entity.DiscountEntity;
import com.example.project_economic.entity.UserEntity;
import com.example.project_economic.response.CartItemResponse;
import com.example.project_economic.service.CartItemService;
import com.example.project_economic.service.DiscountService;
import com.example.project_economic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/api/carts")
public class ShoppingCartController {
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private UserService userService;
    @Autowired
    private DiscountService discountService;
    @GetMapping("/{userId}")
    public String showShoppingCart(Model model,
            @PathVariable Long userId
            , @AuthenticationPrincipal Authentication authentication){
        List<CartItemResponse>cartItemEntities=this.cartItemService.listCartItem(userId);
        UserEntity userEntity = userService.findUserById(userId);
        model.addAttribute("user", userEntity);
        model.addAttribute("cartItems",cartItemEntities);
        return "home/cart";
    }
    @PostMapping("/{discountName}")
    @ResponseBody
    public Long useDiscount(@PathVariable String discountName, Model model){
        try{
            DiscountEntity discountEntity = this.discountService.findByName(discountName.toLowerCase());
            if(discountEntity == null){
                return 0l;
            }
            else{
                model.addAttribute("accept", "");
                return discountEntity.getMoney();
            }
        }
        catch (Exception e){
            return 0l;
        }
    }
}
