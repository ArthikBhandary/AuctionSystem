package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	public void runTask()
	{
		String command = "echo 1234 | sudo -S -k service mysql start";


		try {
			Process process = Runtime.getRuntime().exec(command);


			System.out.println("Executing cmd");
			Thread.sleep(5000);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
//		primaryStage.show();
//		(new CreateItem(primaryStage)).openPage();
		runTask();
		Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
		Scene scene  = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Auction System");
		primaryStage.show();
		
	}

}
