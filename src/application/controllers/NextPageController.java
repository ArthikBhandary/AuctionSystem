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

/**
 * Class which defines methods to traverse to different pages
 * Recommended to inherit every controller from this class or one of it's subclass
 */
public class NextPageController {

    /**
     * Opens a FXML file, in stage in which event is present
     * @param event event which triggered the function call
     * @param FXMLName the fxml filename which is to be the next page
     * @throws IOException
     */
    public void nextPage(ActionEvent event, String FXMLName) throws IOException {

        Parent pane;
        pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(FXMLName)));
        Scene scene = new Scene(pane);
        Stage curStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        curStage.setScene(scene);
        curStage.show();
    }

    @FXML
    public void home(ActionEvent event) {
        try {
            nextPage(event, "../view/Home.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
