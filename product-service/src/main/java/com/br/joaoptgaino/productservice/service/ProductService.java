package com.br.joaoptgaino.productservice.service;

import com.br.joaoptgaino.productservice.dto.ProductRequest;
import com.br.joaoptgaino.productservice.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductRequest productRequest);

    List<ProductResponse> findAllProducts();
}
