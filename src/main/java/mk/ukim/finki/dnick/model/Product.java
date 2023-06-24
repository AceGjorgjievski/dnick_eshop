package mk.ukim.finki.dnick.model;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Lob
    @Column(length = 16777215)
    private String image;

    private Double price;

    private Integer quantity;

    private Integer quantityForShoppingCartItem;

    @ManyToOne
    private Brand brand;

    public Product() {
        this.quantity = 1;
        this.quantityForShoppingCartItem = this.quantity;
    }

    public Product(String name, String image,Integer quantity, Double price, Brand brand) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.brand = brand;
        this.quantity = quantity;
        this.quantityForShoppingCartItem = this.quantity;
    }
}
