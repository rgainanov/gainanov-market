package ru.geekbrains.gainanov.market.carts.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.geekbrains.gainanov.market.api.CartDto;
import ru.geekbrains.gainanov.market.carts.models.Cart;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CartConverter {
    private final CartItemConverter cartItemConverter;

    public CartDto entityToDto(Cart cart) {
        CartDto cartDto = new CartDto();
        cartDto.setItems(cart.getItems().stream().map(cartItemConverter::entityToDto).collect(Collectors.toList()));
        cartDto.setTotalPrice(cart.getTotalPrice());
        return cartDto;
    }
}
