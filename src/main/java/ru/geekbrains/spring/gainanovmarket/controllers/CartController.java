package ru.geekbrains.spring.gainanovmarket.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.geekbrains.spring.gainanovmarket.dtos.Cart;
import ru.geekbrains.spring.gainanovmarket.services.CartService;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public Cart getCart() {
        return cartService.getCurrentCart();
    }

    @GetMapping("addProduct/{id}")
    public void addProduct(@PathVariable Long id) {
        cartService.addProduct(id);
    }

    @GetMapping("removeProduct/{id}")
    public void removeProduct(@PathVariable Long id) {
        cartService.removeProduct(id);
    }

    @GetMapping("clearCart")
    public void clearCart() {
        cartService.removeAll();
    }

    @GetMapping("removeLine/{id}")
    public void removeLine(@PathVariable Long id) {
        cartService.removeLine(id);
    }
}
