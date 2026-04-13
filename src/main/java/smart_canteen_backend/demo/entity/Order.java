package smart_canteen_backend.demo.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Setter
@Getter
public class Order {
 @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private Integer totalPrice;

   private Integer tokenNumber;
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;

    @ManyToOne// ek user ke kaafi orders hosakte hai
    @JoinColumn(name = "user_id") // db me column ban jaaye user_id ka
    private User user;
}
