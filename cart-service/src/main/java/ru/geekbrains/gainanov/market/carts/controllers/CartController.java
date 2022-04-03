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
    public CartDto getCart(@RequestHeader(required = false) String username) {
        return cartConverter.entityToDto(cartService.getCurrentCart(username));
    }

    @GetMapping("add/{id}")
    public void addProduct(@RequestHeader(required = false) String username, @PathVariable Long id) {
        cartService.addProduct(username, id);
    }

    @GetMapping("decrease/{id}")
    public void removeProduct(@RequestHeader(required = false) String username, @PathVariable Long id) {
        cartService.removeProduct(username, id);
    }

    @GetMapping("clear")
    public void clearCart(@RequestHeader(required = false) String username) {
        cartService.removeAll(username);
    }

    @GetMapping("remove-line/{id}")
    public void removeLine(@RequestHeader(required = false) String username, @PathVariable Long id) {
        cartService.removeLine(username, id);
    }
}
