package com.icommerce.product.domain.repository;

import com.icommerce.product.domain.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Order save(Order order);

    Optional<Order> findById(String id);

    Page<Order> findAll(Pageable pageable);

    void deleteAll();

    List<Order> findAll();

}
