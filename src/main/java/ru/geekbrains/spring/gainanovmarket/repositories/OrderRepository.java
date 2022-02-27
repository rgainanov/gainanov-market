package ru.geekbrains.spring.gainanovmarket.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.spring.gainanovmarket.entities.Order;
import ru.geekbrains.spring.gainanovmarket.entities.User;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findAllByUser(User user);
}