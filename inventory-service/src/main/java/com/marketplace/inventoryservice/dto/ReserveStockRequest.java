package com.marketplace.inventoryservice.dto;


import lombok.Data;


@Data
public class ReserveStockRequest {
    private Long productId;
    private int quantity;
}