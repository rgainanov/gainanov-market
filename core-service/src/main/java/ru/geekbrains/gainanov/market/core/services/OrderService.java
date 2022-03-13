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
import ru.geekbrains.gainanov.market.core.entities.User;
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
    public void createOrder(User user, OrderData orderData) {
        CartDto cartDto = cartServiceIntegration.getCartDto();

        Order order = new Order();
        order.setUser(user);
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

        cartServiceIntegration.clearCart();

//        Cart cart = cartService.getCurrentCart();
//        Order order = new Order();
//        order.setUser(user);
//        order.setAddress(orderData.getAddress());
//        order.setPhone(orderData.getPhone());
//        order.setTotalPrice(cart.getTotalPrice());
//        List<OrderItem> orderItemList = cart.getItems().stream().map(cartItem -> {
//            OrderItem oi = new OrderItem();
//            oi.setProduct(productService.findById(cartItem.getProductId())
//                    .orElseThrow(() -> new ResourceNotFoundException("Can't add this product to Order Items. No such product")));
//            oi.setOrder(order);
//            oi.setPricePerProduct(cartItem.getPricePerProduct());
//            oi.setPrice(cartItem.getPrice());
//            oi.setQuantity(cartItem.getQuantity());
//            return oi;
//        }).collect(Collectors.toList());
//        orderRepository.save(order);
//        order.setItems(orderItemList);
//        orderItemRepository.saveAllAndFlush(orderItemList);
    }

    public List<OrderDto> findAllOrdersByUser(User user) {
        return orderRepository.findAllByUser(user).stream().map(orderConverter::entityToDto).collect(Collectors.toList());
    }
}

