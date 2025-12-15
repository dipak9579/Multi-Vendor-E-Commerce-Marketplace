package com.marketplace.cartservice.repository;


import com.marketplace.cartservice.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}