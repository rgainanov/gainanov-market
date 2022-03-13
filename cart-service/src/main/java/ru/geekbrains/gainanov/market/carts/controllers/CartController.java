package ru.geekbrains.gainanov.market.carts.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.gainanov.market.api.CartDto;
import ru.geekbrains.gainanov.market.carts.converters.CartConverter;
import ru.geekbrains.gainanov.market.carts.services.CartService;


@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping
    public CartDto getCart() {
        return cartConverter.entityToDto(cartService.getCurrentCart());
    }

    @GetMapping("add/{id}")
    public void addProduct(@PathVariable Long id) {
        cartService.addProduct(id);
    }

    @GetMapping("decrease/{id}")
    public void removeProduct(@PathVariable Long id) {
        cartService.removeProduct(id);
    }

    @GetMapping("clear")
    public void clearCart() {
        cartService.removeAll();
    }

    @GetMapping("remove-line/{id}")
    public void removeLine(@PathVariable Long id) {
        cartService.removeLine(id);
    }
}
