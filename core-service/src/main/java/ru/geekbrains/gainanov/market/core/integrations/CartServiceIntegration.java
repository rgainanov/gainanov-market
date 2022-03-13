package ru.geekbrains.gainanov.market.core.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.geekbrains.gainanov.market.api.CartDto;

@Component
@RequiredArgsConstructor
public class CartServiceIntegration {
    private final RestTemplate restTemplate;
    @Value("${ms-urls.market-carts}")
    private String baseUrl;

    public CartDto getCartDto() {
        return restTemplate.getForObject(baseUrl, CartDto.class);
    }

    public Void clearCart() {
        return restTemplate.getForObject(baseUrl + "/clear", Void.class);
    }
}
