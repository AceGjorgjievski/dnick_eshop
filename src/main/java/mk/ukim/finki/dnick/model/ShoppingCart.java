package mk.ukim.finki.dnick.model;


import lombok.Data;
import mk.ukim.finki.dnick.model.enums.ShoppingCartStatus;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDateTime dateCreated;

    @ManyToOne
    private User user;

    @ManyToMany
    private List<ShoppingCartItem> shoppingCartItems;

    @Enumerated(EnumType.STRING)
    private ShoppingCartStatus cartStatus;

    public ShoppingCart() {
        this.shoppingCartItems = new ArrayList<>();
    }

    public ShoppingCart(User user) {
        this.user = user;
        this.shoppingCartItems = new ArrayList<>();
        this.dateCreated = LocalDateTime.now();
        this.cartStatus = ShoppingCartStatus.CREATED;
    }
}
