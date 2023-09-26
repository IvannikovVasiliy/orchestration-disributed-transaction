package com.neoflex.orchestration.orchestrator.step;

import com.neoflex.orchestration.orchestrator.domain.dtocommon.PaymentRequestDto;
import com.neoflex.orchestration.orchestrator.domain.dtocommon.PaymentResponseDto;
import com.neoflex.orchestration.orchestrator.domain.enumcommon.PaymentStatus;
import com.neoflex.orchestration.orchestrator.domain.enumcommon.WorkflowStepStatus;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class PaymentStep implements WorkflowStep {

    public PaymentStep(WebClient webClient, PaymentRequestDto paymentRequest) {
        this.webClient = webClient;
        this.paymentRequest = paymentRequest;
    }

    private final WebClient webClient;
    private final PaymentRequestDto paymentRequest;
    private WorkflowStepStatus stepStatus = WorkflowStepStatus.PENDING;

    @Override
    public WorkflowStepStatus getStatus() {
        return stepStatus;
    }

    @Override
    public Mono<Boolean> process() {
        return webClient
                .post()
                .uri("/payment/debit")
                .body(BodyInserters.fromValue(paymentRequest))
                .retrieve()
                .bodyToMono(PaymentResponseDto.class)
                .map(r -> r.getPaymentStatus().equals(PaymentStatus.PAYMENT_APPROVED))
                .doOnNext(b -> stepStatus = b ? WorkflowStepStatus.COMPLETE : WorkflowStepStatus.FAILED);
    }

    @Override
    public Mono<Boolean> revert() {
        return webClient
                .post()
                .uri("/payment/credit")
                .body(BodyInserters.fromValue(paymentRequest))
                .retrieve()
                .bodyToMono(Void.class)
                .map(r -> true)
                .onErrorReturn(false);
    }
}
