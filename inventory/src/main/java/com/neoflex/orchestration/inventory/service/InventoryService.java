package com.neoflex.orchestration.inventory.service;

import com.neoflex.orchestration.inventory.domain.dto.InventoryRequestDto;
import com.neoflex.orchestration.inventory.domain.dto.InventoryResponseDto;

public interface InventoryService {
    InventoryResponseDto deduct(InventoryRequestDto inventoryRequest);
    void save(InventoryRequestDto inventoryRequest);
}
