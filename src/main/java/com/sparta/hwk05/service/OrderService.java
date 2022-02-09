package com.sparta.hwk05.service;

import com.sparta.hwk05.dto.FoodsResponseDto;
import com.sparta.hwk05.dto.OrdersRequestDto;
import com.sparta.hwk05.dto.OrdersResponseDto;
import com.sparta.hwk05.model.Food;
import com.sparta.hwk05.model.OrderItem;
import com.sparta.hwk05.model.Orders;
import com.sparta.hwk05.model.Restaurant;
import com.sparta.hwk05.repository.FoodRepository;
import com.sparta.hwk05.repository.OrderItemRepository;
import com.sparta.hwk05.repository.OrderRepository;
import com.sparta.hwk05.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodRepository foodRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public OrdersResponseDto order(OrdersRequestDto ordersRequestDto){
        Restaurant restaurant = restaurantRepository.findById(ordersRequestDto.getRestaurantId())
                .orElseThrow(
                        () -> new NullPointerException("해당 음식점이 없습니다.")
                );
        int totalPrice = 0;
        List<FoodsResponseDto> foodsResponseDtoList = new ArrayList<>();
        List<OrderItem> orderItems = ordersRequestDto.getFoods();
        List<OrderItem> orderItemList = new ArrayList<>();
        for(OrderItem tempOrderItem : orderItems){

            int quantity = tempOrderItem.getQuantity();
            if(quantity < 1 || quantity > 100){
                throw new IllegalArgumentException("음식 수량이 허용값이 아닙니다");
            }
            Food food = foodRepository.findById(tempOrderItem.getId())
                    .orElseThrow(
                            () -> new NullPointerException("해당 음식이 없습니다.")
                    );

            OrderItem orderItem = OrderItem.builder()
                    .quantity(tempOrderItem.getQuantity())
                    .name(food.getName())
                    .price(food.getPrice() * quantity)
                    .food(food)
                    .build();
            orderItemRepository.save(orderItem);
            FoodsResponseDto foodsResponseDto = new FoodsResponseDto(orderItem);
            foodsResponseDtoList.add(foodsResponseDto);
            totalPrice += food.getPrice() * quantity;
            orderItemList.add(orderItem);
        }
        if(totalPrice < restaurant.getMinOrderPrice()){
            throw new IllegalArgumentException("최소 주문 가격을 넘지 않았습니다.");
        }
        int deliveryFee = restaurant.getDeliveryFee();
        totalPrice += deliveryFee;
        Orders orders = new Orders(restaurant.getName(), totalPrice, orderItemList);
        orderRepository.save(orders);
        return new OrdersResponseDto(orders, foodsResponseDtoList, deliveryFee);
    }
    @Transactional
    public List<OrdersResponseDto> findAllOrder(){
        List<OrdersResponseDto> ordersResponseDtoList = new ArrayList<>();

        List<Orders> ordersList = orderRepository.findAll();

        for(Orders orders : ordersList){
            int deliveryFee = restaurantRepository.findByName(orders.getRestaurantName()).getDeliveryFee();
            List<FoodsResponseDto> foodsResponseDtoList = new ArrayList<>();

            List<OrderItem> orderItemsList = orderItemRepository.findOrderItemsByOrders(orders);
            for(OrderItem orderItem : orderItemsList){
                FoodsResponseDto foodsResponseDto = new FoodsResponseDto(orderItem);
                foodsResponseDtoList.add(foodsResponseDto);
            }
            OrdersResponseDto ordersResponseDto = new OrdersResponseDto(orders, foodsResponseDtoList, deliveryFee);
            ordersResponseDtoList.add(ordersResponseDto);
        }
        return ordersResponseDtoList;
    }

}
