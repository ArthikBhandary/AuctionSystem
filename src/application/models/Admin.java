package application.models;

public class Admin extends User{

    /**
     * @param id id of the admin account
     * @param username username of the admin account
     * @param password password of the admin account
     */
    public Admin(int id, String username, String password) {
        super(id, username, password, true);
    }

    /**
     * @param user user object of user that has admin privileges
     */
    public Admin(User user){
        super(user.getId(), user.getUsername(), user.getPassword(), true);
    }

    /**
     * @param user User object that has admin privileges to be converted into an Admin object
     * @return Admin object
     */
    public static Admin AdminBuilder(User user){
        return new Admin(user);
    }


    /**
     * Create a new item for auctioning and add it to the db
     * @param name name of the item to be created
     * @param description description of the item to be created
     * @param pathname pathname of the image of the item
     */
    public void createItem(String name, String description, String pathname){
        Item.createItem(name, description, pathname);
    }

    /**
     * Finish bidding on a item
     * @param item item whose bidding has to be stopped
     */
    public void finishBid(Item item){
        item.finishBid();
    }
}
