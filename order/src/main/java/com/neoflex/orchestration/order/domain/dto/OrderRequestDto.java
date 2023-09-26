package com.neoflex.orchestration.order.domain.dto;

import com.neoflex.orchestration.order.domain.enumeration.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderRequestDto {
    public OrderRequestDto(Long userId, Long productId, UUID orderId, Double amount) {
        this.userId = userId;
        this.productId = productId;
        this.orderId = orderId;
        this.amount = amount;
    }

    public OrderRequestDto(Long userId, Long productId) {
        this.userId = userId;
        this.productId = productId;
    }

    public OrderRequestDto() {
    }

    private Long userId;
    private Long productId;
    private UUID orderId;
    private Double amount;
}
