package pl.bookstore.bookorder.mapper;

import org.mapstruct.Builder;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import pl.bookstore.bookorder.model.BookToOrder;
import pl.bookstore.model.BookToOrderDetails;
import pl.bookstore.model.BookToOrderRequest;

@org.mapstruct.Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR,
        componentModel = "spring",
        builder = @Builder(disableBuilder = true),
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BookToOrderMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "quantity", ignore = true)
    BookToOrder toEntity(BookToOrderRequest bookToOrderRequest);

    BookToOrderDetails toDetails(BookToOrder bookToOrder);
}
