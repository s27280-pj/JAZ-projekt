package pl.bookstore.bookshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.bookstore.api.BooksApi;
import pl.bookstore.bookshop.feign.OrderReportFeign;
import pl.bookstore.bookshop.model.Book;
import pl.bookstore.bookshop.service.BookService;
import pl.bookstore.model.*;

import java.util.List;
import java.util.UUID;

@RestController
public class BookController implements BooksApi {
    private final BookService bookService;
    private final OrderReportFeign orderReportFeign;

    @Autowired
    public BookController(BookService bookService, OrderReportFeign orderReportFeign) {
        this.bookService = bookService;
        this.orderReportFeign = orderReportFeign;
    }

    @Override
    public ResponseEntity<BookDetails> addNewBook(BookRequest bookRequest) {
        return ResponseEntity.ok(bookService.addNewBook(bookRequest));
    }

    @Override
    public ResponseEntity<Void> deleteBook(UUID id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<BookDetails>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @Override
    public ResponseEntity<BookDetails> getBookById(UUID id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @Override
    public ResponseEntity<BookDetails> updateBook(UUID id, BookRequest bookRequest) {
        return ResponseEntity.ok(bookService.updateBook(id, bookRequest));
    }

    @Override
    public ResponseEntity<List<BookDetails>> filterBooks(String title, BookType bookType, Integer maxPages, Double maxPrice, String sortBy) {
        return ResponseEntity.ok(bookService.filterAndSortBooks(title, bookType, maxPages, maxPrice, sortBy));
    }

    @PostMapping("/order-report")
    ResponseEntity<List<BookToOrderRequest>> sendBookToOrderModule(){
        List<BookToOrderRequest> allByVisitorCountGreaterThan = bookService.findAllByVisitorCountGreaterThan(9);
        return orderReportFeign.sendBookToOrder(allByVisitorCountGreaterThan);
    }

    @GetMapping("/order-report/print")
    ResponseEntity<Resource> getPdfWithOrder(){
        return orderReportFeign.getPdfWithOrder();
    }


}
