package com.neoflex.orchestration.inventory.controller;

import com.neoflex.orchestration.inventory.domain.dto.InventoryRequestDto;
import com.neoflex.orchestration.inventory.domain.dto.InventoryResponseDto;
import com.neoflex.orchestration.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    private InventoryService inventoryService;

    @PostMapping("/deduct")
    public InventoryResponseDto deduct(@RequestBody InventoryRequestDto inventoryRequest) {
        return inventoryService.deduct(inventoryRequest);
    }

    @PostMapping("/add")
    public String save(@RequestBody InventoryRequestDto inventoryRequest) {
        inventoryService.save(inventoryRequest);
        return "save";
    }
}
