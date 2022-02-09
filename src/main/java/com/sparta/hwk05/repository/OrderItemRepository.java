package com.sparta.hwk05.repository;

import com.sparta.hwk05.model.OrderItem;
import com.sparta.hwk05.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findOrderItemsByOrders(Orders orders);
}
