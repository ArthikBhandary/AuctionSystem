package application;
	
import application.models.Item;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;


public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		// Load the login page
		Parent root = FXMLLoader.load(getClass().getResource("view/Login.fxml"));
		Scene scene  = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Auction System");
		primaryStage.show();
		
	}

}
