package com.icommerce.product.data.jpa.repository;

import com.icommerce.product.data.jpa.entity.ProductJpa;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductJpaRepository extends MongoRepository<ProductJpa, String> {
}
