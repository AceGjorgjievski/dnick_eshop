package mk.ukim.finki.dnick.service;


import mk.ukim.finki.dnick.model.Order;
import mk.ukim.finki.dnick.model.ShoppingCartItem;

import java.util.List;

public interface OrderService {

    void placeOrder(List<ShoppingCartItem> shoppingCartItems, String address);
    List<Order> findAll();
}
