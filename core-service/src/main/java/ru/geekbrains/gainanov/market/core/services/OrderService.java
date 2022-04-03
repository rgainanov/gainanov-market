package ru.geekbrains.gainanov.market.core.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.gainanov.market.api.CartDto;
import ru.geekbrains.gainanov.market.api.OrderDto;
import ru.geekbrains.gainanov.market.core.entities.Order;
import ru.geekbrains.gainanov.market.core.entities.OrderItem;
import ru.geekbrains.gainanov.market.core.integrations.CartServiceIntegration;
import ru.geekbrains.gainanov.market.core.repositories.OrderRepository;
import ru.geekbrains.gainanov.market.core.converters.OrderConverter;
import ru.geekbrains.gainanov.market.core.model.OrderData;
import ru.geekbrains.gainanov.market.core.repositories.OrderItemRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
    private final CartServiceIntegration cartServiceIntegration;
    private final ProductService productService;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderConverter orderConverter;

    @Transactional
    public Order createOrder(String username, OrderData orderData) {
        CartDto cartDto = cartServiceIntegration.getCartDto(username);

        Order order = new Order();
        order.setUsername(username);
        order.setAddress(orderData.getAddress());
        order.setPhone(orderData.getPhone());
        order.setTotalPrice(cartDto.getTotalPrice());
        order.setItems(cartDto.getItems().stream().map(
                cartItem -> new OrderItem(
                        productService.findById(cartItem.getProductId()).get(),
                        order,
                        cartItem.getQuantity(),
                        cartItem.getPricePerProduct(),
                        cartItem.getPrice()
                )
        ).collect(Collectors.toList()));
        orderRepository.save(order);

        cartServiceIntegration.clearCart(username);
        return order;
    }

    public List<OrderDto> findAllOrdersByUser(String username) {
        return orderRepository.findAllByUsername(username).stream().map(orderConverter::entityToDto).collect(Collectors.toList());
    }
}

