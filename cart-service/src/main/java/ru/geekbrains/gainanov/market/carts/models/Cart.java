package ru.geekbrains.gainanov.market.carts.models;


import lombok.Data;
import ru.geekbrains.gainanov.market.api.ProductDto;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class Cart {
    private List<CartItem> items;
    private BigDecimal totalPrice;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void addProduct(ProductDto product) {
        for (CartItem ci : items) {
            if (ci.getProductId().equals(product.getId())) {
                ci.changeQuantity(1);
                recalculate();
                return;
            }
        }
        items.add(new CartItem(product.getId(), product.getTitle(), 1, product.getPrice(), product.getPrice()));
        recalculate();

    }

    public void removeProduct(ProductDto product) {
        for (CartItem ci : items) {
            if (ci.getProductId().equals(product.getId()) && ci.getQuantity() > 1) {
                ci.setQuantity(ci.getQuantity() - 1);
                ci.setPrice(ci.getPrice().subtract(product.getPrice()));
                recalculate();
                return;
            }
        }
        items.removeIf(i -> (i.getProductId().equals(product.getId())));
        recalculate();
    }

    public void removeAll() {
        items.clear();
        recalculate();
    }

    public void removeLine(Long productId) {
        if (items.removeIf(i -> (i.getProductId().equals(productId)))) {
            recalculate();
        }
    }


    private void recalculate() {
        totalPrice = new BigDecimal(0);
        for (CartItem i : items) {
            totalPrice = totalPrice.add(i.getPrice());

        }
    }
}
