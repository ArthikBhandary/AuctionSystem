package application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class NextPageController {
     public void nextPage(ActionEvent event, String FXMLName) throws IOException {

        Parent pane;
        System.out.println(FXMLName);
        pane = (AnchorPane) FXMLLoader.load(Objects.requireNonNull(getClass().getResource(FXMLName)));
        Scene scene = new Scene(pane);
        Stage curStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        curStage.setScene(scene);
        curStage.show();
    }

    @FXML
    public void home(ActionEvent event) {
        try {
            System.out.println("Hello");
            nextPage(event, "../view/Home.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
