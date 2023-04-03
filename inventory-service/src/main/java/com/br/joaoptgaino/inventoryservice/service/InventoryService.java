package com.br.joaoptgaino.inventoryservice.service;

import com.br.joaoptgaino.inventoryservice.dto.InventoryResponse;

import java.util.List;

public interface InventoryService {
    List<InventoryResponse> isInStock(List<String> skuCode);
}
