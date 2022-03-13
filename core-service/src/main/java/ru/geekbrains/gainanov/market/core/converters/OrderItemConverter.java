package ru.geekbrains.gainanov.market.core.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.geekbrains.gainanov.market.api.OrderItemDto;
import ru.geekbrains.gainanov.market.core.entities.OrderItem;

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
