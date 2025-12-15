package com.marketplace.orderservice.dto;


import lombok.Data;


@Data
public class InventoryRequest {
    private Long productId;
    private int quantity;
}