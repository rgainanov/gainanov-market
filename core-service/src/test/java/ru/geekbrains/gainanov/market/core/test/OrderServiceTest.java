package ru.geekbrains.gainanov.market.core.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.geekbrains.gainanov.market.api.CartDto;
import ru.geekbrains.gainanov.market.api.CartItemDto;
import ru.geekbrains.gainanov.market.core.entities.Category;
import ru.geekbrains.gainanov.market.core.entities.Order;
import ru.geekbrains.gainanov.market.core.entities.Product;
import ru.geekbrains.gainanov.market.core.integrations.CartServiceIntegration;
import ru.geekbrains.gainanov.market.core.model.OrderData;
import ru.geekbrains.gainanov.market.core.repositories.OrderRepository;
import ru.geekbrains.gainanov.market.core.services.OrderService;
import ru.geekbrains.gainanov.market.core.services.ProductService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@SpringBootTest
public class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @MockBean
    private CartServiceIntegration cartServiceIntegration;

    @MockBean
    private ProductService productService;

    @MockBean
    private OrderRepository orderRepository;

    @Test
    public void createOrderTest() {
        CartDto cartDto = new CartDto();
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setProductId(1231L);
        cartItemDto.setProductTitle("Juice");
        cartItemDto.setPricePerProduct(new BigDecimal("1.55"));
        cartItemDto.setQuantity(2);
        cartItemDto.setPrice(new BigDecimal("3.10"));
        cartDto.setTotalPrice(new BigDecimal("3.10"));
        cartDto.setItems(List.of(cartItemDto));

        Mockito.doReturn(cartDto).when(cartServiceIntegration).getCartDto();

        Category category = new Category();
        category.setId(10L);
        category.setTitle("Beverages");

        Product product = new Product();
        product.setId(1231L);
        product.setPrice(new BigDecimal("1.55"));
        product.setTitle("Juice");
        product.setCategory(category);

        Mockito.doReturn(Optional.of(product)).when(productService).findById(1231L);

        Order order = orderService.createOrder(
                "bob", new OrderData("test@test.com", "+1231231231")
        );
        Assertions.assertEquals(new BigDecimal("3.10"), order.getTotalPrice());
        Mockito.verify(orderRepository, Mockito.times(1)).save(ArgumentMatchers.any());
    }
}
