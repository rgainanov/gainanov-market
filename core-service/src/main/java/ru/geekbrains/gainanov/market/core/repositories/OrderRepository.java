package ru.geekbrains.gainanov.market.core.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.gainanov.market.core.entities.Order;
import ru.geekbrains.gainanov.market.core.entities.User;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findAllByUser(User user);
}