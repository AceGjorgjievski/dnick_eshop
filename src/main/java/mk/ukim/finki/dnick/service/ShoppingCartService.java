package mk.ukim.finki.dnick.service;


import mk.ukim.finki.dnick.model.Product;
import mk.ukim.finki.dnick.model.ShoppingCart;
import mk.ukim.finki.dnick.model.ShoppingCartItem;

import java.util.List;

public interface ShoppingCartService {
    List<Product> listAllProductsInShoppingCart(Long cartId);
    List<ShoppingCartItem> listAllShoppingCartItems(Long cartId);

    ShoppingCart getActiveCart(String username);
    boolean addProductToShoppingCart(String username, Long id);

    void removeProductFromShoppingCart(ShoppingCart shoppingCart, Long id);
    void emptyShoppingCart(String username);
}
