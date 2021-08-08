package application.controllers;

import application.Authentication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.SQLException;

import static application.messages.MessageDisplay.infoBox;
import static application.messages.MessageDisplay.showAlert;

/**
 * Controller of Login.fxml
 * Used to display login page and implement logins
 */
public class LoginController extends NextPageController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button submitButton;

    /**
     * Used to login/authorise the user, by using the username from usernameField and password from passwordField
     * @param event event that results in the triggering of the function call
     */
    @FXML
    public void login(ActionEvent event) {

        Window window = submitButton.getScene().getWindow();
        // Check if the usernameField is empty, if yes return and show alert
        if (usernameField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, window, "Form Error!",
                    "Please enter your email id");
            return;
        }
        // Check if the passwordField is empty, if yes return and show alert
        if (passwordField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, window, "Form Error!",
                    "Please enter a password");
            return;
        }
        // Extract emailId and password
        String emailId = usernameField.getText();
        String password = passwordField.getText();
        // Authenticate the username and password
        boolean flag = Authentication.authenticate(emailId, password);
        // If authentication failed, i.e, returned false show alert
        // Otherwise redirect to next page
        if (!flag) {
            infoBox("Please enter correct Email and Password", null, "Failed");
        } else {

            infoBox("Login Successful!", null, "Success");
            try {
                nextPage(event, "../view/Home.fxml");
            } catch (IOException e) {
                System.out.println(e);
                showAlert(Alert.AlertType.ERROR, window, "Something went wrong",
                        "Couldn't load next Page!");
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }


}