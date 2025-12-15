package com.marketplace.notificationservice.controller;


import org.springframework.web.bind.annotation.*;


import com.marketplace.notificationservice.dto.NotificationRequest;
import com.marketplace.notificationservice.service.NotificationService;


@RestController
@RequestMapping("/api/notifications")
public class NotificationController {


    private final NotificationService service;


    public NotificationController(NotificationService service) {
        this.service = service;
    }


    // Triggered when order is placed
    @PostMapping("/order")
    public void orderPlaced(@RequestBody NotificationRequest request) {
        service.sendEmail(request);
        service.sendSms(request);
    }


    // Triggered when payment is successful
    @PostMapping("/payment")
    public void paymentSuccess(@RequestBody NotificationRequest request) {
        service.sendEmail(request);
        service.sendSms(request);
    }
}