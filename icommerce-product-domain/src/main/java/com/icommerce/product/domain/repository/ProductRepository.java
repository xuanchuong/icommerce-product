package com.icommerce.product.domain.repository;

import com.icommerce.product.domain.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository {
    Product save(Product product);
    Product update(Product product);
    Optional<Product> findById(String id);
    void deleteById(String id);
    List<Product> findAll();
    void deleteAll();
}
