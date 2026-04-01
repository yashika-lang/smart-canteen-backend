package smart_canteen_backend.demo.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class AddOrderRequestDto {


    private Long  menuItemId;
    private Integer quantity;
}
