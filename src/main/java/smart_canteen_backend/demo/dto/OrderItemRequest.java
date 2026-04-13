package smart_canteen_backend.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemRequest {

    private Long menuItemId;
    private int quantity;
}