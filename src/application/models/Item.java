package application.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static application.DBInterface.getConnection;

public class Item {
    protected final int id;
    protected int user_id, bid;
    protected final String name, description;
    protected boolean is_sold = false, is_paid = false;
    private static final String BID_UPDATE_QUERY = "UPDATE items SET bid = ?, user_id = ? where id= ? and bid < ?;";
    private static final String SELECT_ITEMS_QUERY = "select * from items;";
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

    public int getBid() {
        return bid;
    }
    Item(int id, String name, String description){
        this.id = id;
        this.user_id = -1;
        this.name = name;
        this.description = description;
    }


    public static Item builder(int id, String name, String description){
        return new Item(id, name, description);
    }

    private static Item builder(int id, String name, String description, int user_id, boolean is_sold, boolean is_paid){
        Item item = new Item(id, name, description);
        item.is_sold = is_sold;
        item.is_paid = is_paid;
        item.user_id = user_id;
        return item;
    }

    private static Item builder(ResultSet resultSet) throws SQLException{
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String description = resultSet.getString("description");

        Item item = new Item(id, name, description);
        item.is_sold =  resultSet.getInt("is_sold") == 1;
        item.is_paid = resultSet.getInt("is_paid") == 1;
        item.user_id = resultSet.getInt("user_id");
        return item;
    }

    public Boolean setBid(int amount, int user_id) {
        if (amount > bid) {
            try {
                PreparedStatement stmt = getConnection().prepareStatement(BID_UPDATE_QUERY);
                stmt.setInt(1, amount);
                stmt.setInt(2,user_id);
                stmt.setInt(3, id);
                stmt.setInt(4, amount);
                System.out.println(stmt.toString());
                int r = stmt.executeUpdate();
                if(r == 0){
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

    public static ArrayList<Item> getObjectList(){
        ArrayList<Item> itemArrayList = new ArrayList<>();
        Item temp;
        PreparedStatement stmt = null;
        try {
            stmt = getConnection().prepareStatement(SELECT_ITEMS_QUERY);
            ResultSet resultSet = stmt.executeQuery();
            while(resultSet.next()){
                temp = builder(resultSet);
                itemArrayList.add(temp);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return itemArrayList;
    }




}
