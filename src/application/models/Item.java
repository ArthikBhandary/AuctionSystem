
package application.models;

import application.State.State;
import application.exceptions.UserNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static application.DBInterface.getConnection;

public class Item {
    protected final int id;
    protected int user_id, bid;
    protected final String name, description, imagePath;
    protected boolean is_sold = false, is_paid = false;


    private static final String BID_UPDATE_QUERY = "UPDATE items SET bid = ?, user_id = ? where id= ? and bid < ? and is_sold = 0;";
    private static final String FINISH_QUERY = "UPDATE items SET is_sold = 1 where id= ?;";
    private static final String BUY_QUERY = "UPDATE items SET is_paid = 1 where id= ? and user_id = ?;";
    private static final String SELECT_ITEMS_QUERY = "select * from items;";
    private static final String DB_CREATE_QUERY = "INSERT INTO items (name, description, image) VALUES ( ?, ?, ?);";

    public int getId() {
        return id;
    }

    public int getUserId() {
        return user_id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public int getBid() {
        return bid;
    }

    public Boolean isSold() {
        return is_sold;
    }

    public boolean isPaid(){
        return is_paid;
    }

    Item(int id, String name, String description, String imagePath) {
        this.id = id;
        this.user_id = -1;
        this.name = name;
        this.description = description;
        this.imagePath = imagePath;
    }


    public static Item builder(int id, String name, String description, String imagePath) {
        return new Item(id, name, description, imagePath);
    }

    private static Item builder(int id, String name, String description, String imagePath, int user_id, boolean is_sold, boolean is_paid) {
        Item item = new Item(id, name, description, imagePath);
        item.is_sold = is_sold;
        item.is_paid = is_paid;
        item.user_id = user_id;
        return item;
    }

    private static Item builder(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String description = resultSet.getString("description");
        String imagePath = resultSet.getString("image");

        Item item = new Item(id, name, description, imagePath);
        item.is_sold = resultSet.getInt("is_sold") == 1;
        item.is_paid = resultSet.getInt("is_paid") == 1;
        item.user_id = resultSet.getInt("user_id");
        return item;
    }

    public Boolean setBid(int amount) throws UserNotFoundException {
        if (amount > bid) {
            try {
                State.getUser().getId();
                PreparedStatement stmt = getConnection().prepareStatement(BID_UPDATE_QUERY);
                stmt.setInt(1, amount);
                stmt.setInt(2, user_id);
                stmt.setInt(3, id);
                stmt.setInt(4, amount);
                System.out.println(stmt.toString());
                int r = stmt.executeUpdate();
                if (r == 0) {
                    // No row updated
                    return false;
                }
                bid = amount;
                this.user_id = user_id;
                return true;
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        return false;
    }

    public static ArrayList<Item> getObjectList() {
        ArrayList<Item> itemArrayList = new ArrayList<>();
        Item temp;
        PreparedStatement stmt = null;
        try {
            stmt = getConnection().prepareStatement(SELECT_ITEMS_QUERY);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                temp = builder(resultSet);
                itemArrayList.add(temp);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return itemArrayList;
    }



    void finishBid() {
        is_sold = true;
        try {
            PreparedStatement stmt = getConnection().prepareStatement(FINISH_QUERY);
            stmt.setInt(1, id);
            int r = stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public void buyItem(String number) throws UserNotFoundException {
        try {
            if(State.getUser().getId() != user_id){
                throw new UserNotFoundException();
            }
            PreparedStatement stmt = getConnection().prepareStatement(BUY_QUERY);
            stmt.setInt(1, id);
            stmt.setInt(2, user_id);


            int r = stmt.executeUpdate();
            Transaction.createTransaction(this);
        } catch (SQLException e) {
            System.out.println(e);
        }
        is_paid = true;
    }


    public static void createItem(String name, String description, String pathname){

        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(DB_CREATE_QUERY);
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setString(3, pathname);
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
