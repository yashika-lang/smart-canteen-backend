package smart_canteen_backend.demo.dto;

import lombok.Data;

@Data
public class OrderItemDto {
    private String menuItemName;
    private Integer quantity;
}