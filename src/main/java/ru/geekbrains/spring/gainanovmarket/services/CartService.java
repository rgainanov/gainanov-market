package ru.geekbrains.spring.gainanovmarket.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.spring.gainanovmarket.dtos.Cart;
import ru.geekbrains.spring.gainanovmarket.entities.Product;
import ru.geekbrains.spring.gainanovmarket.exceptions.ResourceNotFoundException;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductService productService;
    private Cart tempCart;

    @PostConstruct
    public void init() {
        tempCart = new Cart();
    }

    public Cart getCurrentCart() {
        return tempCart;
    }

    public void addProduct(Long productId) {
        Product product = productService.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Can't add this product to Cart. No such product"));
        tempCart.addProduct(product);
    }

    public void removeProduct(Long productId) {
        Product product = productService.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Can't remove this product from Cart. No such product"));
        tempCart.removeProduct(product);
    }

    public void removeAll() {
        tempCart.removeAll();
    }

    public void removeLine(Long productId) {
        Product product = productService.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Can't remove this product line from Cart. No such product"));
        tempCart.removeLine(product);
    }

}
