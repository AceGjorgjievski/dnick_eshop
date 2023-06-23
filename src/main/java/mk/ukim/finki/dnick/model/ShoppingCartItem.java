package mk.ukim.finki.dnick.model;


import lombok.Data;

import javax.persistence.*;


@Data
@Entity
public class ShoppingCartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    private ShoppingCart shoppingCart;


    private int quantity;

    @Lob
    @Column(length = 16777215)
    private String image;

    public ShoppingCartItem() {
    }

    public ShoppingCartItem(Product product, int quantity, String image) {
        this.product = product;
        this.quantity = quantity;
        this.image = image;
    }

    @Override
    public String toString() {
        return "ShoppingCartItem{" +
                "product=" + product +
                ", quantity=" + quantity +
                ", image='" + image + '\'' +
                '}';
    }
}
