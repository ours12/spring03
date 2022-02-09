package com.sparta.hwk05.controller;

import com.sparta.hwk05.dto.RestaurantRequestDto;
import com.sparta.hwk05.model.Restaurant;
import com.sparta.hwk05.repository.RestaurantRepository;
import com.sparta.hwk05.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class RestaurantController {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantService restaurantService;


    @PostMapping("/restaurant/register")
    public Restaurant addRestaurant(@RequestBody RestaurantRequestDto requestDto) {

        return restaurantService.addRestaurant(requestDto);
    }

    @GetMapping("/restaurants")
    public List<Restaurant> findAllRestaurant(){
        return restaurantService.findAllResataurant();
    }
}