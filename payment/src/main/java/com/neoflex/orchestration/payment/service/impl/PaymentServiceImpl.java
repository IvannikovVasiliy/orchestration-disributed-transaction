package com.neoflex.orchestration.payment.service.impl;

import com.neoflex.orchestration.payment.domain.dto.PaymentRequestDto;
import com.neoflex.orchestration.payment.domain.dto.PaymentResponseDto;
import com.neoflex.orchestration.payment.domain.enumeration.PaymentStatus;
import com.neoflex.orchestration.payment.service.PaymentService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {

    private Map<Long, Double> paymentMap;

    @PostConstruct
    public void init() {
        paymentMap = new HashMap<>();
        paymentMap.put(1L, 500D);
        paymentMap.put(2L, 1000D);
        paymentMap.put(3L, 700D);
    }

    @Override
    public PaymentResponseDto debit(PaymentRequestDto inventoryRequest) {
        double balance = paymentMap.getOrDefault(inventoryRequest.getUserId(), 0D);

        PaymentResponseDto inventoryResponse = new PaymentResponseDto();
        inventoryResponse.setOrderId(inventoryRequest.getOrderId());
        inventoryResponse.setUserId(inventoryRequest.getUserId());
        inventoryResponse.setPaymentStatus(PaymentStatus.PAYMENT_REJECTED);

        if (balance > inventoryRequest.getAmount()) {
            inventoryResponse.setPaymentStatus(PaymentStatus.PAYMENT_APPROVED);
            paymentMap.put(inventoryRequest.getUserId(), balance-inventoryRequest.getAmount());
        }

        return inventoryResponse;
    }

    @Override
    public void credit(PaymentRequestDto paymentRequest) {
        paymentMap.computeIfPresent(paymentRequest.getUserId(), (k, v) -> v + paymentRequest.getAmount());
    }

}
