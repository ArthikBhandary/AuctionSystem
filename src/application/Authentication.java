package application;

import application.State.State;
import application.exceptions.UserNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static application.exceptions.ExceptionDisplay.printSQLException;
import static application.models.User.userBuilder;

public class Authentication {

    //    private static final String VAILIDATE_QUERY = "SELECT * FROM accounts WHERE id = ? and password = ?";
    private static final String LOGIN_QUERY = "SELECT * FROM accounts WHERE username = ? and password = ?";


    /**
     * Used to authenticate and log in a user, using username and password
     * State is updated if the user exists and password is correct
     * @param username username of the user
     * @param password password of the user
     * @return true if the username and password match to an existing user account
     */
    public static boolean authenticate(String username, String password) {

        try {
            // Get connection to the database using DBInterface
            Connection connection = DBInterface.getConnection();
            // Prepare a SQL statement to be executed with the username and password
            PreparedStatement preparedStatement = connection.prepareStatement(LOGIN_QUERY);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();
            // If there were any results for the username and password, i.e, a user exists
            if (resultSet.next()) {
                // extract id, username, password and isAdmin (admin status) from the returned row
                int temp_id = resultSet.getInt(1);
                String resultUsername = resultSet.getString("username");
                String resultPassword = resultSet.getString("password");
                Boolean isAdmin = resultSet.getBoolean("is_admin");

                // User has been successfully authenticated, so he must be logged in
                // Create a User object to store the details and update the State
                State.setUser(userBuilder(temp_id, resultUsername, resultPassword, isAdmin)); // Update state and store logged in user
                return true;
            }


        } catch (SQLException e) {
            // print SQL exception information
            printSQLException(e);
        }
        return false;
    }

    /**
     * To check if the current user is admin, and if yes, allow admin operations
     * Should only be used if user is already logged in with authenticate method
     * @param password password of the user
     * @return true, if the password is right and the user is an admin
     */
    public static boolean validate(String password){

        try {
            // Get connection from the user
            Connection connection = DBInterface.getConnection();
            // Prepare SQL statement to be used with username from the State and given password
            PreparedStatement preparedStatement = connection.prepareStatement(LOGIN_QUERY);
            preparedStatement.setString(1, State.getUser().username);
            preparedStatement.setString(2, password);
            // Execute the statement
            ResultSet resultSet = preparedStatement.executeQuery();
            // If any result was found, and the user found is an admin, the return true
            if (resultSet.next() && resultSet.getBoolean("is_admin")) {
                return true;
            }

        } catch (SQLException e) {
            // print SQL exception information
            printSQLException(e);
        } catch (UserNotFoundException e) {
            System.out.println(e);
        }
        return false;
    }


}