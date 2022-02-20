package ru.geekbrains.spring.gainanovmarket.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.spring.gainanovmarket.dtos.ProductDto;
import ru.geekbrains.spring.gainanovmarket.entities.Product;
import ru.geekbrains.spring.gainanovmarket.exceptions.ResourceNotFoundException;
import ru.geekbrains.spring.gainanovmarket.services.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public List<ProductDto> findAll() {
        return productService.findAll().stream().map(
                p -> new ProductDto(p.getId(), p.getTitle(), p.getPrice())).collect(Collectors.toList()
        );
    }

    @GetMapping("/{id}")
    public ProductDto findById(@PathVariable Long id) {
        Product p =  productService.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product with id: " + id + " not found.")
        );
        return new ProductDto(p.getId(), p.getTitle(), p.getPrice());
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }
}
