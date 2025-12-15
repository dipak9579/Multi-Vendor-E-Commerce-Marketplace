package com.marketplace.sellerservice.service;


import org.springframework.stereotype.Service;


import com.marketplace.sellerservice.entity.Seller;
import com.marketplace.sellerservice.entity.SellerStatus;
import com.marketplace.sellerservice.repository.SellerRepository;


@Service
public class SellerService {


    private final SellerRepository sellerRepo;


    public SellerService(SellerRepository sellerRepo) {
        this.sellerRepo = sellerRepo;
    }


    // Seller applies for registration
    public Seller registerSeller(String username, Seller seller) {


        sellerRepo.findByUsername(username)
                .ifPresent(s -> {
                    throw new RuntimeException("Seller already exists");
                });


        seller.setUsername(username);
        seller.setStatus(SellerStatus.PENDING);
        return sellerRepo.save(seller);
    }


    // Admin approves seller
    public Seller approveSeller(Long sellerId) {
        Seller seller = sellerRepo.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found"));


        seller.setStatus(SellerStatus.ACTIVE);
        return sellerRepo.save(seller);
    }


    // Admin blocks seller
    public Seller blockSeller(Long sellerId) {
        Seller seller = sellerRepo.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found"));


        seller.setStatus(SellerStatus.BLOCKED);
        return sellerRepo.save(seller);
    }


    // Seller views own profile
    public Seller getSellerProfile(String username) {
        return sellerRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Seller not found"));
    }
}