package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static application.CONSTANTS.DB_CONSTANTS.*;
import static application.ExceptionDisplay.printSQLException;

public class ConnectDB {

    static void sqlconnect(String query)
    {
        try
        {
            Class.forName(CLASSNAME);
            Connection con= DriverManager.getConnection(DATABASE_URL ,DATABASE_USERNAME, DATABASE_PASSWORD);
            Statement stmt=con.createStatement();
            System.out.println("Connected Database");
            stmt.execute(query);
        }catch(SQLException e)
        {
            printSQLException(e);
        } catch(Exception e){
            System.out.println(e);
        }
    }

    static Connection getConnection() throws SQLException
    {
        Connection connection= DriverManager.getConnection(DATABASE_URL ,DATABASE_USERNAME, DATABASE_PASSWORD);
        return connection;
    }

}
