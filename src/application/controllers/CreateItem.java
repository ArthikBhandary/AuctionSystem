package application.controllers;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.*;
import java.util.UUID;

import static application.DBInterface.getConnection;
import static application.messages.MessageDisplay.showAlert;

public class CreateItem extends NextPageController {

    private FileChooser fileChooser;
    private ImageView imageView;
    private Label fileLabel;
    private Stage stage;
    private Button fileUploadButton, submitButton;
    private TextField  nameField, descriptionField;
    private Text nameText, descriptionText;
    private File file;


    CreateItem(Stage stage){
        this.stage = stage;
        imageView = getImageView();
        fileChooser = getFileChooser();
        fileLabel = new Label("no files selected");
        fileUploadButton = getFileUploadButton();
        nameField = getNameField();
        nameText = new Text("Name :");
//        Name = new TextField();

        descriptionField = getDescriptionField();
        descriptionText = new Text("Details");
        submitButton = getSubmitButton();
    }

    private TextField getDescriptionField() {
        TextField field = getNameField();
        field.setPromptText("Enter Description");
//        field.setMinHeight(00);
        return field;
    }

    private TextField getNameField() {
        TextField nameField = new TextField();
//        nameField.setAlignment(Pos.CENTER);
//        nameField.setMaxWidth(100);
        nameField.setPromptText("Enter Name");

//        RequiredFieldValidator validator = new RequiredFieldValidator();
//        validator.setMessage("Input Required");
//        nameField.getValidators().add(validator);
//        nameField.focusedProperty().addListener((o,oldVal,newVal)->{
//            if(!newVal) validator.validate();
//        });
        return nameField;
    }

    private ImageView getImageView() {
        ImageView imageV = new ImageView();
        imageV.setFitHeight(200);
        imageV.setPreserveRatio(true);
        return imageV;
    }

    private FileChooser getFileChooser(){
        FileChooser chooser = new FileChooser();

        // set title
        chooser.setTitle("Select File");

        // set initial File
        chooser.setInitialDirectory(new File("e:\\"));
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        return chooser;
    }


    private Button getFileUploadButton(){
        Button button = new Button("Show open dialog");

        // create an Event Handler
        EventHandler<ActionEvent> event = e -> {
            // get the file selected
            file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                fileLabel.setText(file.getAbsolutePath()
                        + "  selected");
                Image uploadedImage = new Image(file.toURI().toString());
                imageView.setImage(uploadedImage);
                File file2 = new File("images/23.jpg");
                System.out.println(file2.getName());
            }
        };

        button.setOnAction(event);
        return button;
    }

    private Button getBackButton(){
        Button button = new Button("Show open dialog");

        // create an Event Handler
        EventHandler<ActionEvent> event = e -> {
            // get the file selected
            try {
                nextPage(e, "Admin_login.fxml");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        };

        button.setOnAction(event);
        return button;
    }

    private Button getSubmitButton(){
        Button button = new Button("Submit");

        // create an Event Handler
        EventHandler<ActionEvent> event = e -> {
            Window window = button.getScene().getWindow();
            if (nameField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, window, "Form Error!",
                        "Please enter a name");
                return;
            }
            if (descriptionField.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, window, "Form Error!",
                        "Please enter description");
                return;
            }
            if(file == null){
                showAlert(Alert.AlertType.ERROR, window, "Form Error!",
                        "Please enter an image");
                return;
            }
            String pathname = "images/" + UUID.randomUUID().toString() + file.getName();
            File file2 = new File(pathname);
            try {
                   Files.copy(file.toPath(), file2.toPath());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            insertToDB(nameField.getText(), descriptionField.getText(), pathname);
            try {
                nextPage(e,"Home.fxml" );
            } catch (IOException except){
                System.out.println(except);
                showAlert(Alert.AlertType.ERROR, window, "Something went wrong",
                        "Couldn't load next Page!");
            }
        };

        button.setOnAction(event);
        return button;
    }

    private void insertToDB(String name, String description, String pathname) {
        String query = "INSERT INTO items (name, description, image) VALUES ( ?, ?, ?)";
        Connection conn = null;
        try {
            conn = getConnection();
            System.out.println("Connection is created successfully:");
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setString(3, pathname);
            System.out.println(stmt.toString());

            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    public void openPage() {
        // create a VBox
        TilePane t = new TilePane();
        t.getChildren().addAll(nameText, nameField);
        TilePane t2 = new TilePane();
        t.getChildren().addAll(descriptionText, descriptionField);
        VBox vbox = new VBox(30, imageView, fileLabel, fileUploadButton, t, t2, getSubmitButton(), getBackButton());

        // set Alignment
        vbox.setAlignment(Pos.CENTER);
        // create a scene
        Scene scene = new Scene(vbox, 800, 500);

        // set the scene
        stage.setMaxWidth(400);
        stage.setMaxHeight(800);
        stage.setScene(scene);
    }
}
