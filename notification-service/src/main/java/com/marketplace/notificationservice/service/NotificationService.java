package com.marketplace.notificationservice.service;


import org.springframework.stereotype.Service;


import com.marketplace.notificationservice.dto.NotificationRequest;


@Service
public class NotificationService {


    public void sendEmail(NotificationRequest request) {
        System.out.println("📧 EMAIL SENT");
        System.out.println("To: " + request.getEmail());
        System.out.println("Message: " + request.getMessage());
    }


    public void sendSms(NotificationRequest request) {
        System.out.println("📱 SMS SENT");
        System.out.println("To: " + request.getPhone());
        System.out.println("Message: " + request.getMessage());
    }
}