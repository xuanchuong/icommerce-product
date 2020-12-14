package com.icommerce.product.data.jpa.adapter;

import com.icommerce.product.domain.entity.Cart;
import com.icommerce.product.domain.entity.CartDetail;
import com.icommerce.product.domain.entity.Product;
import com.icommerce.product.domain.repository.CartRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public class CartRepositoryAdapter implements CartRepository {

    private final Map<String, Cart> shoppingCart;

    @Override
    public Cart addToCart(String userId, Product product, Integer quantity) {
        Cart cart = getCart(userId);
        CartDetail cartDetail = cart.getSelectedProducts().getOrDefault(product.getId(), new CartDetail());
        if (quantity > 1) {
            cartDetail.setPricePerUnit(product.getPrice());
            cartDetail.setQuantity(quantity);
            cart.getSelectedProducts().put(product.getId(), cartDetail);
        } else {
            if (cart.getSelectedProducts().containsKey(product.getId())) {
                cart.getSelectedProducts().remove(product.getId());
            }
        }

        cart.setTotalAmount(calculateTotalAmount(cart.getSelectedProducts()));
        shoppingCart.put(userId, cart);
        return cart;
    }

    private Integer calculateTotalAmount(Map<String, CartDetail> selectedProducts) {
        int totalAmount = 0;
        if (CollectionUtils.isEmpty(selectedProducts)) {
            return totalAmount;
        }
        for (String productId : selectedProducts.keySet()) {
            totalAmount += (selectedProducts.get(productId).getQuantity() *
                selectedProducts.get(productId).getPricePerUnit());
        }
        return totalAmount;
    }

    @Override
    public Cart getCart(String userId) {
        Cart cart = shoppingCart.get(userId);
        if (cart == null) {
            cart = new Cart();
            cart.setId(UUID.randomUUID());
            cart.setUserId(userId);
            cart.setCreatedDate(LocalDate.now());
            cart.setTotalAmount(0);
            cart.setSelectedProducts(new HashMap<>());
            shoppingCart.put(userId, cart);
        }
        return cart;
    }

    @Override
    public void clearCart(String userId) {
        if (shoppingCart.get(userId) != null) {
            shoppingCart.remove(userId);
        }
    }

    @Override
    public Cart create(String userId) {
        return getCart(userId);
    }

}
