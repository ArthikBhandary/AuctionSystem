package application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;


/**
 * AdminHome.fxml uses this controller
 * It is used to display and produces the functionalities of the admin home page
 */
public class AdminHomeController extends NextPageController {
    /**
     * It changes the page/scene and goes to the createItem page (CreateItem.fxml)
     * @param event The event that triggers the functions
     */
    @FXML
    public void goToCreateItem(ActionEvent event) {
        try {
            nextPage(event, "../view/CreateItem.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * It changes the page/scene and goes to reviewItem page (ItemReview.fxml)
     * @param event that triggers the function
     */
    @FXML
    public void goToReviewItem(ActionEvent event) {
        try {
            nextPage(event, "../view/ItemReview.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
