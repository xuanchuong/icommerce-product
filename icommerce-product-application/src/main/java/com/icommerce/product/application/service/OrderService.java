package com.icommerce.product.application.service;

import com.icommerce.product.application.vo.AuthoritiesConstants;
import com.icommerce.product.domain.entity.ActionId;
import com.icommerce.product.domain.entity.Cart;
import com.icommerce.product.domain.entity.Order;
import com.icommerce.product.domain.event.UserActivitiesHistoricalEvent;
import com.icommerce.product.domain.event.UserActivitiesHistoricalEventPublisher;
import com.icommerce.product.domain.repository.CartRepository;
import com.icommerce.product.domain.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final CartRepository cartRepository;

    private final UserActivitiesHistoricalEventPublisher userActivitiesHistoricalEventPublisher;
    
    @Transactional
    public Order makeAnOrder() {
        String currentUser = SecurityUtils.getCurrentUserLogin().orElse("system");
        Cart cart = cartRepository.getCart(currentUser);
        if (CollectionUtils.isEmpty(cart.getSelectedProducts())) {
            throw new RuntimeException("Your cart is empty, you are not allow to make an order");
        }
        Order order = Order.builder()
                .createdDate(LocalDate.now())
                .cart(cart)
                .deliveryDate(LocalDate.now().plusDays(5))
                .paymentType("CASH")
                .createdBy(currentUser).build();
        order = orderRepository.save(order);
        cartRepository.clearCart(currentUser);
        UserActivitiesHistoricalEvent userActivitiesHistoricalEvent = UserActivitiesHistoricalEvent.builder()
                .actionDate(LocalDateTime.now())
                .userId(SecurityUtils.getCurrentUserLogin().orElse(""))
                .actionId(ActionId.CHECKOUT_ORDER.name())
                .actionDescription(String.format("User %s view order detail %s", currentUser, order))
                .build();
        userActivitiesHistoricalEventPublisher.publish(userActivitiesHistoricalEvent);
        // Remove the shopping cart
        return order;
    }

    @Transactional(readOnly = true)
    public Optional<Order> findBy(String id) {
        try {
            return orderRepository.findById(id);
        } finally {
            UserActivitiesHistoricalEvent userActivitiesHistoricalEvent = UserActivitiesHistoricalEvent.builder()
                    .actionDate(LocalDateTime.now())
                    .userId(SecurityUtils.getCurrentUserLogin().orElse(""))
                    .actionId(ActionId.GET_ORDER.name())
                    .actionDescription(String.format("User %s view order detail %s", SecurityUtils.getCurrentUserLogin().orElse("system"), id))
                    .build();
            userActivitiesHistoricalEventPublisher.publish(userActivitiesHistoricalEvent);
        }
    }

    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    @Transactional(readOnly = true)
    public Page<Order> findAll(Pageable pageable) {
        try {
            return orderRepository.findAll(pageable);
        } finally {
            UserActivitiesHistoricalEvent userActivitiesHistoricalEvent = UserActivitiesHistoricalEvent.builder()
                    .actionDate(LocalDateTime.now())
                    .userId(SecurityUtils.getCurrentUserLogin().orElse(""))
                    .actionId(ActionId.GET_ALL_ORDERS.name())
                    .actionDescription(String.format("User %s view list order, %s", SecurityUtils.getCurrentUserLogin().orElse("system"), pageable))
                    .build();
            userActivitiesHistoricalEventPublisher.publish(userActivitiesHistoricalEvent);
        }
    }
}
