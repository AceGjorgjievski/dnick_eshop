package mk.ukim.finki.dnick.model.exceptions;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(Long id) {
        super(String.format("Product with id: %ld was not found!",id));
    }
}
