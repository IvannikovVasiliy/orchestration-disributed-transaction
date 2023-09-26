package com.neoflex.orchestration.payment.service;

import com.neoflex.orchestration.payment.domain.dto.PaymentRequestDto;
import com.neoflex.orchestration.payment.domain.dto.PaymentResponseDto;

public interface PaymentService {
    PaymentResponseDto debit(PaymentRequestDto inventoryRequest);
    void credit(PaymentRequestDto paymentRequest);
}
