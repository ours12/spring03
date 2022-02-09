package com.sparta.hwk05.service;

import com.sparta.hwk05.repository.FoodRepository;
import com.sparta.hwk05.repository.OrderItemRepository;
import com.sparta.hwk05.repository.OrderRepository;
import com.sparta.hwk05.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodRepository foodRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    

}
