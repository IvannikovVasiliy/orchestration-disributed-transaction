package com.neoflex.orchestration.order.service.impl;

import com.neoflex.orchestration.order.domain.dto.OrchestratorRequestDto;
import com.neoflex.orchestration.order.domain.dto.OrchestratorResponseDto;
import com.neoflex.orchestration.order.domain.dto.OrderRequestDto;
import com.neoflex.orchestration.order.domain.dto.OrderResponseDto;
import com.neoflex.orchestration.order.domain.entity.PurchaseOrder;
import com.neoflex.orchestration.order.domain.enumeration.OrderStatus;
import com.neoflex.orchestration.order.repository.OrderRepository;
import com.neoflex.orchestration.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            ModelMapper modelMapper,
                            Sinks.Many<OrchestratorRequestDto> sink) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.sink = sink;
    }

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private Sinks.Many<OrchestratorRequestDto> sink;

    private static final Map<Long, Double> OrderPrice = Map.of(
            1L, 100D,
            2L, 200D,
            3L, 300D
    );

    @Override
    public Flux<OrderResponseDto> getAllOrders() {
        return orderRepository.findAll().map(order -> convertToDto(order));
    }

    private OrderResponseDto convertToDto(PurchaseOrder order){
        OrderResponseDto dto = modelMapper.map(order, OrderResponseDto.class);
        dto.setOrderId(order.getId());
        dto.setAmount(order.getPrice());
        return dto;
    }

    @Override
    public Mono<PurchaseOrder> createOrder(OrderRequestDto orderRequestDto) {
        log.info("save purchase order");
//        orderRequestDto.setOrderId(UUID.randomUUID());
        log.info("Order created with Pending Status");
        OrchestratorRequestDto orchestratorRequestDto = emitEvent(orderRequestDto);

        PurchaseOrder purchaseOrder = modelMapper.map(orchestratorRequestDto, PurchaseOrder.class);
        purchaseOrder.setPrice(orchestratorRequestDto.getAmount());

//        return Mono.just(purchaseOrder);
        return orderRepository
                .save(purchaseOrder)
                .doOnNext(order -> orderRequestDto.setOrderId(order.getId()))
                .doOnNext(e -> {
                    log.info("Order created with Pending Status");
                    emitEvent(orderRequestDto);
                });
    }

    private OrchestratorRequestDto emitEvent(OrderRequestDto orderRequest) {
        OrchestratorRequestDto orchestratorRequest = modelMapper.map(orderRequest, OrchestratorRequestDto.class);
        orchestratorRequest.setOrderStatus(OrderStatus.ORDER_CREATED);
        orchestratorRequest.setAmount(OrderPrice.get(orderRequest.getProductId()));
        sink.tryEmitNext(orchestratorRequest);

        return orchestratorRequest;
    }
}
