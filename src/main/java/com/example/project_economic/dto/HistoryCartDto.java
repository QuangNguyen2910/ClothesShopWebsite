package com.example.project_economic.dto;

import com.example.project_economic.response.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoryCartDto {
    private Long id;
    private ProductResponse productResponse;
    private Boolean Received=false;

    private String BoughtAt;
    private String size;
    private String color;
    private Integer quantity;
    private Long discount;
    public Long sumTotalInCartHistory(){
        return this.quantity*this.productResponse.getSalePrice();
    }

    public String sumTotalInCartHistoryFormat(){
        Long money = (Long)(this.quantity*this.productResponse.getSalePrice());
        if(this.discount != null) money -= this.discount;
        String formattedNumber = (money) + "";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < formattedNumber.length(); i++) {
            char currentChar = formattedNumber.charAt(i);
            if (Character.isDigit(currentChar) && i > 0 && (formattedNumber.length() - i) % 3 == 0) result.append('.');
            result.append(currentChar);
        }
        return result.toString() + '₫';
    }

}
