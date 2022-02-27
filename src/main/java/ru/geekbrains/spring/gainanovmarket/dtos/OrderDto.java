package ru.geekbrains.spring.gainanovmarket.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.geekbrains.spring.gainanovmarket.entities.OrderItem;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private List<OrderItem> items;
    private int totalPrice;
    private LocalDateTime createdAt;
}
