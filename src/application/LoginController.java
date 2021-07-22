package application;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Window;

import application.Authentication;

import static application.messages.MessageDisplay.infoBox;
import static application.messages.MessageDisplay.showAlert;

public class LoginController extends NextPageController {


    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button submitButton;

    @FXML
    public void login(ActionEvent event) throws SQLException {

        Window window = submitButton.getScene().getWindow();

        System.out.println(usernameField.getText());
        System.out.println(passwordField.getText());

        if (usernameField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, window, "Form Error!",
                    "Please enter your email id");
            return;
        }
        if (passwordField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, window, "Form Error!",
                    "Please enter a password");
            return;
        }

        String emailId = usernameField.getText();
        String password = passwordField.getText();

        boolean flag = Authentication.authenticate(emailId, password);

        if (!flag) {
            infoBox("Please enter correct Email and Password", null, "Failed");
        } else {

            infoBox("Login Successful!", null, "Success");
            try {
                nextPage(event,"Home.fxml" );
            } catch (IOException e){
                System.out.println(e);
                showAlert(Alert.AlertType.ERROR, window, "Something went wrong",
                        "Couldn't load next Page!");
            }
        }
    }


}