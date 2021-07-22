package application;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import javafx.stage.Stage;

public class AdminLoginController extends NextPageController{
	
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
				//FROM HERE WE NAVIGATE TO INITIAL BID PAGE
					Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
					(new CreateItem(stage)).openPage();
				}

				else
				{
					System.out.println("Incorrect");
					msg.setText("Invalid!");
				}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}


}
