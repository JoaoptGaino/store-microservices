package com.br.joaoptgaino.orderservice.service;

import com.br.joaoptgaino.orderservice.dto.InventoryResponse;
import com.br.joaoptgaino.orderservice.dto.OrderLineItemsDTO;
import com.br.joaoptgaino.orderservice.dto.OrderRequest;
import com.br.joaoptgaino.orderservice.model.Order;
import com.br.joaoptgaino.orderservice.model.OrderLineItems;
import com.br.joaoptgaino.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItemsList = orderRequest
                .getOrderLineItemsDTO()
                .stream()
                .map(this::mapToDto)
                .toList();
        order.setOrderLineItemsList(orderLineItemsList);

        List<String> skuCodes = order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode).toList();

        InventoryResponse[] inventoryResponses = webClient.get()
                .uri("http://localhost:8082/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes)
                                .build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean allProductsInStock = Arrays.stream(Objects.requireNonNull(inventoryResponses)).allMatch(InventoryResponse::isInStock);

        if (!allProductsInStock) {
            StringBuilder sb = new StringBuilder();
            sb.append("Unable to place order because the following products are not in stock: ");
            for (InventoryResponse response : inventoryResponses) {
                if (!response.isInStock()) {
                    sb.append(response.getSkuCode()).append(" ");
                }
            }
            throw new IllegalArgumentException(sb.toString());
        }

        orderRepository.save(order);

    }

    private OrderLineItems mapToDto(OrderLineItemsDTO orderLine) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLine.getPrice());
        orderLineItems.setQuantity(orderLine.getQuantity());
        orderLineItems.setSkuCode(orderLine.getSkuCode());
        return orderLineItems;
    }
}