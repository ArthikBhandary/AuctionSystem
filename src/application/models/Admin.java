package application.models;

public class Admin extends User{

    public Admin(int id, String username, String password) {
        super(id, username, password, true);
    }
    public Admin(User user){
        super(user.id, user.getUsername(), user.password, true);
    }

    public static Admin AdminBuilder(User user){
        return new Admin(user);
    }

}
