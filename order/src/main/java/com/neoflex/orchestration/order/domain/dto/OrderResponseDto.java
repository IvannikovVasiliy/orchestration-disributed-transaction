package com.neoflex.orchestration.order.domain.dto;

import com.neoflex.orchestration.order.domain.enumeration.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderResponseDto {
    public OrderResponseDto(Long userId, Long productId, UUID orderId, double amount, OrderStatus orderStatus) {
        this.userId = userId;
        this.productId = productId;
        this.orderId = orderId;
        this.amount = amount;
        this.orderStatus = orderStatus;
    }

    public OrderResponseDto() {
    }

    private Long userId;
    private Long productId;
    private UUID orderId;
    private double amount;
    private OrderStatus orderStatus;
}
