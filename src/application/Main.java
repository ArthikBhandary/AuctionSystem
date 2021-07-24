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
//		String iP = "/images/0cd98b09-6d09-497d-8070-188fd7dbfdc420201102_194010440_001 (2).jpg";
//		System.out.println(iP);
//		System.out.println(Main.class.toString());
//		System.out.println(getClass().getResource("a97ba17cd-02ce-4650-872a-dd1d7dd9aa6c744900a.jpg"));
//		System.out.println(Main.class.getResource("../application/resources/images/white.jpg"));
		ArrayList<Item> itemArrayList = Item.getObjectList();
		for(Item item: itemArrayList){
			System.out.println("String :  /application/resources/" + item.getImagePath());
			System.out.println(Main.class.getResource("/application/resources/" + item.getImagePath()).toExternalForm());
		}
//		System.out.println(Main.class.getResource("/application/resources/images/e8ced9b8-9222-4d16-b62b-e216462c08d8744900.png"));
//		URL _url = getClass().getResource("/application/resources/images/white.jpg");
//		System.out.println("here");
//		System.out.println(_url);
//		Image im = new Image(_url.toExternalForm());

		runTask();
		Parent root = FXMLLoader.load(getClass().getResource("view/Login.fxml"));
		Scene scene  = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Auction System");
		primaryStage.show();
		
	}

}
