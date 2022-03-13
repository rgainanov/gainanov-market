package ru.geekbrains.gainanov.market.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.gainanov.market.api.OrderDto;
import ru.geekbrains.gainanov.market.api.ResourceNotFoundException;
import ru.geekbrains.gainanov.market.core.entities.User;
import ru.geekbrains.gainanov.market.core.model.OrderData;
import ru.geekbrains.gainanov.market.core.services.OrderService;
import ru.geekbrains.gainanov.market.core.services.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@CrossOrigin("*")
public class OrderController {
    private final UserService userService;
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createdOrder(Principal principal, @RequestBody OrderData orderData) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(
                () -> new ResourceNotFoundException("User with name: " + principal.getName() + " not found."));
        orderService.createOrder(user, orderData);
    }

    @GetMapping
    public List<OrderDto> getOrders(Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(
                () -> new ResourceNotFoundException("User with name: " + principal.getName() + " not found."));
        return orderService.findAllOrdersByUser(user);
    }
}
