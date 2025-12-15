package com.marketplace.inventoryservice.dto;

import lombok.Data;

@Data
public class CreateInventoryRequest {
    private Long productId;
    private int quantity;
}
