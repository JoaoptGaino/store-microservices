package com.br.joaoptgaino.orderservice.controller;


import com.br.joaoptgaino.orderservice.dto.OrderRequest;
import com.br.joaoptgaino.orderservice.model.Order;
import com.br.joaoptgaino.orderservice.service.OrderService;
import com.br.joaoptgaino.orderservice.service.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> placeOrder(@RequestBody OrderRequest orderRequest) {
        Order order = orderService.placeOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
}
