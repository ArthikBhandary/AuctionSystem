package application.models;

public class Admin extends User{

    public Admin(int id, String username, String password) {
        super(id, username, password, true);
    }
    public Admin(User user){
        super(user.getId(), user.getUsername(), user.getPassword(), true);
    }

    public static Admin AdminBuilder(User user){
        return new Admin(user);
    }

    public void finishBid(Item item){
        item.finishBid();
    }
}
