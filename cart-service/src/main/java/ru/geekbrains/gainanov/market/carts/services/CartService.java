package ru.geekbrains.gainanov.market.carts.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.geekbrains.gainanov.market.api.ProductDto;
import ru.geekbrains.gainanov.market.carts.integrations.ProductServiceIntegration;
import ru.geekbrains.gainanov.market.carts.models.Cart;
import ru.geekbrains.gainanov.market.carts.models.CartItem;

import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductServiceIntegration productServiceIntegration;
    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${cart-service.cart-prefix}")
    private String cartPrefix;

    public Cart getCurrentCart(String uuid) {
        String targetUuid = cartPrefix + uuid;
        if (!redisTemplate.hasKey(targetUuid)) {
            redisTemplate.opsForValue().set(targetUuid, new Cart());
        }
        return (Cart) redisTemplate.opsForValue().get(targetUuid);
    }

    public void mergeCarts(String username, String uuid) {
        Cart userCart = getCurrentCart(username);
        Cart unownedCart = getCurrentCart(uuid);

        for (CartItem ci : unownedCart.getItems()) {
            userCart.addCartItem(ci);
        }

        removeAll(uuid);
        redisTemplate.opsForValue().set(cartPrefix + username, userCart);
    }

    public void addProduct(String uuid, Long productId) {
        ProductDto product = productServiceIntegration.getProductById(productId);
        execute(uuid, cart -> cart.addProduct(product));
    }

    public void removeProduct(String uuid, Long productId) {
        ProductDto product = productServiceIntegration.getProductById(productId);
        execute(uuid, cart -> cart.removeProduct(product));
    }

    public void removeAll(String uuid) {
        execute(uuid, Cart::removeAll);
    }

    public void removeLine(String uuid, Long productId) {
        execute(uuid, cart -> cart.removeLine(productId));
    }

    private void execute(String uuid, Consumer<Cart> operation) {
        String targetUuid = cartPrefix + uuid;
        Cart cart = getCurrentCart(uuid);
        operation.accept(cart);
        redisTemplate.opsForValue().set(targetUuid, cart);
    }

}
