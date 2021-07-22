package application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class NextPageController {
    protected void nextPage(ActionEvent event, String FXMLName) throws IOException {
        Parent pane;
        pane = (AnchorPane) FXMLLoader.load(getClass().getResource(FXMLName));
        Scene scene = new Scene(pane);
        Stage curStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        curStage.setScene(scene);
        curStage.show();
    }
}
