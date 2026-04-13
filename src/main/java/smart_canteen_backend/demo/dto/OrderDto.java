package smart_canteen_backend.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Data
public class OrderDto {

    private Long id;

    private Long userId;

    private Integer totalPrice;
    private String status;
    private Integer tokenNumber;
    private LocalDateTime createdAt;

    // MULTI ITEMS SUPPORT
    private List<OrderItemDto> items;
}
