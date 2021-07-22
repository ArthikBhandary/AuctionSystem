package application.exceptions;

public class UserNotFoundException extends Exception {
    @Override
    public String toString() {
        return "User not found. Log in first";
    }
}
