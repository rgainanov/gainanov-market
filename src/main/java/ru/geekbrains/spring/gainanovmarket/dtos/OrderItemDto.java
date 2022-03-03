package ru.geekbrains.spring.gainanovmarket.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
    private Long id;
    private ProductDto productDto;
    private Long orderId;
    private int quantity;
    private int pricePerProduct;
    private int price;
}
