package com.icommerce.product.application.service;

import com.icommerce.product.domain.entity.ActionId;
import com.icommerce.product.domain.entity.Cart;
import com.icommerce.product.domain.entity.Product;
import com.icommerce.product.domain.event.UserActivitiesHistoricalEvent;
import com.icommerce.product.domain.event.UserActivitiesHistoricalEventPublisher;
import com.icommerce.product.domain.repository.CartRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @InjectMocks
    CartService cartService;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private ProductService productService;
    @Mock
    private UserActivitiesHistoricalEventPublisher userActivitiesHistoricalEventPublisher;

    @Test
    public void addToCart_should_work_correctly() {
        // Given
        Product product = mock(Product.class);
        Cart cart = mock(Cart.class);
        when(productService.findDetailBy("productId")).thenReturn(Optional.of(product));
        when(cartRepository.addToCart("user", product, 5)).thenReturn(cart);
        doNothing().when(userActivitiesHistoricalEventPublisher).publish(any(UserActivitiesHistoricalEvent.class));
        ArgumentCaptor<UserActivitiesHistoricalEvent> eventArgumentCaptor = ArgumentCaptor.forClass(UserActivitiesHistoricalEvent.class);
        // When
        Cart result = cartService.addToCart("user", "productId", 5);
        // Then
        assertThat(result).isSameAs(result);
        verify(productService).findDetailBy("productId");
        verify(cartRepository).addToCart("user", product, 5);
        verify(userActivitiesHistoricalEventPublisher).publish(eventArgumentCaptor.capture());
        assertThat(eventArgumentCaptor.getValue().getActionId()).isEqualTo(ActionId.ADD_TO_CART.toString());
    }

    @Test
    public void addToCart_should_throw_exception_if_product_not_found() {
        // Given
        when(productService.findDetailBy("productId")).thenReturn(Optional.ofNullable(null));
        doNothing().when(userActivitiesHistoricalEventPublisher).publish(any(UserActivitiesHistoricalEvent.class));
        ArgumentCaptor<UserActivitiesHistoricalEvent> eventArgumentCaptor = ArgumentCaptor.forClass(UserActivitiesHistoricalEvent.class);
        // When
        Throwable throwable = catchThrowable(() -> cartService.addToCart("user", "productId", 5));
        // Then
        assertThat(throwable).isInstanceOf(RuntimeException.class);
        assertThat(throwable).hasMessage("Could not found product with id productId");
        verify(productService).findDetailBy("productId");
        verify(cartRepository, never()).addToCart(anyString(), any(), anyInt());
        verify(userActivitiesHistoricalEventPublisher).publish(eventArgumentCaptor.capture());
        assertThat(eventArgumentCaptor.getValue().getActionId()).isEqualTo(ActionId.ADD_TO_CART.toString());
    }

    @Test
    public void getCart_should_work_correctly() {
        // Given
        Cart cart = mock(Cart.class);
        when(cartRepository.getCart("user")).thenReturn(cart);
        doNothing().when(userActivitiesHistoricalEventPublisher).publish(any(UserActivitiesHistoricalEvent.class));
        ArgumentCaptor<UserActivitiesHistoricalEvent> eventArgumentCaptor = ArgumentCaptor.forClass(UserActivitiesHistoricalEvent.class);
        // When
        Cart result = cartService.getCart("user");
        // Then
        assertThat(result).isSameAs(cart);
        verify(cartRepository).getCart("user");
        verify(userActivitiesHistoricalEventPublisher).publish(eventArgumentCaptor.capture());
        assertThat(eventArgumentCaptor.getValue().getActionId()).isEqualTo(ActionId.VIEW_CART.toString());
    }

}
