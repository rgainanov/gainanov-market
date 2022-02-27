package ru.geekbrains.spring.gainanovmarket.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.spring.gainanovmarket.dtos.OrderDto;
import ru.geekbrains.spring.gainanovmarket.entities.User;
import ru.geekbrains.spring.gainanovmarket.exceptions.ResourceNotFoundException;
import ru.geekbrains.spring.gainanovmarket.model.OrderData;
import ru.geekbrains.spring.gainanovmarket.services.OrderService;
import ru.geekbrains.spring.gainanovmarket.services.UserService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final UserService userService;
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createdOrder(Principal principal, @RequestBody OrderData orderData) { // TODO
        User user = userService.findByUsername(principal.getName()).orElseThrow(
                () -> new ResourceNotFoundException("User with name: " + principal.getName() + " not found."));
        orderService.createOrder(user, orderData);
    }

    @GetMapping
    public List<OrderDto> getOrders(Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(
                () -> new ResourceNotFoundException("User with name: " + principal.getName() + " not found."));
        return orderService.findAllByUser(user);
    }
}
