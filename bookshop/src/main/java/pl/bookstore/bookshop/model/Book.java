package pl.bookstore.bookshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import pl.bookstore.model.BookType;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "book_table")
public class Book {
    @Id
    @GeneratedValue
    private UUID bookId;
    private String title;

    @ManyToOne
    private Author author;

    private BookType bookType;

    private Integer pages;
    private Double price;

    private Integer visitorCount;
}
