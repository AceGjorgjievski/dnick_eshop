package mk.ukim.finki.dnick.model.exceptions;

public class UserWithUsernameNotFoundException extends RuntimeException{

    public UserWithUsernameNotFoundException(String username) {
        super(String.format("User with this username: %s was not found!",username));
    }
}
