package com.sparta.hwk05.controller;

import com.sparta.hwk05.dto.FoodRequestDto;
import com.sparta.hwk05.model.Food;
import com.sparta.hwk05.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FoodController {
    private final FoodService foodService;

    //해당 레스토랑에 음식 추가
    @PostMapping("/restaurant/{restaurantId}/food/register")
    public void addRestaurantFood(
            @PathVariable Long restaurantId,
            @RequestBody List<FoodRequestDto> requestDtoList){
        foodService.addRestaurantFood(restaurantId, requestDtoList);
    }

    //해당 레스토랑에 모든 음식 조회
    @GetMapping("/restaurant/{restaurantId}/foods")
    public List<Food> findAllRestaurantFoods(@PathVariable Long restaurantId){
        return foodService.findAllRestaurantFoods(restaurantId);
    }

}
