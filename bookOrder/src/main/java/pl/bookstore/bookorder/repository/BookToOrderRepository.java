package pl.bookstore.bookorder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bookstore.bookorder.model.BookToOrder;

import java.util.UUID;

@Repository
public interface BookToOrderRepository extends JpaRepository<BookToOrder, UUID> {
}
