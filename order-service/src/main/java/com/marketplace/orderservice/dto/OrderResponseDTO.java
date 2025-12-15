package com.marketplace.orderservice.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDTO {

    private Long id;
    private String username;
    private String status;
    private double totalAmount;
    private LocalDateTime createdAt;
    private List<OrderItemResponseDTO> items;
}
