package pl.bookstore.bookorder.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Service;
import pl.bookstore.bookorder.mapper.BookToOrderMapper;
import pl.bookstore.bookorder.model.BookToOrder;
import pl.bookstore.bookorder.repository.BookToOrderRepository;
import pl.bookstore.model.BookToOrderDetails;
import pl.bookstore.model.BookToOrderRequest;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookToOrderService {

    private final BookToOrderRepository bookToOrderRepository;
    private final BookToOrderMapper mapper;

    public BookToOrderService(BookToOrderRepository bookToOrderRepository, BookToOrderMapper mapper) {
        this.bookToOrderRepository = bookToOrderRepository;
        this.mapper = mapper;
    }

    public List<BookToOrderDetails> addNewBooksToOrder(List<BookToOrderRequest> bookToOrderRequests) {
        List<BookToOrder> processedBooks = new ArrayList<>();

        for (BookToOrderRequest bookToOrderRequest : bookToOrderRequests) {
            BookToOrder book = mapper.toEntity(bookToOrderRequest);
            Optional<BookToOrder> existingBookToOrder = bookToOrderRepository.findById(book.getBookId());
            if (existingBookToOrder.isPresent()) {

                BookToOrder bookToUpdate = existingBookToOrder.get();

                if (bookToOrderRequest.getVisitorCount() % 10 == 0) {
                    int countToOrder = bookToOrderRequest.getVisitorCount() / 10;
                    bookToUpdate.setQuantity(bookToUpdate.getQuantity() + countToOrder);
                }
                bookToOrderRepository.delete(book);
                bookToOrderRepository.save(bookToUpdate);
                processedBooks.add(bookToUpdate);
            } else {
                BookToOrder newBookToOrder = mapper.toEntity(bookToOrderRequest);
                bookToOrderRepository.save(newBookToOrder);
                processedBooks.add(newBookToOrder);
            }
        }

        return processedBooks.stream()
                .map(mapper::toDetails)
                .collect(Collectors.toList());
    }

    public byte[] generatePdf() {
        List<BookToOrder> books = bookToOrderRepository.findAll();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        for (BookToOrder book : books) {
            document.add(new Paragraph("Book ID: " + book.getBookId()));
            document.add(new Paragraph("Quantity: " + book.getQuantity()));
            document.add(new Paragraph("\n"));
        }

        return baos.toByteArray();
    }
}
