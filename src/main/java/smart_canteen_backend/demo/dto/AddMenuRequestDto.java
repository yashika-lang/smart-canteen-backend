package smart_canteen_backend.demo.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AddMenuRequestDto {

    @NotBlank(message = "Price must be greater than 0")
    private int price;
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @NotBlank(message = "Enter the valid url")
    private String imageUrl;
    private boolean available;
}
