package ru.geekbrains.gainanov.market.core.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.gainanov.market.api.OrderDto;
import ru.geekbrains.gainanov.market.api.ProductDto;
import ru.geekbrains.gainanov.market.core.model.OrderData;
import ru.geekbrains.gainanov.market.core.services.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "Methods for interacting with orders")
public class OrderController {
    private final OrderService orderService;

    @Operation(
            summary = "Create Order",
            responses = {
                    @ApiResponse(
                            description = "Success", responseCode = "200"
                    )
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createdOrder(@RequestHeader String username, @RequestBody OrderData orderData) {
        orderService.createOrder(username, orderData);
    }

    @Operation(
            summary = "Get User Orders",
            responses = {
                    @ApiResponse(
                            description = "Success", responseCode = "200",
                            content = @Content(
                                    array = @ArraySchema(schema = @Schema(implementation = OrderDto.class))
                            )
                    )
            }
    )
    @GetMapping
    public List<OrderDto> getOrders(@RequestHeader String username) {
        return orderService.findAllOrdersByUser(username);
    }
}
