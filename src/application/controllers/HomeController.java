package application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

/**
 * Home.fxml uses this controller
 * It is used to display the home page and redirect the user to different based on their choices
 */
public class HomeController extends NextPageController {

    /**
     * Go to admin login page (AdminLogin.fxml)
     * @param event event which triggers the function
     */
    @FXML
    public void adminLogin(ActionEvent event){
        //msg.setText("yolo");
        System.out.println("To enter starting bidding prices");
        try {
            nextPage(event, "../view/AdminLogin.fxml");
        } catch (IOException e) {
            System.out.println(e);
        }

    }
    /**
     * Go to item bidding page (CustomerBid.fxml)
     * @param event event which triggers the function
     */
    @FXML
    public void bid(ActionEvent event) {
        try {
            nextPage(event, "../view/CustomerBid.fxml");
        } catch (IOException e) {
            System.out.println(e);
        }

    }
    /**
     * Go to item buying page (CustomerBuy.fxml)
     * @param event event which triggers the function
     */
    @FXML
    public void buy(ActionEvent event) {
        try {
            nextPage(event, "../view/CustomerBuy.fxml");
        } catch (IOException e) {
            System.out.println(e);
        }
    }


}
