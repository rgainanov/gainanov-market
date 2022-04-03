package ru.geekbrains.gainanov.market.carts.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.gainanov.market.api.CartDto;
import ru.geekbrains.gainanov.market.api.StringResponse;
import ru.geekbrains.gainanov.market.carts.converters.CartConverter;
import ru.geekbrains.gainanov.market.carts.services.CartService;

import java.util.UUID;


@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final CartConverter cartConverter;

    @GetMapping("/generate-uuid")
    public StringResponse generateUuid() {
        return new StringResponse(UUID.randomUUID().toString());
    }

    @GetMapping("/{uuid}")
    public CartDto getCart(
            @RequestHeader(name = "username", required = false) String username,
            @PathVariable String uuid
    ) {
        String targetUuid = getCartUuid(username, uuid);
        if (username != null) {
            cartService.mergeCarts(username, uuid);
        }
        return cartConverter.entityToDto(cartService.getCurrentCart(targetUuid));
    }

    @GetMapping("/{uuid}/add/{id}")
    public void addProduct(
            @RequestHeader(name = "username", required = false) String username,
            @PathVariable String uuid,
            @PathVariable Long id
    ) {
        String targetUuid = getCartUuid(username, uuid);
        cartService.addProduct(targetUuid, id);
    }

    @GetMapping("/{uuid}/decrease/{id}")
    public void removeProduct(
            @RequestHeader(name = "username", required = false) String username,
            @PathVariable String uuid,
            @PathVariable Long id
    ) {
        String targetUuid = getCartUuid(username, uuid);
        cartService.removeProduct(targetUuid, id);
    }

    @GetMapping("/{uuid}/clear")
    public void clearCart(
            @RequestHeader(name = "username", required = false) String username,
            @PathVariable String uuid
    ) {
        String targetUuid = getCartUuid(username, uuid);
        cartService.removeAll(targetUuid);
    }

    @GetMapping("/{uuid}/remove-line/{id}")
    public void removeLine(
            @RequestHeader(name = "username", required = false) String username,
            @PathVariable String uuid,
            @PathVariable Long id
    ) {
        String targetUuid = getCartUuid(username, uuid);
        cartService.removeLine(targetUuid, id);
    }

    private String getCartUuid(String username, String uuid) {
        if (username != null) {
            return username;
        }
        return uuid;
    }
}
