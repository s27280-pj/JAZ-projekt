package pl.bookstore.bookorder.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.bookstore.api.BookToOrderApi;
import pl.bookstore.bookorder.service.BookToOrderService;
import pl.bookstore.model.BookToOrderDetails;
import pl.bookstore.model.BookToOrderRequest;

import java.util.List;

@RestController
public class BookToOrderController implements BookToOrderApi {

    private final BookToOrderService bookToOrderService;

    public BookToOrderController(BookToOrderService bookToOrderService) {
        this.bookToOrderService = bookToOrderService;
    }

    @Override
    public ResponseEntity<List<BookToOrderDetails>> sendBookToOrder(List<BookToOrderRequest> bookToOrderRequest) {
        return ResponseEntity.ok(bookToOrderService.addNewBooksToOrder(bookToOrderRequest));
    }

    @Override
    public ResponseEntity<Resource> generatePDFReport() {
        byte[] pdfContent = bookToOrderService.generatePdf();

        ByteArrayResource resource = new ByteArrayResource(pdfContent);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "books.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }
}
