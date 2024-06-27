package pl.bookstore.bookshop.service;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.bookstore.bookshop.mapper.BookMapper;
import pl.bookstore.bookshop.model.Book;
import pl.bookstore.bookshop.repository.BookRepository;
import pl.bookstore.model.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper mapper;

    public BookService(BookRepository bookRepository, BookMapper mapper) {
        this.bookRepository = bookRepository;
        this.mapper = mapper;
    }

    public List<BookDetails> getAllBooks() {
        return bookRepository
                .findAll()
                .stream()
                .map(mapper::toDetails)
                .collect(Collectors.toList());
    }

    public BookDetails getBookById (UUID id) {
        incrementVisitCount(id);
        return mapper.toDetails(bookRepository.getReferenceById(id));
    }

    public List<BookDetails> filterAndSortBooks(String title, BookType bookType, Integer maxPages, Double maxPrice, String sortBy) {
        Specification<Book> spec = Specification.where(null);

        if (title != null) {
            spec = spec.and(BookSpecifications.hasTitle(title));
        }
        if (bookType != null) {
            spec = spec.and(BookSpecifications.hasBookType(bookType));
        }
        if (maxPages != null) {
            spec = spec.and(BookSpecifications.hasPagesLessThanOrEqualTo(maxPages));
        }
        if (maxPrice != null) {
            spec = spec.and(BookSpecifications.hasPriceLessThanOrEqualTo(maxPrice));
        }

        Sort sort = Sort.by(sortBy != null ? sortBy : "id");

        return bookRepository
                .findAll(spec, sort)
                .stream()
                .map(mapper::toDetails)
                .collect(Collectors.toList());
    }

    @Transactional
    public void incrementVisitCount(UUID id) {
        Book book = bookRepository.getReferenceById(id);
        book.setVisitorCount(book.getVisitorCount()+1);
        Book updated = book;
        bookRepository.delete(book);
        bookRepository.save(updated);
    }

    public BookDetails addNewBook (BookRequest bookRequest) {
        Book entity = mapper.toEntity(bookRequest);
        bookRepository.save(entity);
        return mapper.toDetails(entity);
    }

    public void deleteBook (UUID id) {
        bookRepository.delete(bookRepository.getReferenceById(id));
    }

    public BookDetails updateBook (UUID id, BookRequest bookRequest) {
        Book existingBook = bookRepository.getReferenceById(id);

        Book updatedBook = mapper.toEntity(bookRequest);
        updatedBook.setBookId(existingBook.getBookId());
        bookRepository.save(updatedBook);

        return mapper.toDetails(updatedBook);
    }
    public List<BookToOrderRequest> findAllByVisitorCountGreaterThan(int visitorCount) {
        List<Book> books = bookRepository.findAllByVisitorCountGreaterThan(visitorCount);
        return books.stream()
                .map(this::convertToBookToOrderDetails)
                .collect(Collectors.toList());
    }

    private BookToOrderRequest convertToBookToOrderDetails(Book book) {
        return new BookToOrderRequest(book.getBookId(),book.getTitle(), book.getVisitorCount());
    }

}
