package com.neoflex.orchestration.payment.domain.dto;

import com.neoflex.orchestration.payment.domain.enumeration.PaymentStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class PaymentResponseDto {
    private Long userId;
    private UUID orderId;
    private double amount;
    private PaymentStatus paymentStatus;
}