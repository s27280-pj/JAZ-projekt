package pl.bookstore.bookshop.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.bookstore.model.BookToOrderDetails;
import pl.bookstore.model.BookToOrderRequest;

import java.util.List;

@FeignClient(name = "BookOrder", url = "http://localhost:8081")
public interface OrderReportFeign {
    @PostMapping("/order-report")
    ResponseEntity<List<BookToOrderRequest>> sendBookToOrder(List<BookToOrderRequest> bookToOrderRequest);

    @GetMapping("/order-report/print")
    ResponseEntity<Resource> getPdfWithOrder();
}
