package com.neoflex.orchestration.inventory.domain.dto;

import com.neoflex.orchestration.inventory.domain.enumeration.InventoryStatus;
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
