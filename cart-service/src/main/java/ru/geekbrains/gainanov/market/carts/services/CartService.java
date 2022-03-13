package ru.geekbrains.gainanov.market.carts.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.gainanov.market.api.ProductDto;
import ru.geekbrains.gainanov.market.api.ResourceNotFoundException;
import ru.geekbrains.gainanov.market.carts.integrations.ProductServiceIntegration;
import ru.geekbrains.gainanov.market.carts.models.Cart;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productServiceIntegration;
    private Cart tempCart;

    @PostConstruct
    public void init() {
        tempCart = new Cart();
    }

    public Cart getCurrentCart() {
        return tempCart;
    }

    public void addProduct(Long productId) {
        ProductDto product = productServiceIntegration.getProductById(productId).orElseThrow(() -> new ResourceNotFoundException("Can't add this product to Cart. No such product"));
        tempCart.addProduct(product);
    }

    public void removeProduct(Long productId) {
        ProductDto product = productServiceIntegration.getProductById(productId).orElseThrow(() -> new ResourceNotFoundException("Can't remove this product from Cart. No such product"));
        tempCart.removeProduct(product);
    }

    public void removeAll() {
        tempCart.removeAll();
    }

    public void removeLine(Long productId) {
        tempCart.removeLine(productId);
    }

}
