package com.neoflex.orchestration.orchestrator.domain.dtocommon;

import com.neoflex.orchestration.orchestrator.domain.enumcommon.InventoryStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class InventoryResponseDto {
    private Long userId;
    private Long productId;
    private UUID orderId;
    private InventoryStatus inventoryStatus;
}
