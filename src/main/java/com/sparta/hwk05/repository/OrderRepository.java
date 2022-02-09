package com.sparta.hwk05.repository;

import com.sparta.hwk05.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository <Orders, Long>{

}
