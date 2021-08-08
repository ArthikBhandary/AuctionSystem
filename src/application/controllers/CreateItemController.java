package application.controllers;

import application.models.Item;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

import static application.CONSTANTS.PATH.RESOURCE_DIR;
import static application.messages.MessageDisplay.showAlert;


/**
 * Controller of CreateItem.fxml
 * Used to create a new item
 * Accepts image, name and description of an item
 */
public class CreateItemController extends NextPageController {

    /**
     * image uploaded to be used for the item
     */
    private File file;

    @FXML
    private TextField nameField;

    @FXML
    private TextArea descriptionAreaField;

    @FXML
    private ImageView imageView;

    /**
     * Used to open the fileChooser window and save and display the file chosen.
     * @param event that triggers the function
     */
    @FXML
    public void imageUpload(ActionEvent event) {
        FileChooser chooser = new FileChooser();

        // set title
        chooser.setTitle("Select File");

        // set initial directory
        chooser.setInitialDirectory(new File("e:\\"));
        // Only accept images
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        // Open the dialog/window so the user can choose an image
        file = chooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
        if (file != null) {
            // If an image has been chosen, create an image preview
            Image uploadedImage = new Image(file.toURI().toString());
            imageView.setImage(uploadedImage);
        }

    }

    /**
     * To return to AdminLogin Page
     * @param event event that results in the triggering of the function call
     */
    @FXML
    public void back(ActionEvent event) {
        // get the file selected
        try {
            nextPage(event, "../view/AdminLogin.fxml");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }


    /**
     * Function to be called on submission
     * Displays alert if any of the fields are empty
     * Else creates a new item, saves it to the db and redirect to home page
     * @param event event that results in the triggering of the function call
     */
    @FXML
    public void submit(ActionEvent event) {
        Window window = ((Node) event.getSource()).getScene().getWindow();
        //Show alert if nameField is empty
        if (nameField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, window, "Form Error!",
                    "Please enter a name");
            return;
        }
        //Show alert if descriptionAreaField is empty
        if (descriptionAreaField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, window, "Form Error!",
                    "Please enter description");
            return;
        }
        //Show alert if no file has been chosen
        if (file == null) {
            showAlert(Alert.AlertType.ERROR, window, "Form Error!",
                    "Please enter an image");
            return;
        }
        String pathname = "images/" + UUID.randomUUID() + file.getName();
        // Store images in images folder and prefix the file name with random unique UUID so as to avoid duplication
        // TODO
        // Store images in database or use static/media server to upload and host images
        File file2 = new File(RESOURCE_DIR + pathname);
        try {
            Files.copy(file.toPath(), file2.toPath());
            // Copy and Save the file to RESOURCE_DIR
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        // Create an item and insert it to the db
        insertToDB(nameField.getText(), descriptionAreaField.getText(), pathname);
        try {
            nextPage(event, "../view/AdminHome.fxml");
        } catch (IOException except) {
            System.out.println(except);
            showAlert(Alert.AlertType.ERROR, window, "Something went wrong",
                    "Couldn't load next Page!");
        }
    }


    /**
     * Create an item and insert it to the db
     * @param name name of the item to be inserted
     * @param description description of the item to be inserted
     * @param pathname path where the image
     */
    private void insertToDB(String name, String description, String pathname) {
        Item.createItem(name, description, pathname);

    }

}
