package com.marketplace.sellerservice.entity;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;


@Entity
@Table(name = "seller")
@Data
public class Seller {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true, nullable = false)
    private String username; // from JWT / Gateway


    private String businessName;
    private String email;
    private String phone;


    @Enumerated(EnumType.STRING)
    private SellerStatus status = SellerStatus.PENDING;


    private LocalDateTime createdAt = LocalDateTime.now();
}