package com.marketplace.paymentservice.controller;


import org.springframework.web.bind.annotation.*;


import com.marketplace.paymentservice.dto.PaymentRequest;
import com.marketplace.paymentservice.entity.Payment;
import com.marketplace.paymentservice.service.PaymentService;
import com.marketplace.paymentservice.repository.PaymentRepository;


@RestController
@RequestMapping("/api/payments")
public class PaymentController {


    private final PaymentService service;
    private final PaymentRepository paymentRepo;


    public PaymentController(PaymentService service,PaymentRepository paymentRepo) {
        this.service = service;
        this.paymentRepo=paymentRepo;
    }

    @PostMapping("/initiate")
    public Payment initiatePayment(@RequestBody PaymentRequest request) {
        return service.processPayment(request);
    }
    @GetMapping("/order/{orderId}")
    public Payment getByOrder(@PathVariable Long orderId) {
        return paymentRepo.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }


}