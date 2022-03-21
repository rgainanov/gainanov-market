package ru.geekbrains.gainanov.market.core.repositories.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;
import ru.geekbrains.gainanov.market.core.entities.Product;

import java.math.BigDecimal;

public class ProductSpecifications {
    private static Specification<Product> priceGreaterOrEqualsThan(BigDecimal minPrice) {
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    private static Specification<Product> priceLessOrEqualsThan(BigDecimal maxPrice) {
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    private static Specification<Product> titleLike(String titlePart) {
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder
                .like(root.get("title"), String.format("%%%s%%", titlePart));
    }

    public static Specification<Product> build(MultiValueMap<String, String> params) {
        Specification<Product> spec = Specification.where(null);
        if (params.containsKey("minPrice") && !params.getFirst("minPrice").isBlank()) {
            spec = spec.and(ProductSpecifications.priceGreaterOrEqualsThan(new BigDecimal(params.getFirst("minPrice"))));
        }
        if (params.containsKey("maxPrice") && !params.getFirst("maxPrice").isBlank()) {
            spec = spec.and(ProductSpecifications.priceLessOrEqualsThan(new BigDecimal(params.getFirst("maxPrice"))));
        }
        if (params.containsKey("title") && !params.getFirst("title").isBlank()) {
            spec = spec.and(ProductSpecifications.titleLike(params.getFirst("title")));
        }
        return spec;
    }
}
