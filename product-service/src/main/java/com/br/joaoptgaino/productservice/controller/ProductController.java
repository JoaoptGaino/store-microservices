package com.br.joaoptgaino.productservice.controller;


import com.br.joaoptgaino.productservice.dto.ProductRequest;
import com.br.joaoptgaino.productservice.dto.ProductResponse;
import com.br.joaoptgaino.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest) {
        productService.creteProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> findAllProducts() {
        return productService.findAllProducts();
    }
}
