package smart_canteen_backend.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuDto {

    private Long id;
    private int price;
    private String name;
    private String imageUrl;
    private boolean available;

}
