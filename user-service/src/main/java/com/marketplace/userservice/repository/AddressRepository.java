package com.marketplace.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import  java.util.List;

import com.marketplace.userservice.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUserId(Long userId);
}