package com.marketplace.productservice.repository;


import com.marketplace.productservice.entity.SellerProductMapping;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface SellerProductRepository extends JpaRepository<SellerProductMapping, Long> {


    List<SellerProductMapping> findBySellerUsername(String sellerUsername);
}