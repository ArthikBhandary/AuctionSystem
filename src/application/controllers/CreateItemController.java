package application.controllers;

import application.models.Item;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.event.ActionEvent;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

import static application.messages.MessageDisplay.showAlert;


//TODO
// Make use of fxml files
public class CreateItemController extends NextPageController {

    private File file;


    @FXML
    private TextField nameField;

    @FXML
    private TextArea descriptionAreaField;

    @FXML
    private ImageView imageView;

    @FXML
    public void imageUpload(ActionEvent event){
        FileChooser chooser = new FileChooser();

        // set title
        chooser.setTitle("Select File");

        // set initial File
        chooser.setInitialDirectory(new File("e:\\"));
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        file = chooser.showOpenDialog(((Node)event.getSource()).getScene().getWindow());
        if (file != null) {
            Image uploadedImage = new Image(file.toURI().toString());
            imageView.setImage(uploadedImage);
        }

    }

    @FXML
    public void back(ActionEvent event){
        // get the file selected
        try {
            nextPage(event, "../view/Admin_login.fxml");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }


    @FXML
    public void submit(ActionEvent event){
        Window window = ((Node)event.getSource()).getScene().getWindow();
        if (nameField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, window, "Form Error!",
                    "Please enter a name");
            return;
        }
        if (descriptionAreaField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, window, "Form Error!",
                    "Please enter description");
            return;
        }
        if(file == null){
            showAlert(Alert.AlertType.ERROR, window, "Form Error!",
                    "Please enter an image");
            return;
        }
        String location = "src/application/resources/";
        String pathname = "images/" + UUID.randomUUID().toString() + file.getName();
        System.out.println(pathname);
        File file2 = new File(location + pathname);
        System.out.println(file2.getAbsolutePath());
        try {
            Files.copy(file.toPath(), file2.toPath());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        insertToDB(nameField.getText(), descriptionAreaField.getText(), pathname);
        try {
            nextPage(event,"../view/AdminHome.fxml" );
        } catch (IOException except){
            System.out.println(except);
            showAlert(Alert.AlertType.ERROR, window, "Something went wrong",
                    "Couldn't load next Page!");
        }
    }


    private void insertToDB(String name, String description, String pathname) {
        Item.createItem(name, description, pathname);

    }

}
