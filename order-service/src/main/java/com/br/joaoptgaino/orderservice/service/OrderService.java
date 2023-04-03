package com.br.joaoptgaino.orderservice.service;

import com.br.joaoptgaino.orderservice.dto.OrderRequest;
import com.br.joaoptgaino.orderservice.model.Order;

public interface OrderService {
    Order placeOrder(OrderRequest orderRequest);
}
