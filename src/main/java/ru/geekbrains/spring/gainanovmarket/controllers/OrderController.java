package ru.geekbrains.spring.gainanovmarket.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.spring.gainanovmarket.entities.User;
import ru.geekbrains.spring.gainanovmarket.model.OrderData;
import ru.geekbrains.spring.gainanovmarket.services.OrderService;
import ru.geekbrains.spring.gainanovmarket.services.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final UserService userService;
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createdOrder(Principal principal, @RequestBody OrderData orderData) { // TODO
        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new RuntimeException());
        orderService.createOrder(user, orderData);
    }
}
