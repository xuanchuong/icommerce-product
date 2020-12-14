package com.icommerce.product.application.configuration;

import com.icommerce.product.application.dto.*;
import com.icommerce.product.application.service.CartService;
import com.icommerce.product.application.service.OrderService;
import com.icommerce.product.application.service.ProductService;
import com.icommerce.product.domain.event.ProductChangelogHistoricalEventPublisher;
import com.icommerce.product.domain.event.UserActivitiesHistoricalEventPublisher;
import com.icommerce.product.domain.repository.CartRepository;
import com.icommerce.product.domain.repository.OrderRepository;
import com.icommerce.product.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

@Configurable
public class ApplicationConfiguration {

    @Bean
    public ProductService productService(ProductRepository productRepository, List<ProductSearchCriteria> searchCriteria,
                                         MongoTemplate mongoTemplate,
                                         UserActivitiesHistoricalEventPublisher userActivitiesHistoricalEventPublisher,
                                         ProductChangelogHistoricalEventPublisher productChangelogHistoricalEventPublisher) {
        return new ProductService(productRepository, searchCriteria, mongoTemplate, userActivitiesHistoricalEventPublisher,
                productChangelogHistoricalEventPublisher);
    }

    @Bean
    public CartService cartService(CartRepository cartRepository, ProductService productService,
                                   UserActivitiesHistoricalEventPublisher userActivitiesHistoricalEventPublisher) {
        return new CartService(cartRepository, productService, userActivitiesHistoricalEventPublisher);
    }

    @Bean
    public OrderService orderService(OrderRepository orderRepository, CartRepository cartRepository,
                                     UserActivitiesHistoricalEventPublisher userActivitiesHistoricalEventPublisher) {
        return new OrderService(orderRepository, cartRepository, userActivitiesHistoricalEventPublisher);
    }

    @Bean
    public ProductSearchCriteria titleSearching() {
        return new TitleSearching();
    }
    @Bean
    public ProductSearchCriteria brandSearching() {
        return new BrandSearching();
    }

    @Bean
    public ProductSearchCriteria priceSearching() {
        return new PriceSearching();
    }
}
