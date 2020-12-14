package com.icommerce.product.data.jpa.configuration;

import com.icommerce.product.data.jpa.adapter.*;
import com.icommerce.product.data.jpa.mapper.OrderJpaMapper;
import com.icommerce.product.data.jpa.mapper.ProductJpaMapper;
import com.icommerce.product.data.jpa.mapper.UserJpaMapper;
import com.icommerce.product.data.jpa.repository.OrderJpaRepository;
import com.icommerce.product.data.jpa.repository.ProductJpaRepository;
import com.icommerce.product.data.jpa.repository.UserJpaRepository;
import com.icommerce.product.domain.entity.Cart;
import com.icommerce.product.domain.repository.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
    @ComponentScan({"com.icommerce.product.data.jpa.mapper"})
    @EnableMongoRepositories("com.icommerce.product.data.jpa.repository")
public class DataJpaConfiguration {

    @Bean
    public UserRepository userRepository(UserJpaRepository userJpaRepository,
                                         UserJpaMapper userJpaMapper) {
        return new UserRepositoryAdapter(userJpaRepository, userJpaMapper);
    }

    @Bean
    public ProductRepository productRepository(ProductJpaRepository productJpaRepository,
                                               ProductJpaMapper productJpaMapper) {
        return new ProductRepositoryAdapter(productJpaRepository, productJpaMapper);
    }

    @Bean
    public Map<String, Cart> shoppingCart() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    public CartRepository cartRepository(Map<String, Cart> shoppingCart) {
        return new CartRepositoryAdapter(shoppingCart);
    }

    @Bean
    public OrderRepository orderRepository(OrderJpaRepository orderJpaRepository, OrderJpaMapper mapper) {
        return new OrderRepositoryAdapter(orderJpaRepository, mapper);
    }
}
