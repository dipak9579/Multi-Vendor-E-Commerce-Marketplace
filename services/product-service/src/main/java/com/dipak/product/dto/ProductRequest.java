package com.dipak.product.dto;

import lombok.Data;

@Data
public class ProductRequest {
    private String title;
    private String description;
    private Double price;
    private String brand;
    private Long categoryId;
    private Long sellerId;
    private int initialStock;
}
