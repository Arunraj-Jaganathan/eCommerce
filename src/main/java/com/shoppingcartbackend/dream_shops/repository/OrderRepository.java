package com.shoppingcartbackend.dream_shops.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shoppingcartbackend.dream_shops.model.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}
