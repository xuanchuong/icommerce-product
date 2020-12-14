package com.icommerce.product.application.service;

import com.icommerce.product.domain.entity.ActionId;
import com.icommerce.product.domain.entity.Cart;
import com.icommerce.product.domain.entity.Product;
import com.icommerce.product.domain.event.UserActivitiesHistoricalEvent;
import com.icommerce.product.domain.event.UserActivitiesHistoricalEventPublisher;
import com.icommerce.product.domain.repository.CartRepository;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    private final ProductService productService;

    private final UserActivitiesHistoricalEventPublisher userActivitiesHistoricalEventPublisher;

    public Cart addToCart(String user, String productId, Integer quantity) {
        try {
            Product product = productService.findDetailBy(productId).orElseThrow(() ->
                new RuntimeException("Could not found product with id " + productId));
            return cartRepository.addToCart(user, product, quantity);
        } finally {
            UserActivitiesHistoricalEvent event = UserActivitiesHistoricalEvent.builder()
                    .actionId(ActionId.ADD_TO_CART.name())
                    .actionDescription(String.format("User adding to cart product id %s and %s units", user, productId, quantity))
                    .actionDate(LocalDateTime.now())
                    .userId(user).build();
            userActivitiesHistoricalEventPublisher.publish(event);
        }
    }

    public Cart getCart(String user) {
        try {
            return cartRepository.getCart(user);
        } finally {
            UserActivitiesHistoricalEvent event = UserActivitiesHistoricalEvent.builder()
                    .actionId(ActionId.VIEW_CART.name())
                    .actionDescription(String.format("User %s views cart detail", user))
                    .actionDate(LocalDateTime.now())
                    .userId(user).build();
            userActivitiesHistoricalEventPublisher.publish(event);
        }
    }
    
    public Cart createCart(String user) {
        return cartRepository.create(user);
    }
}
