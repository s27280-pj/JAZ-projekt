package pl.bookstore.bookshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.bookstore.bookshop.model.Book;
import pl.bookstore.model.BookDetails;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID>, JpaSpecificationExecutor<Book> {

    List<Book> findAllByVisitorCountGreaterThan(@Param("visitorCount") Integer visitorCount);
}
