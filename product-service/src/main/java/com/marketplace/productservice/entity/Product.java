package com.marketplace.productservice.entity;


import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;


@Entity
@Table(name = "products")
@Data
public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;
    private String description;
    private BigDecimal price;


    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}