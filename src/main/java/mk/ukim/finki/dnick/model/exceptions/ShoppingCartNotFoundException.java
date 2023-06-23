package mk.ukim.finki.dnick.model.exceptions;

public class ShoppingCartNotFoundException extends RuntimeException{
    public ShoppingCartNotFoundException(Long id) {
        super(String.format("ShoppingCart with id: %ld was not found!",id));
    }
}
