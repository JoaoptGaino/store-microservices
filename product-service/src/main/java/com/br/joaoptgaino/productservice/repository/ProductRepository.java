package com.br.joaoptgaino.productservice.repository;

import com.br.joaoptgaino.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
