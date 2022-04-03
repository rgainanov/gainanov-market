package ru.geekbrains.gainanov.market.carts.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.gainanov.market.api.ProductDto;
import ru.geekbrains.gainanov.market.carts.integrations.ProductServiceIntegration;
import ru.geekbrains.gainanov.market.carts.models.Cart;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productServiceIntegration;
    private Map<String, Cart> tempCarts;

    @PostConstruct
    public void init() {
        tempCarts = new HashMap<>();
        tempCarts.put("unified", new Cart());
    }

    public Cart getCurrentCart(String username) {
        if (username != null) {
            if (!tempCarts.containsKey(username)) {
                tempCarts.put(username, new Cart());
            }
            return tempCarts.get(username);
        }
        return tempCarts.get("unified");
    }

    public void addProduct(String username, Long productId) {
        ProductDto product = productServiceIntegration.getProductById(productId);
        if (username != null) {
            tempCarts.get(username).addProduct(product);
            return;
        }
        tempCarts.get("unified").addProduct(product);
    }

    public void removeProduct(String username, Long productId) {
        ProductDto product = productServiceIntegration.getProductById(productId);
        if (username != null) {
            tempCarts.get(username).removeProduct(product);
            return;
        }
        tempCarts.get("unified").removeProduct(product);
    }

    public void removeAll(String username) {
        if (username != null) {
            tempCarts.get(username).removeAll();
            return;
        }
        tempCarts.get("unified").removeAll();
    }

    public void removeLine(String username, Long productId) {
        if (username != null) {
            tempCarts.get(username).removeLine(productId);
            return;
        }
        tempCarts.get("unified").removeLine(productId);
    }

}
