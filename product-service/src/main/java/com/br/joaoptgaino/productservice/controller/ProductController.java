package com.br.joaoptgaino.productservice.controller;


import com.br.joaoptgaino.productservice.dto.ProductRequest;
import com.br.joaoptgaino.productservice.dto.ProductResponse;
import com.br.joaoptgaino.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest) {
        ProductResponse productResponse = productService.createProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(productResponse);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAllProducts() {
        List<ProductResponse> productResponses = productService.findAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(productResponses);
    }
}
