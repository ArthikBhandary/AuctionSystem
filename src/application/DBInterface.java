package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static application.CONSTANTS.DB_CONSTANTS.*;
import static application.exceptions.ExceptionDisplay.printSQLException;


/**
 * Class used to get and create and get connection to the database
 */
public class DBInterface {

    private static DBInterface instance;
    private Connection connection;


    DBInterface(){
        try
        {
            Class.forName(CLASSNAME);
            // create a connection to the database
            connection= DriverManager.getConnection(DATABASE_URL ,DATABASE_USERNAME, DATABASE_PASSWORD);
        }catch(SQLException e)
        {
            printSQLException(e);
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public static DBInterface getInstance() {
        // Get instance if it is already created, or create one if it doesn't exits
        if (instance == null) {
            instance = new DBInterface();
        }
        return instance;
    }


    /**
     * @return connection to the database, to execute statements
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException
    {
        return getInstance().connection;
    }

}
