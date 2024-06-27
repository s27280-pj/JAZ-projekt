package pl.bookstore.bookshop.service;

import org.springframework.data.jpa.domain.Specification;
import pl.bookstore.bookshop.model.Author;
import pl.bookstore.bookshop.model.Book;
import pl.bookstore.model.BookDetails;
import pl.bookstore.model.BookType;

public class BookSpecifications {

    public static Specification<Book> hasTitle(String title) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("title"), title);
    }

    public static Specification<Book> hasBookType(BookType bookType) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("bookType"), bookType);
    }

    public static Specification<Book> hasPagesLessThanOrEqualTo(Integer pages) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("pages"), pages);
    }

    public static Specification<Book> hasPriceLessThanOrEqualTo(Double price) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), price);
    }
}