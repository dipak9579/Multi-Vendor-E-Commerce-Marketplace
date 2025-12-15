package com.marketplace.orderservice.dto;


import lombok.Data;


@Data
public class OrderItemRequest {
    private Long productId;
    private int quantity;
    private double price;
}