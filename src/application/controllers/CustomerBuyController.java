package application.controllers;

import application.State.State;
import application.controllers.interfaces.DynamicItemScrollController;
import application.exceptions.UserNotFoundException;
import application.models.Item;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;

import static application.CONSTANTS.PATH.PROJECT_DIR;
import static application.CONSTANTS.PATH.RESOURCE_DIR;
import static application.messages.MessageDisplay.infoBox;

/**
 * CustomerBuy.fxml uses this controller
 * It is used to buy an item, on which the highest bid was done by the user
 */
public class CustomerBuyController extends DynamicItemScrollController {

    @FXML
    protected ScrollPane scrollPane;

    @Override
    protected ScrollPane getScrollPane() {
        return scrollPane;
    }


    /**
     * Condition to check which items to be added to the scroll pane
     * @param item the item to be checked
     * @return true if the item should be included in the list, i.e, the bidding on the item has stopped and the user had highest bid
     */
    @Override
    protected boolean checkItem(Item item) {
        try {
            System.out.println(item.isSold() && item.getUserId() == State.getUser().getId());
            return item.isSold() && item.getUserId() == State.getUser().getId();
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Go to the previous page, that is, the home page
     * @param event event which triggered the function call
     */
    @FXML
    public void back(ActionEvent event) {
        try {
            nextPage(event, "../view/Home.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a node to display the item, with fields to pay the amount if not already paid
     *
     * @param item the item which is to be displayed
     * @return Vbox displaying the node
     */
    @Override
    public VBox getItemNode(Item item) {
        File file = new File(PROJECT_DIR + RESOURCE_DIR + item.getImagePath());
        Image image = new Image(file.getPath());

        ImageView imageView = new ImageView(image);
        imageView.maxWidth(390);
        imageView.setFitWidth(390);
        imageView.setPreserveRatio(true);

        Text nameText = new Text(item.getName());
        nameText.maxWidth(390);
        nameText.setWrappingWidth(390);
        nameText.setStyle("-fx-font: 30 arial;");
        HBox nameBox = new HBox(nameText);
        nameBox.maxWidth(390);
        nameBox.setAlignment(Pos.CENTER);

        Text descriptionText = new Text("Description : " + item.getDescription());
        descriptionText.setStyle("-fx-font: 20 arial;");
        descriptionText.setWrappingWidth(390);
        HBox descriptionBox = new HBox(descriptionText);
        descriptionBox.maxWidth(390);
        descriptionBox.setAlignment(Pos.CENTER);

        Text prevBidText = new Text("Price : " + item.getBid());
        prevBidText.setStyle("-fx-font: 20 arial;");
        HBox prevBidBox = new HBox(prevBidText);
        prevBidBox.setMaxWidth(390);
        prevBidBox.setAlignment(Pos.CENTER);


        VBox node;

        if (!item.isPaid()) {
            // To buy an item
            Text payText = new Text("Gpay number ");
            payText.setStyle("-fx-font: 20 arial;");
            TextField payField = new TextField();
            HBox newBidBox = new HBox(payText, payField);
            Button bidButton = new Button("Pay");
            newBidBox.setAlignment(Pos.CENTER);
            node = new VBox(nameBox, imageView, descriptionBox, prevBidBox, newBidBox, bidButton);

            EventHandler<ActionEvent> event = e -> {
                String number = payField.getText();
                // Check if the number enter is empty or does not have 10 digits (987654321) or number with country code (+91987654310)
                if (number.isEmpty() || !number.matches("(\\+\\d{2})?\\d{10}")) {
                    infoBox("Enter proper gpay number", "Gpay number is invalid", null);
                    return;
                }

                try {
                    // Buy the item and remove the bid button and payment area for this item
                    item.buyItem(number);
                    node.getChildren().remove(bidButton);
                    node.getChildren().remove(newBidBox);
                    node.getChildren().add(new Text("Paid"));

                } catch (UserNotFoundException userNotFoundException) {
                    userNotFoundException.printStackTrace();
                }
            };
            bidButton.setOnAction(event);

        } else {
            // If item is already bought
            Text userInfoText = new Text("Bought ");
            userInfoText.setStyle("-fx-font: 20 arial;");
            HBox userInfoBox = new HBox(userInfoText);
            node = new VBox(nameBox, imageView, descriptionBox, prevBidBox, userInfoBox);
        }
        node.setAlignment(Pos.CENTER);
        node.setMaxWidth(390);
        node.setSpacing(10);

        return node;
    }
}
