package com.sparta.hwk05.service;

import com.sparta.hwk05.dto.RestaurantRequestDto;
import com.sparta.hwk05.model.Restaurant;
import com.sparta.hwk05.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public Restaurant addRestaurant(RestaurantRequestDto requestDto) {
        int minOrderPrice = requestDto.getMinOrderPrice();
        int deliveryFee = requestDto.getDeliveryFee();
        if(!(1000 <= minOrderPrice && minOrderPrice <= 100000)){
            throw new IllegalArgumentException("최소주문 허용 가격을 벗어났습니다.");
        }
        if(!(minOrderPrice % 100 == 0)){
            throw new IllegalArgumentException("100원 단위로 입력하지 않았습니다.");
        }
        if(!(0 <= deliveryFee && deliveryFee <= 10000)){
            throw new IllegalArgumentException("기본 배달비 허용가격을 벗어났습니다.");
        }
        if(!( deliveryFee % 500 == 0)){
            throw new IllegalArgumentException("기본 배달비 500원 단위로 입력하지 않았습니다.");
        }
        Restaurant restaurant = Restaurant.builder()
                .name(requestDto.getName())
                .minOrderPrice(minOrderPrice)
                .deliveryFee(requestDto.getDeliveryFee())
                .build();

        restaurantRepository.save(restaurant);
        return restaurant;
    }

    @Transactional
    public List<Restaurant> findAllResataurant(){
        return restaurantRepository.findAll();
    }
}
