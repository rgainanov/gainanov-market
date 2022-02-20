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

    @GetMapping("add/{id}")
    public void add(@PathVariable Long id) {
        cartService.add(id);
    }

    @GetMapping("remove/{id}")
    public void remove(@PathVariable Long id) {
        cartService.remove(id);
    }

}
