package com.marketplace.cartservice.service;


import org.springframework.stereotype.Service;


import com.marketplace.cartservice.entity.*;
import com.marketplace.cartservice.repository.*;

@Service
public class CartService {


    private final CartRepository cartRepo;


    public CartService(CartRepository cartRepo) {
        this.cartRepo = cartRepo;
    }


    private Cart getOrCreateCart(String username) {
        return cartRepo.findByUsername(username)
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUsername(username);
                    return cartRepo.save(cart);
                });
    }

public Cart getCart(String username) {
    return getOrCreateCart(username);
}


public Cart addItem(String username, Long productId, int quantity) {
    Cart cart = getOrCreateCart(username);


    for (CartItem item : cart.getItems()) {
        if (item.getProductId().equals(productId)) {
            item.setQuantity(item.getQuantity() + quantity);
            return cartRepo.save(cart);
        }
    }


    CartItem item = new CartItem();
    item.setProductId(productId);
    item.setQuantity(quantity);
    item.setCart(cart);
    cart.getItems().add(item);


    return cartRepo.save(cart);
}


public Cart updateItem(String username, Long productId, int quantity) {
    Cart cart = getOrCreateCart(username);


    cart.getItems().removeIf(item -> {
        if (item.getProductId().equals(productId)) {
            if (quantity <= 0) return true;
            item.setQuantity(quantity);
        }
        return false;
    });


    return cartRepo.save(cart);
}


public Cart removeItem(String username, Long productId) {
    Cart cart = getOrCreateCart(username);
    cart.getItems().removeIf(item -> item.getProductId().equals(productId));
    return cartRepo.save(cart);
}


public void clearCart(String username) {
    Cart cart = getOrCreateCart(username);
    cart.getItems().clear();
    cartRepo.save(cart);
}
}