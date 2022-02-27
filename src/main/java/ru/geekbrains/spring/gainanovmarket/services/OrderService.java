package ru.geekbrains.spring.gainanovmarket.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.gainanovmarket.dtos.OrderDto;
import ru.geekbrains.spring.gainanovmarket.entities.Order;
import ru.geekbrains.spring.gainanovmarket.entities.OrderItem;
import ru.geekbrains.spring.gainanovmarket.entities.User;
import ru.geekbrains.spring.gainanovmarket.exceptions.ResourceNotFoundException;
import ru.geekbrains.spring.gainanovmarket.model.Cart;
import ru.geekbrains.spring.gainanovmarket.model.OrderData;
import ru.geekbrains.spring.gainanovmarket.repositories.OrderItemRepository;
import ru.geekbrains.spring.gainanovmarket.repositories.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CartService cartService;
    private final ProductService productService;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public void createOrder(User user, OrderData orderData) {
        Cart cart = cartService.getCurrentCart();
        Order order = new Order();
        order.setUser(user);
        order.setAddress(orderData.getAddress());
        order.setPhone(orderData.getPhone());
        order.setTotalPrice(cart.getTotalPrice());
        List<OrderItem> orderItemList = cart.getItems().stream().map(cartItem -> {
            OrderItem oi = new OrderItem();
            oi.setProduct(productService.findById(cartItem.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Can't add this product to Order Items. No such product")));
            oi.setOrder(order);
            oi.setPricePerProduct(cartItem.getPricePerProduct());
            oi.setPrice(cartItem.getPrice());
            oi.setQuantity(cartItem.getQuantity());
            return oi;
        }).collect(Collectors.toList());
        orderRepository.save(order);
        order.setItems(orderItemList);
        orderItemRepository.saveAllAndFlush(orderItemList);
    }

    public List<OrderDto> findAllByUser(User user) {
        List<OrderDto> orderDtoList = new ArrayList<>();
        System.out.println(orderRepository.findAllByUser(user));
//                orderRepository.findAllByUser(user).stream().map(OrderDto::new).collect(Collectors.toList());
        return orderDtoList;
    }
}

