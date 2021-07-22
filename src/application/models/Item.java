package application.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static application.DBInterface.getConnection;

public class Item {
    protected int id, user_id, bid;
    protected String name, description;

    private static final String BID_UPDATE_QUERY = "UPDATE items SET bid = ? where id= ? and bid < ?;";
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
    public Boolean setBid(int amount) {
        if (amount > bid) {
            try {
                PreparedStatement stmt = getConnection().prepareStatement(BID_UPDATE_QUERY);
                stmt.setInt(1, amount);
                stmt.setInt(2, id);
                stmt.setInt(3, amount);
                System.out.println(stmt.toString());
                int r = stmt.executeUpdate();
                if(r == 0){
                    // No row updated
                    return false;
                }
                return true;
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        return false;
    }


}
