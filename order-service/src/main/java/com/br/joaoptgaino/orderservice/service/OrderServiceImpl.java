package com.br.joaoptgaino.orderservice.service;

import com.br.joaoptgaino.orderservice.dto.InventoryResponse;
import com.br.joaoptgaino.orderservice.dto.OrderLineItemsDTO;
import com.br.joaoptgaino.orderservice.dto.OrderRequest;
import com.br.joaoptgaino.orderservice.model.Order;
import com.br.joaoptgaino.orderservice.model.OrderLineItems;
import com.br.joaoptgaino.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    private final ModelMapper modelMapper;

    public Order placeOrder(OrderRequest orderRequest) {
        Order order = getOrder(orderRequest);

        List<String> skuCodes = order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode).toList();

            InventoryResponse[] inventoryResponses = getInventory(skuCodes);

        boolean allProductsInStock = Arrays.stream(Objects.requireNonNull(inventoryResponses)).allMatch(InventoryResponse::isInStock);

        if (!allProductsInStock) {
            StringBuilder sb = getStringBuilder(inventoryResponses);
            throw new IllegalArgumentException(sb.toString());
        }

        return orderRepository.save(order);
    }

    private static StringBuilder getStringBuilder(InventoryResponse[] inventoryResponses) {
        StringBuilder sb = new StringBuilder();
        sb.append("Unable to place order because the following products are not in stock: ");
        for (InventoryResponse response : inventoryResponses) {
            if (!response.isInStock()) {
                sb.append(response.getSkuCode()).append(" ");
            }
        }
        return sb;
    }

    private Order getOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItemsList = orderRequest
                .getOrderLineItemsDTO()
                .stream()
                .map(this::mapToDto)
                .toList();
        order.setOrderLineItemsList(orderLineItemsList);
        return order;
    }

    private InventoryResponse[] getInventory(List<String> skuCodes) {
        return webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes)
                                .build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();
    }

    private OrderLineItems mapToDto(OrderLineItemsDTO orderLine) {
        return modelMapper.map(orderLine, OrderLineItems.class);
    }
}
