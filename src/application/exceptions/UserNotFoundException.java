package application.exceptions;

/**
 * Exception to be thrown when the user isn't found
 */
public class UserNotFoundException extends Exception {
    @Override
    public String toString() {
        return "User not found. Log in first";
    }
}
