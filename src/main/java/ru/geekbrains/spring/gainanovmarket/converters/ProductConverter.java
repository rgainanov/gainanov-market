package ru.geekbrains.spring.gainanovmarket.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.geekbrains.spring.gainanovmarket.dtos.ProductDto;
import ru.geekbrains.spring.gainanovmarket.entities.Category;
import ru.geekbrains.spring.gainanovmarket.entities.Product;
import ru.geekbrains.spring.gainanovmarket.exceptions.ResourceNotFoundException;
import ru.geekbrains.spring.gainanovmarket.services.CategoryService;
import ru.geekbrains.spring.gainanovmarket.soap.products.ProductWsDto;

@Component
@RequiredArgsConstructor
public class ProductConverter {
    private final CategoryService categoryService;

    public ProductDto entityToDto(Product p) {
        return new ProductDto(p.getId(), p.getTitle(), p.getPrice(), p.getCategory().getTitle());
    }

    public ProductWsDto entityToWs(Product p) {
        ProductWsDto pWs = new ProductWsDto();
        pWs.setId(p.getId());
        pWs.setTitle(p.getTitle());
        pWs.setPrice(p.getPrice());
        pWs.setCategoryTitle(p.getCategory().getTitle());
        return pWs;
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
