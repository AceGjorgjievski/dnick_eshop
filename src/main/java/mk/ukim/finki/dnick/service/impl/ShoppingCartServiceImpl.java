package mk.ukim.finki.dnick.service.impl;

import mk.ukim.finki.dnick.model.Product;
import mk.ukim.finki.dnick.model.ShoppingCart;
import mk.ukim.finki.dnick.model.ShoppingCartItem;
import mk.ukim.finki.dnick.model.User;
import mk.ukim.finki.dnick.model.enums.ShoppingCartStatus;
import mk.ukim.finki.dnick.model.exceptions.ProductNotFoundException;
import mk.ukim.finki.dnick.model.exceptions.ShoppingCartNotFoundException;
import mk.ukim.finki.dnick.model.exceptions.UserWithUsernameNotFoundException;
import mk.ukim.finki.dnick.repository.jpa.ProductRepository;
import mk.ukim.finki.dnick.repository.jpa.ShoppingCartItemRepository;
import mk.ukim.finki.dnick.repository.jpa.ShoppingCartRepository;
import mk.ukim.finki.dnick.repository.jpa.UserRepository;
import mk.ukim.finki.dnick.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Stream;


@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ShoppingCartItemRepository shoppingCartItemRepository;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository,
                                   UserRepository userRepository,
                                   ProductRepository productRepository,
                                   ShoppingCartItemRepository shoppingCartItemRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.shoppingCartItemRepository = shoppingCartItemRepository;
    }

    @Override
    public List<Product> listAllProductsInShoppingCart(Long cartId) {
        if (!this.shoppingCartRepository.findById(cartId).isPresent())
            throw new ShoppingCartNotFoundException(cartId);

//        return this.shoppingCartRepository
//                .findById(cartId)
//                .get()
//                .getProducts();
        return null;
    }

    @Override
    @Transactional
    public List<ShoppingCartItem> listAllShoppingCartItems(Long cartId) {
        if (!this.shoppingCartRepository.findById(cartId).isPresent()) {
            throw new ShoppingCartNotFoundException(cartId);
        }

        return this.shoppingCartRepository
                .findById(cartId)
                .get()
                .getShoppingCartItems();
    }

    @Override
    public ShoppingCart getActiveCart(String username) {
        User u = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UserWithUsernameNotFoundException(username));


        return this.shoppingCartRepository
                .findByUserAndCartStatus(u, ShoppingCartStatus.CREATED)
                .orElseGet(() -> {
                    ShoppingCart shoppingCart = new ShoppingCart(u);
                    return this.shoppingCartRepository.save(shoppingCart);
                });
    }

    @Override
    public boolean addProductToShoppingCart(String username, Long productId) {
        ShoppingCart shoppingCart = this.getActiveCart(username);
        Product product = this.productRepository
                .findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        ShoppingCartItem existingItem = shoppingCart.getShoppingCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        boolean addedToShoppingCart = false;

        if (product.getQuantityForShoppingCartItem() > 0) { //only if its > 0, others can add to their shopping cart
            //else cannot proceed
            if (existingItem != null) {
                existingItem.setQuantity(existingItem.getQuantity() + 1);
            } else {
                ShoppingCartItem newCartItem = new ShoppingCartItem(product, 1, product.getImage());
                newCartItem.setShoppingCart(shoppingCart);
                ShoppingCartItem savedCartItem = shoppingCartItemRepository.save(newCartItem); // Save the new cart item
                shoppingCart.getShoppingCartItems().add(savedCartItem);
            }
            product.setQuantityForShoppingCartItem(product.getQuantityForShoppingCartItem() - 1);
            this.productRepository.save(product);

            addedToShoppingCart = true;
        }
        shoppingCart = this.shoppingCartRepository.save(shoppingCart); // Save the shopping cart first
        return addedToShoppingCart;
    }

    @Override
    public void removeProductFromShoppingCart(ShoppingCart shoppingCart, Long id) {
        List<ShoppingCartItem> items = shoppingCart.getShoppingCartItems();

        ShoppingCartItem selectedShoppingCartItem = null;

        if(items.stream().filter(i -> i.getId().equals(id)).findFirst().isPresent()) {
            selectedShoppingCartItem = items
                    .stream()
                    .filter(i -> i.getId().equals(id))
                    .findFirst().get();

            Product p = selectedShoppingCartItem.getProduct();

            p.setQuantityForShoppingCartItem(
                    p.getQuantityForShoppingCartItem() + selectedShoppingCartItem.getQuantity()
            );
            this.productRepository.save(p);
            items.removeIf(i -> i.getId().equals(id));
            this.shoppingCartItemRepository.deleteById(selectedShoppingCartItem.getId());
        }


        shoppingCart.setShoppingCartItems(items);
        this.shoppingCartRepository.save(shoppingCart);
    }

    @Override
    public void emptyShoppingCart(String username) {
        ShoppingCart cart = this.getActiveCart(username);

        cart.getShoppingCartItems().clear();

        this.shoppingCartRepository.save(cart);
    }


}
