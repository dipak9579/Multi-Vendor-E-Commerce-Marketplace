package com.marketplace.cartservice.controller;


import org.springframework.web.bind.annotation.*;


import com.marketplace.cartservice.dto.AddToCartRequest;
import com.marketplace.cartservice.entity.Cart;
import com.marketplace.cartservice.service.CartService;


@RestController
@RequestMapping("/api/cart")
public class CartController {


    private final CartService service;


    public CartController(CartService service) {
        this.service = service;
    }


    @GetMapping
    public Cart viewCart(@RequestHeader("X-USER") String username) {
        return service.getCart(username);
    }


    @PostMapping("/add")
    public Cart addToCart(@RequestHeader("X-USER") String username,
                          @RequestBody AddToCartRequest req) {
        return service.addItem(username, req.getProductId(), req.getQuantity());
    }


    @PutMapping("/update")
    public Cart updateItem(@RequestHeader("X-USER") String username,
                           @RequestBody AddToCartRequest req) {
        return service.updateItem(username, req.getProductId(), req.getQuantity());
    }


    @DeleteMapping("/remove/{productId}")
    public Cart removeItem(@RequestHeader("X-USER") String username,
                           @PathVariable Long productId) {
        return service.removeItem(username, productId);
    }
}