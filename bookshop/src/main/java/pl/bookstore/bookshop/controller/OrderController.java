package pl.bookstore.bookshop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.bookstore.api.OrdersApi;
import pl.bookstore.bookshop.service.OrderService;
import pl.bookstore.model.OrderDetails;
import pl.bookstore.model.OrderRequest;

import java.util.List;

@RestController
public class OrderController implements OrdersApi {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public ResponseEntity<OrderDetails> addNewOrder(OrderRequest orderRequest) {
        return ResponseEntity.ok(orderService.addNewOrder(orderRequest));
    }

    @Override
    public ResponseEntity<List<OrderDetails>> getAllOrder() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
}
