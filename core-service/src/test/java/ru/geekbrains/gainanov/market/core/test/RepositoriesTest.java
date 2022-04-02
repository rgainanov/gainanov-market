package ru.geekbrains.gainanov.market.core.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import ru.geekbrains.gainanov.market.core.entities.Product;
import ru.geekbrains.gainanov.market.core.repositories.ProductRepository;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
public class RepositoriesTest {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void productRepositoryTest() {
        List<Product> products = productRepository.findAll();

        Assertions.assertEquals(3, products.size());
        Assertions.assertEquals("Bread", products.get(1).getTitle());
    }

}
