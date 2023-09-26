package com.neoflex.orchestration.orchestrator.domain.dtocommon;

import com.neoflex.orchestration.orchestrator.domain.enumcommon.OrderStatus;
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
