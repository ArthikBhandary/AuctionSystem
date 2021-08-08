package application.controllers;

import application.Authentication;
import application.State.State;
import application.exceptions.UserNotFoundException;
import application.models.Admin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import java.io.IOException;

/**
 * AdminLogin.fxml uses this controller
 * It is used to display and produces the functionalities of the admin login page
 * It accepts a password(user password) to verify if the user is a admin, and allows access on correct passowrd
 */
public class AdminLoginController extends NextPageController {

    @FXML
    private Label msg;
    @FXML
    private PasswordField passwordField;


    /**
     * Used to check if the entered password is correct for the current user and check if the user
     * has admin privileges. Redirect to AdminHome on correct password
     *
     * @param event event that triggers this function
     * @throws IOException
     */
    @FXML
    public void login(ActionEvent event) throws IOException {
        String password = passwordField.getText();
        try {
            if (Authentication.validate(password)) {
                // If user has admin privileges and password checks out, then go to next page
                State.setAdmin(new Admin(State.getUser()));
                nextPage(event, "../view/AdminHome.fxml");

            } else {
                // display error message if not validated
                msg.setText("Invalid password!");
            }
        } catch (UserNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }


}
