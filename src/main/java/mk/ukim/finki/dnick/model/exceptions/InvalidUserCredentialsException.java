package mk.ukim.finki.dnick.model.exceptions;

public class InvalidUserCredentialsException extends RuntimeException{

    public InvalidUserCredentialsException() {
        super(String.format("Invalid username or password"));
    }
}
