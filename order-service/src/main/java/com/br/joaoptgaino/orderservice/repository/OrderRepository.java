package com.br.joaoptgaino.orderservice.repository;

import com.br.joaoptgaino.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
