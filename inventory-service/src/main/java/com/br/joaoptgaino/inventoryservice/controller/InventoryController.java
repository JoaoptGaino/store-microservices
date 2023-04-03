package com.br.joaoptgaino.inventoryservice.controller;


import com.br.joaoptgaino.inventoryservice.dto.InventoryResponse;
import com.br.joaoptgaino.inventoryservice.service.InventoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryServiceImpl inventoryService;

    @GetMapping
    public ResponseEntity<List<InventoryResponse>> isInStock(@RequestParam List<String> skuCode) {
        List<InventoryResponse> inventoryResponses = inventoryService.isInStock(skuCode);
        return ResponseEntity.status(HttpStatus.OK).body(inventoryResponses);
    }
}
