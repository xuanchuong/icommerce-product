package com.icommerce.product.domain.repository;

import com.icommerce.product.domain.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);

    Optional<Order> findById(String id);

    void deleteAll();

    List<Order> findAll();

}
