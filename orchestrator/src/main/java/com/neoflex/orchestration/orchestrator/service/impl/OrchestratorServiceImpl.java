package com.neoflex.orchestration.orchestrator.service.impl;

import com.neoflex.orchestration.orchestrator.domain.dtocommon.InventoryRequestDto;
import com.neoflex.orchestration.orchestrator.domain.dtocommon.OrchestratorRequestDto;
import com.neoflex.orchestration.orchestrator.domain.dtocommon.OrchestratorResponseDto;
import com.neoflex.orchestration.orchestrator.domain.dtocommon.PaymentRequestDto;
import com.neoflex.orchestration.orchestrator.domain.enumcommon.OrderStatus;
import com.neoflex.orchestration.orchestrator.domain.enumcommon.WorkflowStepStatus;
import com.neoflex.orchestration.orchestrator.exception.WorkflowException;
import com.neoflex.orchestration.orchestrator.saga.OrderWorkflow;
import com.neoflex.orchestration.orchestrator.saga.Workflow;
import com.neoflex.orchestration.orchestrator.service.OrchestratorService;
import com.neoflex.orchestration.orchestrator.step.InventoryStep;
import com.neoflex.orchestration.orchestrator.step.PaymentStep;
import com.neoflex.orchestration.orchestrator.step.WorkflowStep;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.function.Function;

@Service
@Slf4j
public class OrchestratorServiceImpl implements OrchestratorService/*, Function<Flux<OrchestratorRequestDto>, Flux<OrchestratorResponseDto>>*/ {

    @Autowired
    public OrchestratorServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    @Qualifier("paymentClient")
    private WebClient paymentClient;
    @Autowired
    @Qualifier("inventoryClient")
    private WebClient inventoryClient;
    private ModelMapper modelMapper;

    @Override
    public Mono<OrchestratorResponseDto> orderProduct(OrchestratorRequestDto orchestratorRequest) {
        log.debug("request. order product");

        Workflow orderWorkflow = getOrderWorkflow(orchestratorRequest);

        OrchestratorResponseDto orchestratorResponseDto = modelMapper.map(orchestratorRequest, OrchestratorResponseDto.class);
        orchestratorResponseDto.setOrderStatus(OrderStatus.ORDER_COMPLETED);

        return Flux
                .fromStream(() -> orderWorkflow.getSteps().stream())
                .flatMap(workflowStep -> workflowStep.process())
                .handle(((aBoolean, synchronousSink) -> {
                    if (aBoolean) {
                        synchronousSink.next(true);
                    } else {
                        synchronousSink.error(new WorkflowException("create order failed!"));
                    }
                }))
                .then(Mono.fromCallable(() -> orchestratorResponseDto))
                .onErrorResume(e -> revertOrder(orderWorkflow, orchestratorRequest));
    }

    private Mono<OrchestratorResponseDto> revertOrder(Workflow workflow, OrchestratorRequestDto orchestratorRequest) {
        return Flux.fromStream(() -> workflow.getSteps().stream())
                .filter(wf -> wf.getStatus().equals(WorkflowStepStatus.COMPLETE))
                .flatMap(wf -> wf.revert())
                .retry(3)
                .then(Mono.just(this.getResponseDto(orchestratorRequest, OrderStatus.ORDER_CANCELLED)));
    }

    private Workflow getOrderWorkflow(OrchestratorRequestDto orchestratorRequestDto) {
        PaymentRequestDto paymentRequest = modelMapper.map(orchestratorRequestDto, PaymentRequestDto.class);
        InventoryRequestDto inventoryRequest = modelMapper.map(orchestratorRequestDto, InventoryRequestDto.class);

        WorkflowStep paymentStep = new PaymentStep(paymentClient, paymentRequest);
        WorkflowStep inventoryStep = new InventoryStep(inventoryClient, inventoryRequest);

        return new OrderWorkflow(List.of(paymentStep, inventoryStep));
    }

    private OrchestratorResponseDto getResponseDto(OrchestratorRequestDto requestDTO, OrderStatus status){
        OrchestratorResponseDto response = new OrchestratorResponseDto();
        response.setOrderId(requestDTO.getOrderId());
        response.setAmount(requestDTO.getAmount());
        response.setProductId(requestDTO.getProductId());
        response.setUserId(requestDTO.getUserId());
        response.setOrderStatus(status);
        return response;
    }

//    @Override
//    public Flux<OrchestratorResponseDto> apply(Flux<OrchestratorRequestDto> orchestratorRequestDtoFlux) {
//        log.info("request. apply");
//
//        return orchestratorRequestDtoFlux
//                .flatMap(dto -> orderProduct(dto))
//                .doOnNext(dto -> log.info("Status: {}", dto.getOrderStatus()));
////        return flux -> flux
////                .flatMap(dto -> orchestratorService.orderProduct(dto))
////                .doOnNext(dto -> log.info("Status: {}", dto.getOrderStatus()));
//    }
}
