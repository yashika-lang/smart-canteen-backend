package smart_canteen_backend.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class MenuItem {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)

    private Long id;
    private int price;
    private String name;
    private String imageUrl;
    private boolean available;
    @Column
    private String category;
    @Column(columnDefinition = "TEXT")
    private String description;
}

