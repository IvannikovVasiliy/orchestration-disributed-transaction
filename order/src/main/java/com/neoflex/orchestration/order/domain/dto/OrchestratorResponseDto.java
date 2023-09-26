package com.neoflex.orchestration.order.domain.dto;

import com.neoflex.orchestration.order.domain.enumeration.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrchestratorResponseDto {
    private Long userId;
    private Long productId;
    private UUID orderId;
    private Double amount;
    private OrderStatus orderStatus;
}
