package com.neoflex.orchestration.payment.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class PaymentRequestDto {
    private Long userId;
    private UUID orderId;
    private double amount;
}
