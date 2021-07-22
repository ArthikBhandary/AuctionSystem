package application.models;

public class User {
    final int id;
    final String username;
    final private String password;
    final private Boolean is_admin;
    public User(int id, String username, String password, Boolean is_admin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.is_admin = is_admin;
    }
}
