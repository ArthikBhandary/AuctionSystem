package application.models;

import application.State.State;
import application.exceptions.UserNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static application.DBInterface.getConnection;

public class Transaction {
    private final int id;
    private final Item item;
    private final int user_id;
    private static final String DB_CREATE_QUERY = "INSERT INTO transaction (item_id, user_id) VALUES ( ?, ?);";

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", user_id=" + user_id +
                '}';
    }

    public int getId(){
        return id;
    }

    public Item getItem(){
        return item;
    }

    public int getUserId(){
        return user_id;
    }

    Transaction(int id, Item item, int user_id){
        this.id = id;
        this.item = item;
        this.user_id = user_id;
    }

    public Transaction builder(int id, Item item, int user_id){
        return new Transaction(id, item, user_id);
    }

    static public void createTransaction(Item item) throws UserNotFoundException{

        Connection conn;
        try {
            conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(DB_CREATE_QUERY);
            stmt.setInt(1, item.getId());
            stmt.setInt(2, State.getUser().getId());
            stmt.executeUpdate();
        }  catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
