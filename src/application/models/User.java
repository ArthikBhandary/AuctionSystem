package application.models;

public class User {
    final private int id;
    final public String username;
    final private String password;
    final protected Boolean is_admin;


    public User(int id, String username, String password, Boolean is_admin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.is_admin = is_admin;
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    public static User userBuilder(int id, String username, String password, Boolean is_admin){
        return new User(id, username, password, is_admin);
    }

    public String getPassword() {
        return password;
    }
}
