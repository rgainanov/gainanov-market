package ru.geekbrains.spring.gainanovmarket.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.spring.gainanovmarket.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
