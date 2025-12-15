package com.marketplace.paymentservice.dto;


import lombok.Data;


@Data
public class PaymentResponse {
    private Long paymentId;
    private String status;
}