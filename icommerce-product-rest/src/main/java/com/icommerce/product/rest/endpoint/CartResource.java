package com.icommerce.product.rest.endpoint;

import com.icommerce.product.application.service.CartService;
import com.icommerce.product.application.service.UserService;
import com.icommerce.product.domain.entity.Cart;
import io.github.jhipster.web.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CartResource {

    private final Logger log = LoggerFactory.getLogger(CartResource.class);

    private static final String ENTITY_NAME = "productCart";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CartService cartService;

    public CartResource(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/carts/{productId}/{quantity}")
    public ResponseEntity<Cart> addToCart(@PathVariable("productId") String productId,
                                          @PathVariable("quantity") Integer quantity) throws URISyntaxException {
        log.debug("REST request to save Cart product Id : {} , {} units", productId, quantity);
        String user = UserService.getCurrentUserLogin().orElse("");
        Cart result = cartService.addToCart(user, productId, quantity);
        return ResponseEntity.created(new URI("/api/carts/" + productId))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, productId))
            .body(result);
    }

    @GetMapping("/carts")
    public ResponseEntity<List<Cart>> getCart() {
        String user = UserService.getCurrentUserLogin().orElse("");
        return ResponseEntity.ok(Collections.singletonList(cartService.getCart(user)));
    }

    @PostMapping("/carts")
    public ResponseEntity<Cart> createCart() {
        String user = UserService.getCurrentUserLogin().orElse("");
        Cart cart = cartService.createCart(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(cart);
    }

}
