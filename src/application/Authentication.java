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
    // Replace below database url, username and password with your actual database credentials

//    private static final String VAILIDATE_QUERY = "SELECT * FROM accounts WHERE id = ? and password = ?";
    private static final String LOGIN_QUERY = "SELECT * FROM accounts WHERE username = ? and password = ?";

    public static boolean authenticate(String username, String password) throws SQLException {

        // Step 1: Establishing a Connection and
        // try-with-resource statement will auto close the connection.
        try {
            Connection connection = DBInterface.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(LOGIN_QUERY);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            System.out.println(preparedStatement);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int temp_id = resultSet.getInt(1);
                String temp_username = resultSet.getString("username");
                String temp_password = resultSet.getString("password");
                Boolean temp_isadmin = resultSet.getBoolean("is_admin");
                State.setUser(userBuilder(temp_id, temp_username, temp_password, temp_isadmin)); // Update state and store logged in user
                return true;
            }


        } catch (SQLException e) {
            // print SQL exception information
            printSQLException(e);
        }
        return false;
    }

    public static boolean validate(String password) throws SQLException {

        // Step 1: Establishing a Connection and
        // try-with-resource statement will auto close the connection.
        try {
            Connection connection = DBInterface.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(LOGIN_QUERY);
            preparedStatement.setString(1, State.getUser().username);
            preparedStatement.setString(2, password);

            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && resultSet.getBoolean("is_admin")) {
                return true;
            }

        } catch (SQLException e) {
            // print SQL exception information
            printSQLException(e);
        } catch(UserNotFoundException e){
            System.out.println(e);
        }
        return false;
}


}