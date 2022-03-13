package ru.geekbrains.gainanov.market.api;

import java.util.List;

public class OrderDto {
    private Long id;
    private List<OrderItemDto> items;
    private int totalPrice;

    public OrderDto() {
    }

    public OrderDto(Long id, List<OrderItemDto> items, int totalPrice) {
        this.id = id;
        this.items = items;
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
