package com.br.joaoptgaino.productservice.fixtures;

import com.br.joaoptgaino.productservice.dto.ProductRequest;

import java.math.BigDecimal;

public class ProductFixtures {
    public static ProductRequest getProductRequest() {
        return ProductRequest.builder()
                .name("Iphone 13")
                .description("Iphone 13")
                .price(BigDecimal.valueOf(1200))
                .build();
    }
}
