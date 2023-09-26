package com.neoflex.orchestration.order.controller;

import com.neoflex.orchestration.order.domain.dto.OrderRequestDto;
import com.neoflex.orchestration.order.domain.dto.OrderResponseDto;
import com.neoflex.orchestration.order.domain.entity.PurchaseOrder;
import com.neoflex.orchestration.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    private OrderService orderService;

    @PostMapping
    public Mono<PurchaseOrder> save(@RequestBody OrderRequestDto orderRequest) {
        return orderService.createOrder(orderRequest);
    }

    @PostMapping("/all")
    public Flux<OrderResponseDto> getOrders() {
        return orderService.getAllOrders();
    }
}
