package com.example.project_economic.impl;

import com.example.project_economic.entity.CartItemEntity;
import com.example.project_economic.entity.HistoryCard;
import com.example.project_economic.entity.UserEntity;
import com.example.project_economic.repository.CartItemRepository;
import com.example.project_economic.repository.UserRepository;
import com.example.project_economic.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
public class EmailSenderImpl implements EmailSenderService {
    @Autowired
    private final JavaMailSender mailSender;
    @Autowired
    private final CartItemRepository cartItemRepository;
    @Autowired
    private final UserRepository userRepository;

    public EmailSenderImpl(JavaMailSender mailSender, CartItemRepository cartItemRepository, UserRepository userRepository){
        this.mailSender = mailSender;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
    }
    @Override
    public String sendEmail(Long userId){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("viethunghoang1508@gmail.com");

        UserEntity user=this.userRepository.findById(userId).get();
        message.setTo(user.getEmail());
        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("HH:mm:ss,dd/MM/yyyy");
        String subject = "";
        String body = "";
        Long totalMoney = 0l;
        List<CartItemEntity> cartItemEntities=this.cartItemRepository.findByUser(user);
        for (CartItemEntity cartItem:cartItemEntities) {
            HistoryCard historyCard=HistoryCard.builder()
                    .quantity(cartItem.getQuantity())
                    .product(cartItem.getProduct())
                    .Received(false)
                    .BoughtAt(dateTimeFormatter.format(LocalDateTime.now()))
                    .color(cartItem.getColor())
                    .size(cartItem.getSize())
                    .user(user)
                    .build();
            subject = "Đơn hàng mới của " + user.getUsername() + " " + "vào ngày " + historyCard.getBoughtAt();
            body += "Tên sản phẩm: " + historyCard.getProduct().getName() + "\n";
            body += "Ngày đặt: " + historyCard.getBoughtAt() + "\n";
            body += "Số lượng: " + historyCard.getQuantity() + "\n";
            body += "Kích thước: " + historyCard.getSize() + "\n";
            body += "Màu sắc: " + historyCard.getColor() + "\n";
            body += "Số tiền: " + (historyCard.getDiscount() != null ? historyCard.getProduct().getSalePrice() - historyCard.getDiscount() : historyCard.getProduct().getSalePrice()) + "₫\n\n";
            totalMoney += cartItem.totalInCartItem();
        }
        if(totalMoney == 0) return "Không có sản phẩm trong giỏ hàng";
        body += "Tổng số tiền bạn phải thanh toán là: " + totalMoney + "₫\n";
        body += "Địa chỉ nhận hàng: " + user.getAddress() + "\n";
        body += "Số điện thoại người nhận: " + user.getPhoneNumber() + "\n";
                message.setText(body);
                message.setSubject(subject);

                mailSender.send(message);
                return "Gửi mail thành công";
                }

    }

