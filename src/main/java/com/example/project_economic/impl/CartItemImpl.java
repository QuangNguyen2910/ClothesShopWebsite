package com.example.project_economic.impl;

import com.example.project_economic.entity.CartItemEntity;
import com.example.project_economic.entity.ProductEntity;
import com.example.project_economic.entity.UserEntity;
import com.example.project_economic.repository.CartItemRepository;
import com.example.project_economic.repository.ProductRepository;
import com.example.project_economic.repository.UserRepository;
import com.example.project_economic.response.CartItemResponse;
import com.example.project_economic.service.CartItemService;
import com.example.project_economic.utils.ProductUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartItemImpl implements CartItemService {
    private final ProductUtils productUtils;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartItemImpl(ProductUtils productUtils, CartItemRepository cartItemRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.productUtils = productUtils;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<CartItemResponse> listCartItem(Long userId) {
        UserEntity user=this.userRepository.findById(userId).get();
        List<CartItemEntity>cartItemEntities=this.cartItemRepository.findByUser(user);
        List<CartItemResponse>cartItemResponses=cartItemEntities.stream()
                .map(cartITemEntity->{
                    CartItemResponse cartItemResponse=new CartItemResponse();
                    cartItemResponse.setId(cartITemEntity.getId());
                    cartItemResponse.setProductResponse(productUtils.enity_to_response(cartITemEntity.getProduct()));
                    cartItemResponse.setQuantity(cartITemEntity.getQuantity());
                    cartItemResponse.setUser(cartITemEntity.getUser());
                    cartItemResponse.setSize(cartITemEntity.getSize());
                    cartItemResponse.setColor(cartITemEntity.getColor());
                    return cartItemResponse;
                }).collect(Collectors.toList());

        return cartItemResponses;
    }

    @Override
    public CartItemEntity updateCard(Long cardId, Integer quantity) {
        Optional<CartItemEntity> cartItemEntityP =this.cartItemRepository.findById(cardId);
        if (cartItemEntityP.isPresent()){
            CartItemEntity cartItemEntity = cartItemEntityP.get();
            cartItemEntity.setQuantity(quantity);
            return this.cartItemRepository.save(cartItemEntity);
        }
        return null;
    }

    @Override
    public CartItemEntity addProduct(Long productId, Integer quantity, Long userId, String size, String color) {
        UserEntity user=this.userRepository.findById(userId).get();
        ProductEntity product=this.productRepository.findById(productId).get();
        CartItemEntity cartItemEntity=this.cartItemRepository.findByUserAndProductAttribute(user.getId(),product.getId(), size, color);
        if(cartItemEntity==null){
            CartItemEntity cartItemEntity1=new CartItemEntity();
            cartItemEntity1.setQuantity(quantity);
            cartItemEntity1.setProduct(product);
            cartItemEntity1.setUser(user);
            cartItemEntity1.setSize(size);
            cartItemEntity1.setColor(color);
            return this.cartItemRepository.save(cartItemEntity1);

        }
        else{
            int quantityNew=cartItemEntity.getQuantity()+quantity;
            cartItemEntity.setQuantity(quantityNew);
        }
        return this.cartItemRepository.save(cartItemEntity);
    }

    @Override
    public void deleteCart(Long cartId) {
        this.cartItemRepository.deleteById(cartId);
    }

    @Override
    public Long countCart(Long userId) {
        return this.cartItemRepository.countCart(userId);
    }

    @Override
    public boolean findCartByProductId(Long productId,Long userId) {
        Long cnt=this.cartItemRepository.countCartByProductIdAndUserId(productId,userId);
        if(cnt==0)return false;
        return true;
    }

    @Override
    public void deleteAllCartByUserId(Long userId) {
//        UserEntity user=this.userRepository.findById(userId).get();
        this.cartItemRepository.deleteAllCartByUserId(userId);
    }
}
