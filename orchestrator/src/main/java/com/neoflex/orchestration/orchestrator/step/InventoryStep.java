package com.neoflex.orchestration.orchestrator.step;

import com.neoflex.orchestration.orchestrator.domain.dtocommon.InventoryRequestDto;
import com.neoflex.orchestration.orchestrator.domain.dtocommon.InventoryResponseDto;
import com.neoflex.orchestration.orchestrator.domain.dtocommon.PaymentResponseDto;
import com.neoflex.orchestration.orchestrator.domain.enumcommon.InventoryStatus;
import com.neoflex.orchestration.orchestrator.domain.enumcommon.OrderStatus;
import com.neoflex.orchestration.orchestrator.domain.enumcommon.WorkflowStepStatus;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class InventoryStep implements WorkflowStep {

    public InventoryStep(WebClient webClient, InventoryRequestDto inventoryRequest) {
        this.webClient = webClient;
        this.inventoryRequest = inventoryRequest;
        this.stepStatus = WorkflowStepStatus.PENDING;
    }

    private final WebClient webClient;
    private final InventoryRequestDto inventoryRequest;
    private WorkflowStepStatus stepStatus;

    @Override
    public WorkflowStepStatus getStatus() {
        return stepStatus;
    }

    @Override
    public Mono<Boolean> process() {
        return webClient
                .post()
                .uri("/inventory/deduct")
                .body(BodyInserters.fromValue(inventoryRequest))
                .retrieve()
                .bodyToMono(InventoryResponseDto.class)
                .map(r -> r.getInventoryStatus().equals(InventoryStatus.AVAILABLE))
                .doOnNext(b -> stepStatus = b ? WorkflowStepStatus.COMPLETE : WorkflowStepStatus.FAILED);
    }

    @Override
    public Mono<Boolean> revert() {
        return webClient
                .post()
                .uri("/inventory/add")
                .body(BodyInserters.fromValue(inventoryRequest))
                .retrieve()
                .bodyToMono(Void.class)
                .map(r -> true)
                .onErrorReturn(false);
    }
}
