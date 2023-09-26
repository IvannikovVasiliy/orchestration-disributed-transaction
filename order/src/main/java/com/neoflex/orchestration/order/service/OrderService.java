package com.neoflex.orchestration.order.service;

import com.neoflex.orchestration.order.domain.entity.PurchaseOrder;
import com.neoflex.orchestration.order.domain.dto.OrderRequestDto;
import com.neoflex.orchestration.order.domain.dto.OrderResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderService {
    Flux<OrderResponseDto> getAllOrders();
    Mono<PurchaseOrder> createOrder(OrderRequestDto orderRequestDto);
}
