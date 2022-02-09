package com.sparta.hwk05.dto;

import com.sparta.hwk05.model.OrderItem;
import lombok.Getter;

import java.util.List;

@Getter
public class OrdersRequestDto {
    private Long restaurantId;
    private List<OrderItem> foods;

}
