package com.marketplace.notificationservice.dto;


import lombok.Data;


@Data
public class NotificationRequest {
    private String username;
    private String email;
    private String phone;
    private String message;
}