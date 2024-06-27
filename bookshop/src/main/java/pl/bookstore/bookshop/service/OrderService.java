package pl.bookstore.bookshop.service;

import org.springframework.stereotype.Service;
import pl.bookstore.bookshop.model.Order;
import pl.bookstore.bookshop.mapper.OrderMapper;
import pl.bookstore.bookshop.repository.OrderRepository;
import pl.bookstore.model.OrderDetails;
import pl.bookstore.model.OrderRequest;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    public OrderDetails addNewOrder(OrderRequest orderRequest) {
        Order order = orderMapper.toEntity(orderRequest);
        orderRepository.save(order);
        return orderMapper.toDetails(order);
    }

    public List<OrderDetails> getAllOrders(){
        return orderRepository
                .findAll()
                .stream()
                .map(orderMapper::toDetails)
                .collect(Collectors.toList());
    }
}
