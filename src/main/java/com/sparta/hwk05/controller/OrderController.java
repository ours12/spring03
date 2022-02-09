package com.sparta.hwk05.controller;

import com.sparta.hwk05.dto.OrdersRequestDto;
import com.sparta.hwk05.dto.OrdersResponseDto;
import com.sparta.hwk05.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/order/request")
    public OrdersResponseDto orderfood(@RequestBody OrdersRequestDto ordersRequestDto){
        return orderService.order(ordersRequestDto);
    }

    @GetMapping("/orders")
    public List<OrdersResponseDto> findAllOrder(){
        return orderService.findAllOrder();
    }
}
