package ru.geekbrains.gainanov.market.carts.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.geekbrains.gainanov.market.api.ProductDto;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductServiceIntegration {
    private final RestTemplate restTemplate;

    @Value("${ms-urls.market-products}")
    private String baseUrl;

    public Optional<ProductDto> getProductById(Long id) {
        return Optional.ofNullable(
                restTemplate.getForObject(baseUrl+ "/" + id, ProductDto.class)
        );
    }
}
