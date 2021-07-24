package application.controllers;

import java.io.IOException;
import java.sql.SQLException;

import application.Authentication;
import application.State.State;
import application.exceptions.UserNotFoundException;
import application.models.Admin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class AdminLoginController extends NextPageController {
	
	@FXML
	private Label msg;
	@FXML
	private PasswordField passwordField;


	@FXML
	public void login(ActionEvent event) throws IOException
	{
		Parent pane;
		String password = passwordField.getText();
		System.out.println(password);
		try {
			if(Authentication.validate(password))
				{
					System.out.println("Correct");
					msg.setText("valid");
					State.setAdmin(new Admin(State.getUser()));
				//FROM HERE WE NAVIGATE TO INITIAL BID PAGE
					nextPage(event, "../view/AdminHome.fxml");

				}

				else
				{
					System.out.println("Incorrect");
					msg.setText("Invalid!");
				}
		} catch (SQLException | UserNotFoundException throwables) {
			throwables.printStackTrace();
		}
	}


}
