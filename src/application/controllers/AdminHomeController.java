package application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;


public class AdminHomeController extends NextPageController {
    @FXML
    public void goToCreateItem(ActionEvent event){
        // TODO
        // Change to nextpage();
        try {
            nextPage(event, "../view/CreateItem.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goToReviewItem(ActionEvent event){
        try {
            nextPage(event, "../view/ItemReview.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
