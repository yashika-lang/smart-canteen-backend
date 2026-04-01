package smart_canteen_backend.demo.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Setter
@Getter
public class Order {
 @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private Long menuItemId;

    private Integer quantity;
    private Integer totalPrice;
    private String status;

   private Integer tokenNumber;
    private LocalDateTime createdAt;


}
