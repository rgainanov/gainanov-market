package ru.geekbrains.gainanov.market.core.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.gainanov.market.api.ProductDto;
import ru.geekbrains.gainanov.market.api.ResourceNotFoundException;
import ru.geekbrains.gainanov.market.core.converters.ProductConverter;
import ru.geekbrains.gainanov.market.core.entities.Product;
import ru.geekbrains.gainanov.market.core.repositories.specifications.ProductSpecifications;
import ru.geekbrains.gainanov.market.core.services.ProductService;


@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductConverter productConverter;

    @GetMapping
    public Page<ProductDto> findAll(
            @RequestParam MultiValueMap<String, String> params,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "1") int size
    ) {

        if (page < 1) {
            page = 1;
        }
        return productService.findAll(ProductSpecifications.build(params), page, size);
    }

    @GetMapping("/{id}")
    public ProductDto findById(@PathVariable Long id) {
        Product p = productService.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product with id: " + id + " not found.")
        );
        return productConverter.entityToDto(p);
    }

    @PostMapping
    public ProductDto createNewProduct(@RequestBody ProductDto productDto) {
        Product p = productService.createNewProduct(productDto);
        return productConverter.entityToDto(p);

    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }
}
