package ru.geekbrains.spring.gainanovmarket.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import ru.geekbrains.spring.gainanovmarket.entities.Order;
import ru.geekbrains.spring.gainanovmarket.entities.Product;

import javax.persistence.*;
import java.time.LocalDateTime;

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
