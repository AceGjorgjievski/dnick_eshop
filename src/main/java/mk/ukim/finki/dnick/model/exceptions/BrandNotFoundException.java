package mk.ukim.finki.dnick.model.exceptions;

public class BrandNotFoundException extends RuntimeException{
    public BrandNotFoundException(Long id) {
        super(String.format("Brand with id: %ld was not found!",id));
    }
}
