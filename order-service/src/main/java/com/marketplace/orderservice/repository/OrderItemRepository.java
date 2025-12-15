package com.marketplace.orderservice.repository;


import com.marketplace.orderservice.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;





public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}