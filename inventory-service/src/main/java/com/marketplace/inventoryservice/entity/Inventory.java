package com.marketplace.inventoryservice.entity;


import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "inventory")
@Data
public class Inventory {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true, nullable = false)
    private Long productId;


    private int quantity;
}