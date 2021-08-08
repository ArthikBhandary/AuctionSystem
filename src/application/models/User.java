package application.models;

/**
 * User model to store account information.
 */
public class User {

    final private int id;
    final public String username;
    final private String password;
    final protected Boolean is_admin;


    /**
     * @param id id/primary key of the user
     * @param username username of the user
     * @param password password of the user object
     * @param is_admin true if the user has admin privileges
     */
    public User(int id, String username, String password, Boolean is_admin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.is_admin = is_admin;
    }

    /**
     * @return username of the user object
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return id/primary key of the user object
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     * @param username
     * @param password
     * @param is_admin
     * @return user object
     */
    public static User userBuilder(int id, String username, String password, Boolean is_admin){
        return new User(id, username, password, is_admin);
    }

    public String getPassword() {
        return password;
    }
}
