package ru.geekbrains.spring.gainanovmarket.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.geekbrains.spring.gainanovmarket.dtos.OrderDto;
import ru.geekbrains.spring.gainanovmarket.dtos.OrderItemDto;
import ru.geekbrains.spring.gainanovmarket.entities.Order;
import ru.geekbrains.spring.gainanovmarket.entities.OrderItem;

@Component
@RequiredArgsConstructor
public class OrderItemConverter {
    private final ProductConverter productConverter;

    public OrderItemDto entityToDto(OrderItem orderItem) {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setId(orderItem.getId());
        orderItemDto.setProductDto(productConverter.entityToDto(orderItem.getProduct()));
        orderItemDto.setOrderId(orderItem.getOrder().getId());
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setPricePerProduct(orderItem.getPricePerProduct());
        orderItemDto.setPrice(orderItem.getPrice());
        return orderItemDto;
    }

}
