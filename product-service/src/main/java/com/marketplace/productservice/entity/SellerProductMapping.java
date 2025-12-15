package com.marketplace.productservice.entity;


import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name = "seller_products")
@Data
public class SellerProductMapping {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String sellerUsername; // from X-USER header


    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}