package ru.geekbrains.spring.gainanovmarket.dtos;


import lombok.Data;
import ru.geekbrains.spring.gainanovmarket.entities.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class Cart {
    private List<CartItem> items;
    private int totalPrice;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void addProduct(Product product) {
        boolean itemExisted = false;
        for (int i = 0; i < items.size(); i++) {
            CartItem existingItem = items.get(i);
            if (existingItem.getProductId().equals(product.getId())) {
                existingItem.setQuantity(existingItem.getQuantity() + 1);
                existingItem.setPrice(existingItem.getPrice() + product.getPrice());
                itemExisted = true;
            }
        }
        if (!itemExisted) {
            items.add(
                    new CartItem(product.getId(), product.getTitle(), 1, product.getPrice(), product.getPrice())
            );
        }
        recalculate();
    }

    public void removeProduct(Product product) {
        for (int i = 0; i < items.size(); i++) {
            CartItem existingItem = items.get(i);
            if (existingItem.getProductId().equals(product.getId()) && existingItem.getQuantity() > 1) {
                existingItem.setQuantity(existingItem.getQuantity() - 1);
                existingItem.setPrice(existingItem.getPrice() - product.getPrice());
            } else if (existingItem.getProductId().equals(product.getId())) {
                items.remove(i);
            }
        }
        recalculate();
    }

    public void removeAll() {
        items.clear();
        recalculate();
    }

    public void removeLine(Product product) {
        for (int i = 0; i < items.size(); i++) {
            CartItem existingItem = items.get(i);
            if (existingItem.getProductId().equals(product.getId())) {
                items.remove(i);
            }
        }
        recalculate();
    }


    private void recalculate() {
        totalPrice = 0;
        for (CartItem i : items) {
            totalPrice += i.getPrice();

        }
    }
}
