package com.sparta.hwk05.service;

import com.sparta.hwk05.dto.FoodRequestDto;
import com.sparta.hwk05.model.Food;
import com.sparta.hwk05.model.Restaurant;
import com.sparta.hwk05.repository.FoodRepository;
import com.sparta.hwk05.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public void addRestaurantFood(Long restaurantId, List<FoodRequestDto> requestDtoList){
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElse(null);
        if(restaurant == null){
            throw new IllegalArgumentException("해당 음식점이 없습니다.");
        }

        for(FoodRequestDto requestDto : requestDtoList){
            int price = requestDto.getPrice();
            if(!(100 < price && price < 1_000_000)){
                throw new IllegalArgumentException("음식 가격이 허용값이 아닙니다.");
            }
            if(!( price % 100 == 0)) {
                throw  new IllegalArgumentException("100원 단위로만 입력이 가능합니다.");
            }
            Optional<Food> found = foodRepository.findFoodByRestaurantAndName(restaurant, requestDto.getName());
            if(found.isPresent()){
                throw new IllegalArgumentException("중복된 이름의 음식이 존재합니다.");
            }
            Food food = new Food(requestDto,restaurant);
            foodRepository.save(food);
        }
    }

    @Transactional
    public List<Food> findAllRestaurantFoods(Long restaurantId){
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(
                        () -> new NullPointerException("해당 음식점이 없습니다."));
        return foodRepository.findFoodsByRestaurant(restaurant);
    }

}
