package smart_canteen_backend.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long menuItemId;
    private String menuItemName;
    private int quantity;
    private double price;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}