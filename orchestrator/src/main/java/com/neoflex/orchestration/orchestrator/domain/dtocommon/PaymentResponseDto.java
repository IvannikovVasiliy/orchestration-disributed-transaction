package com.neoflex.orchestration.orchestrator.domain.dtocommon;

import com.neoflex.orchestration.orchestrator.domain.enumcommon.PaymentStatus;
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