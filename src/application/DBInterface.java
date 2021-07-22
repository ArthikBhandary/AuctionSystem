package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static application.CONSTANTS.DB_CONSTANTS.*;
import static application.exceptions.ExceptionDisplay.printSQLException;

public class DBInterface {

    private static DBInterface instance;
    private Connection connection;


    DBInterface(){
        try
        {
            Class.forName(CLASSNAME);
            connection= DriverManager.getConnection(DATABASE_URL ,DATABASE_USERNAME, DATABASE_PASSWORD);
            System.out.println("Connected Database");
        }catch(SQLException e)
        {
            printSQLException(e);
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public static DBInterface getInstance() {
        //Singleton Class
        if (instance == null) {
            instance = new DBInterface();
        }
        return instance;
    }


    public static Connection getConnection() throws SQLException
    {
        return getInstance().connection;
    }

}
