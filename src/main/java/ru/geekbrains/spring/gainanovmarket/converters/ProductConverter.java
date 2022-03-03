package ru.geekbrains.spring.gainanovmarket.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.geekbrains.spring.gainanovmarket.dtos.ProductDto;
import ru.geekbrains.spring.gainanovmarket.entities.Category;
import ru.geekbrains.spring.gainanovmarket.entities.Product;
import ru.geekbrains.spring.gainanovmarket.exceptions.ResourceNotFoundException;
import ru.geekbrains.spring.gainanovmarket.services.CategoryService;

@Component
@RequiredArgsConstructor
public class ProductConverter {
    private final CategoryService categoryService;

    public ProductDto entityToDto(Product p) {
        return new ProductDto(p.getId(), p.getTitle(), p.getPrice(), p.getCategory().getTitle());
    }

    public Product dtoToEntity(ProductDto productDto) {
        Product p = new Product();
        p.setId(productDto.getId());
        p.setTitle(productDto.getTitle());
        p.setPrice(productDto.getPrice());

        Category c = categoryService.findByTitle(productDto.getCategoryTitle()).orElseThrow(
                () -> new ResourceNotFoundException("Category with title: " + productDto.getTitle() + " not found.")
        );
        p.setCategory(c);
        return p;
    }
}
