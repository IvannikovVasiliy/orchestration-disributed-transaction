package com.neoflex.orchestration.inventory.service.impl;

import com.neoflex.orchestration.inventory.domain.dto.InventoryRequestDto;
import com.neoflex.orchestration.inventory.domain.dto.InventoryResponseDto;
import com.neoflex.orchestration.inventory.domain.enumeration.InventoryStatus;
import com.neoflex.orchestration.inventory.service.InventoryService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InventoryServiceImpl implements InventoryService {

    private Map<Long, Integer> inventoryMap;

    @PostConstruct
    public void init() {
        inventoryMap = new HashMap<>();
        inventoryMap.put(1L, 2);
        inventoryMap.put(2L, 3);
        inventoryMap.put(3L, 4);
    }

    @Override
    public InventoryResponseDto deduct(InventoryRequestDto inventoryRequest) {
        int qty = inventoryMap.getOrDefault(inventoryRequest.getProductId(), 0);

        InventoryResponseDto inventoryResponse = new InventoryResponseDto();
        inventoryResponse.setOrderId(inventoryRequest.getOrderId());
        inventoryResponse.setProductId(inventoryRequest.getProductId());
        inventoryResponse.setUserId(inventoryRequest.getUserId());
        inventoryResponse.setInventoryStatus(InventoryStatus.UNAVAILABLE);

        if (qty > 0) {
            inventoryResponse.setInventoryStatus(InventoryStatus.AVAILABLE);
            inventoryMap.put(inventoryRequest.getProductId(), qty-1);
        }

        return inventoryResponse;
    }

    @Override
    public void save(InventoryRequestDto inventoryRequest) {
        inventoryMap.computeIfPresent(inventoryRequest.getProductId(), (k, v) -> v+1);
    }

}
