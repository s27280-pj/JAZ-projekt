package pl.bookstore.bookshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.bookstore.bookshop.model.Order;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
