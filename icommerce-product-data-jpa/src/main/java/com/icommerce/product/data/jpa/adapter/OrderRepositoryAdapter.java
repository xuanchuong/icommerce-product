package com.icommerce.product.data.jpa.adapter;

import com.icommerce.product.data.jpa.mapper.OrderJpaMapper;
import com.icommerce.product.data.jpa.repository.OrderJpaRepository;
import com.icommerce.product.domain.entity.Order;
import com.icommerce.product.domain.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
public class OrderRepositoryAdapter implements OrderRepository {
    private final OrderJpaRepository orderJpaRepository;
    private final OrderJpaMapper orderJpaMapper;

    @Override
    public Order save(Order order) {
        return orderJpaMapper.map(orderJpaRepository.save(orderJpaMapper.map(order)));
    }

    @Override
    public Optional<Order> findById(String id) {
        return orderJpaRepository.findById(id).map(orderJpaMapper::map);
    }

    @Override
    public Page<Order> findAll(Pageable pageable) {
        return orderJpaRepository.findAll(pageable).map(orderJpaMapper::map);
    }

    @Override
    public void deleteAll() {
        orderJpaRepository.deleteAll();
    }

    @Override
    public List<Order> findAll() {
        return orderJpaRepository.findAll().stream().map(orderJpaMapper::map).collect(Collectors.toList());
    }
}
