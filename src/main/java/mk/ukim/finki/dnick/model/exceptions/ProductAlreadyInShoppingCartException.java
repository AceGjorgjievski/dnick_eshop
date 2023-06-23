package mk.ukim.finki.dnick.model.exceptions;


public class ProductAlreadyInShoppingCartException extends RuntimeException{
    public ProductAlreadyInShoppingCartException(Long productId, String username) {
        super(String.format("Product with this id: %ld already exists in cart for user with username: %s",
                productId,
                username));
    }
}
