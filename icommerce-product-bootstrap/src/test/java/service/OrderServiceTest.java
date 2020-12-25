package service;

import com.icommerce.product.application.service.OrderService;
import com.icommerce.product.application.service.UserService;
import com.icommerce.product.domain.entity.*;
import com.icommerce.product.domain.event.UserActivitiesHistoricalEvent;
import com.icommerce.product.domain.event.UserActivitiesHistoricalEventPublisher;
import com.icommerce.product.domain.repository.CartRepository;
import com.icommerce.product.domain.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;

    @Mock
    CartRepository cartRepository;

    @Mock
    UserActivitiesHistoricalEventPublisher userActivitiesHistoricalEventPublisher;

    @InjectMocks
    OrderService orderService;

    @Mock
    UserService userService;

    @Test
    public void makeAnOrder_should_work_correctly() {
        // Given
        CartDetail cartDetail = new CartDetail();
        cartDetail.setQuantity(10);
        cartDetail.setPricePerUnit(1000);
        Map<String, CartDetail> selectedProducts = new HashMap();
        selectedProducts.put("123", cartDetail);
        Cart cart = new Cart();
        cart.setSelectedProducts(selectedProducts);
        cart.setTotalAmount(100);
        Order order = mock(Order.class);
        when(cartRepository.getCart(anyString())).thenReturn(cart);
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        doNothing().when(userActivitiesHistoricalEventPublisher).publish(any(UserActivitiesHistoricalEvent.class));
        ArgumentCaptor<UserActivitiesHistoricalEvent> eventArgumentCaptor = ArgumentCaptor.forClass(UserActivitiesHistoricalEvent.class);
        // When
        Order result = orderService.makeAnOrder();
        // Then
        assertThat(result).isSameAs(order);
        verify(cartRepository).getCart(anyString());
        verify(cartRepository).clearCart(anyString());
        verify(userActivitiesHistoricalEventPublisher).publish(eventArgumentCaptor.capture());
        assertThat(eventArgumentCaptor.getValue().getActionId()).isEqualTo(ActionId.CHECKOUT_ORDER.toString());
        ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(captor.capture());
        assertThat(captor.getValue().getCreatedDate()).isEqualTo(LocalDate.now());
        assertThat(captor.getValue().getDeliveryDate()).isEqualTo(LocalDate.now().plusDays(5));
        assertThat(captor.getValue().getPaymentType()).isEqualTo("CASH");
        assertThat(captor.getValue().getCart().getSelectedProducts()).containsEntry("123", cartDetail);
        assertThat(captor.getValue().getCart().getTotalAmount()).isEqualTo(100);
    }

    @Test
    public void makeAnOrder_throw_exception_if_cart_is_empty() {
        // Given
        Cart cart = new Cart();
        cart.setSelectedProducts(null);
        when(cartRepository.getCart(anyString())).thenReturn(cart);
        // When
        Throwable throwable = catchThrowable(() -> orderService.makeAnOrder());
        // Then
        assertThat(throwable).isInstanceOf(RuntimeException.class);
        assertThat(throwable).hasMessage("Your cart is empty, you are not allow to make an order");
        verify(cartRepository).getCart(anyString());
        verify(orderRepository, never()).save(any(Order.class));
        verify(userActivitiesHistoricalEventPublisher, never()).publish(any(UserActivitiesHistoricalEvent.class));
    }

    @Test
    public void findBy_should_work_correctly() {
        // Given
        Order order = mock(Order.class);
        when(orderRepository.findById("id")).thenReturn(Optional.ofNullable(order));
        doNothing().when(userActivitiesHistoricalEventPublisher).publish(any(UserActivitiesHistoricalEvent.class));
        ArgumentCaptor<UserActivitiesHistoricalEvent> eventArgumentCaptor = ArgumentCaptor.forClass(UserActivitiesHistoricalEvent.class);
        // When
        Optional<Order> result = orderService.findBy("id");
        // Then
        assertThat(result.get()).isSameAs(order);
        verify(orderRepository).findById("id");
        verify(userActivitiesHistoricalEventPublisher).publish(eventArgumentCaptor.capture());
        assertThat(eventArgumentCaptor.getValue().getActionId()).isEqualTo(ActionId.GET_ORDER.toString());
    }

    @Test
    public void findAll_should_work_correctly() {
        // Given
        Page page = mock(Page.class);
        Pageable pageable = mock(Pageable.class);
        when(orderRepository.findAll(pageable)).thenReturn(page);
        doNothing().when(userActivitiesHistoricalEventPublisher).publish(any(UserActivitiesHistoricalEvent.class));
        ArgumentCaptor<UserActivitiesHistoricalEvent> eventArgumentCaptor = ArgumentCaptor.forClass(UserActivitiesHistoricalEvent.class);
        // When
        Page<Order> result = orderService.findAll(pageable);
        // Then
        assertThat(result).isSameAs(page);
        verify(orderRepository).findAll(pageable);
        verify(userActivitiesHistoricalEventPublisher).publish(eventArgumentCaptor.capture());
        assertThat(eventArgumentCaptor.getValue().getActionId()).isEqualTo(ActionId.GET_ALL_ORDERS.toString());
    }
}
