package pl.bookstore.bookshop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "order_table")
public class Order {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Book book;

    private Integer quantity;

    private Float totalPrice;
}
