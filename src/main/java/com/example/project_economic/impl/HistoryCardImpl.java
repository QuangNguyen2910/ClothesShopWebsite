package com.example.project_economic.impl;

import com.example.project_economic.dto.HistoryCartDto;
import com.example.project_economic.entity.CartItemEntity;
import com.example.project_economic.entity.HistoryCard;
import com.example.project_economic.entity.UserEntity;
import com.example.project_economic.repository.CartItemRepository;
import com.example.project_economic.repository.HistoryCardRepository;
import com.example.project_economic.repository.ProductRepository;
import com.example.project_economic.repository.UserRepository;
import com.example.project_economic.service.HistoryCardService;
import com.example.project_economic.utils.ProductUtils;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoryCardImpl implements HistoryCardService {
    private final HistoryCardRepository historyCardRepository;
    private final CartItemRepository cartItemRepository;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final ProductUtils productUtils;

    public HistoryCardImpl(HistoryCardRepository historyCardRepository, CartItemRepository cartItemRepository, UserRepository userRepository, ProductRepository productRepository, ProductUtils productUtils) {
        this.historyCardRepository = historyCardRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.productUtils = productUtils;
    }


    @Override
    public void addProductToHistoryCard(Long userId, Long discount){
        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("HH:mm:ss,dd/MM/yyyy");

        UserEntity user=this.userRepository.findById(userId).get();
        List<CartItemEntity> cartItemEntities=this.cartItemRepository.findByUser(user);
        Long n = (Long)(discount/cartItemEntities.size());
        for (CartItemEntity cartItem:cartItemEntities) {
            HistoryCard historyCard=HistoryCard.builder()
                    .quantity(cartItem.getQuantity())
                    .product(cartItem.getProduct())
                    .Received(false)
                    .BoughtAt(dateTimeFormatter.format(LocalDateTime.now()))
                    .user(user)
                    .size(cartItem.getSize())
                    .color(cartItem.getColor())
                    .discount(n)
                    .build();
            this.historyCardRepository.save(historyCard);
        }
    }
    @Override
    public List<HistoryCartDto> findByUserId(Long userId) {
        List<HistoryCard> historyCards=this.historyCardRepository.findByUser(
                this.userRepository.findById(userId).get()
        );
        List<HistoryCartDto> historyCartDtos=historyCards.stream().map((cart)->{
            return HistoryCartDto.builder()
                    .id(cart.getId())
                    .Received(cart.getReceived())
                    .BoughtAt(cart.getBoughtAt())
                    .quantity(cart.getQuantity())
                    .size(cart.getSize())
                    .color(cart.getColor())
                    .productResponse(this.productUtils.enity_to_response(cart.getProduct()))
                    .discount(cart.getDiscount())
                    .build();
        }).collect(Collectors.toList());
        return historyCartDtos;
    }
}
