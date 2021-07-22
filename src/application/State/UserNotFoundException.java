package application.State;

public class UserNotFoundException extends Exception {
    @Override
    public String toString() {
        return "User not found. Log in first";
    }
}
