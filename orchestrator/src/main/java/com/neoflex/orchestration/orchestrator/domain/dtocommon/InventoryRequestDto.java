package com.neoflex.orchestration.orchestrator.domain.dtocommon;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class InventoryRequestDto {
    private Long userId;
    private Long productId;
    private UUID orderId;
}
