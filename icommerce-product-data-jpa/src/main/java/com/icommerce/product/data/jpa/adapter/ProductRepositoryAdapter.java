package com.icommerce.product.data.jpa.adapter;

import com.icommerce.product.data.jpa.entity.ProductJpa;
import com.icommerce.product.data.jpa.mapper.ProductJpaMapper;
import com.icommerce.product.data.jpa.repository.ProductJpaRepository;
import com.icommerce.product.domain.entity.Product;
import com.icommerce.product.domain.repository.ProductRepository;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ProductRepositoryAdapter implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;
    private final ProductJpaMapper productJpaMapper;

    @Override
    public Product save(Product product) {
        ProductJpa productJpa = productJpaMapper.map(product);
        return productJpaMapper.map(productJpaRepository.save(productJpa));
    }

    @Override
    public Product update(Product product) {
        ProductJpa existing = productJpaRepository.findById(product.getId())
                .orElseThrow(() -> new RuntimeException("Could not found product with id " + product.getId()));
        existing.setBrand(product.getBrand());
        existing.setPrice(product.getPrice());
        existing.setTitle(product.getTitle());
        return productJpaMapper.map(productJpaRepository.save(existing));
    }

    @Override
    public Optional<Product> findById(String id) {
        return productJpaRepository.findById(id).map(productJpaMapper::map);
    }

    @Override
    public void deleteById(String id) {
        productJpaRepository.deleteById(id);
    }

    @Override
    public List<Product> findAll() {
        return productJpaRepository.findAll().stream().map(productJpaMapper::map)
            .collect(Collectors.toList());
    }

    @Override
    public void deleteAll() {
        productJpaRepository.deleteAll();
    }
}
