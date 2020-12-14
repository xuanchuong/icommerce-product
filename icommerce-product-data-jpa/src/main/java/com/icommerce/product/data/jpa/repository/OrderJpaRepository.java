package com.icommerce.product.data.jpa.repository;

import com.icommerce.product.data.jpa.entity.OrderJpa;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderJpaRepository extends MongoRepository<OrderJpa, String> {
}
