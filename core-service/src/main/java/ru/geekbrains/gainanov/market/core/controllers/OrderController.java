package ru.geekbrains.gainanov.market.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.gainanov.market.api.OrderDto;
import ru.geekbrains.gainanov.market.core.model.OrderData;
import ru.geekbrains.gainanov.market.core.services.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createdOrder(@RequestHeader String username, @RequestBody OrderData orderData) {
        orderService.createOrder(username, orderData);
    }

    @GetMapping
    public List<OrderDto> getOrders(@RequestHeader String username) {
        return orderService.findAllOrdersByUser(username);
    }
}
