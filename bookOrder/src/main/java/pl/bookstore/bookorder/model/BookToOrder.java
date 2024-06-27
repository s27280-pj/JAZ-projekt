package pl.bookstore.bookorder.model;

import io.swagger.models.auth.In;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "bookToOrder_table")
public class BookToOrder {
    @Id
    @GeneratedValue
    private UUID id;

    private UUID bookId;

    private Integer quantity;

}
