package smart_canteen_backend.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;


@Data
public class OrderDto {

    private Long id;
    private Long menuItemId;

    private Integer quantity;
    private Integer totalPrice;
    private String status;
    private Integer tokenNumber;
    private LocalDateTime createdAt;
}
