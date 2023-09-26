package com.neoflex.orchestration.payment.controller;

import com.neoflex.orchestration.payment.domain.dto.PaymentRequestDto;
import com.neoflex.orchestration.payment.domain.dto.PaymentResponseDto;
import com.neoflex.orchestration.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    private PaymentService paymentService;

    @PostMapping("/debit")
    public PaymentResponseDto debit(@RequestBody PaymentRequestDto paymentRequest) {
        return paymentService.debit(paymentRequest);
    }

    @PostMapping("/credit")
    public String credit(@RequestBody PaymentRequestDto paymentRequest) {
        paymentService.credit(paymentRequest);
        return "credit";
    }
}
