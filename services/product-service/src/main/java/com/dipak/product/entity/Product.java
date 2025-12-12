package com.dipak.product.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;
    @Column(length = 2000)
    private String description;
    @Column(nullable = false)
    private Double price;
    private String brand;
    private String imagePath; // local file path or S3 key
    private Double averageRating;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private Long sellerId; // link to seller service (just id)

}