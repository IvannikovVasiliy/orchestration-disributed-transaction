package com.neoflex.orchestration.order.service.impl;

import com.neoflex.orchestration.order.domain.dto.OrchestratorResponseDto;
import com.neoflex.orchestration.order.repository.OrderRepository;
import com.neoflex.orchestration.order.service.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UpdateServiceImpl implements UpdateService {

    @Autowired
    public UpdateServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    private OrderRepository orderRepository;

    public Mono<Void> update(OrchestratorResponseDto orchestratorResponse) {
        return orderRepository
                .findById(orchestratorResponse.getOrderId())
                .doOnNext(p -> p.setOrderStatus(orchestratorResponse.getOrderStatus()))
                .doOnNext(orderRepository::save)
                .then();
    }
}
