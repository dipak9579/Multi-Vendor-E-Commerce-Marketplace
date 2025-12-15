package com.marketplace.userservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.marketplace.userservice.entity.UserProfile;


@Entity
@Table(name = "address")
@Data
public class Address {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String street;
    private String city;
    private String state;
    private String pincode;
    private String country;
    private boolean isDefault;


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserProfile user;
}