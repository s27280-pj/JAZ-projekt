package pl.bookstore.bookshop.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import pl.bookstore.bookshop.model.Order;
import pl.bookstore.model.OrderDetails;
import pl.bookstore.model.OrderRequest;

@org.mapstruct.Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR,
        componentModel = "spring",
        builder = @Builder(disableBuilder = true),
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "totalPrice", ignore = true)
    @Mapping(target = "book", ignore = true)
    Order toEntity (OrderRequest orderRequest);

    @Mapping(target = "orderId", ignore = true)
    @Mapping(target = "bookId", ignore = true)
    OrderDetails toDetails (Order order);
}
