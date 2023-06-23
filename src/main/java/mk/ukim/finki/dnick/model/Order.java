package mk.ukim.finki.dnick.model;


import lombok.Data;
import lombok.Getter;
import mk.ukim.finki.dnick.model.enums.OrderStatus;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="user_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    private Double totalPrice;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<ShoppingCartItem> shoppingCartItems;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime orderCreated;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public Order() {
        this.orderCreated = LocalDateTime.now();
        this.orderStatus = OrderStatus.CREATED;
        this.shoppingCartItems = new ArrayList<>();
    }

    public Order(List<ShoppingCartItem> shoppingCartItems, String address) {
        this.address = address;
        this.shoppingCartItems = shoppingCartItems;
        this.orderCreated = LocalDateTime.now();
        this.orderStatus = OrderStatus.CREATED;
        this.totalPrice = shoppingCartItems
                .stream()
                .mapToDouble(i -> i.getProduct().getPrice() * i.getQuantity())
                .sum();
    }

    @Override
    public String toString() {
        return "Order{" +
                "address='" + address + '\'' +
                ", totalPrice=" + totalPrice +
                ", orderCreated=" + orderCreated +
                ", orderStatus=" + orderStatus +
                '}';
    }
}
