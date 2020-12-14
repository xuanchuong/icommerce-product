package com.icommerce.product.domain.repository;

import com.icommerce.product.domain.entity.Cart;
import com.icommerce.product.domain.entity.Product;

public interface CartRepository {

    Cart addToCart(String userId, Product product, Integer quantity);

    Cart getCart(String userId);

    void clearCart(String userId);
    
    Cart create(String userId);

}
