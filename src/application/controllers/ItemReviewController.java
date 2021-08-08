package application.controllers;

import application.controllers.interfaces.DynamicItemScrollController;
import application.exceptions.UserNotFoundException;
import application.models.Item;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.File;

import static application.CONSTANTS.PATH.PROJECT_DIR;
import static application.CONSTANTS.PATH.RESOURCE_DIR;
import static application.State.State.getAdmin;
import static application.messages.MessageDisplay.infoBox;

/**
 * ItemReview.fxml uses this controller
 * This is an admin page, where can admin stop the bidding of any ongoing item
 */
public class ItemReviewController extends DynamicItemScrollController {

    @FXML
    protected ScrollPane scrollPane;

    @Override
    protected ScrollPane getScrollPane() {
        return scrollPane;
    }

    /**
     * @param item the item to be checked
     * @return true if the item should be included in the list
     * i.e, the bidding on the item is still going on
     */
    @Override
    protected boolean checkItem(Item item) {
        return !item.isSold();
    }

    /**
     * Returns a node to display the item, with fields to pay the amount if not already paid
     *
     * @param item the item which is to be displayed
     * @return Vbox displaying the node
     */
    protected VBox getItemNode(Item item) {
        System.out.println("resources/" + item.getImagePath());
        Image image;
        ImageView imageView;
        File file = new File(PROJECT_DIR + RESOURCE_DIR + item.getImagePath());
        image = new Image(file.getPath());

        imageView = new ImageView(image);
        imageView.setFitWidth(400);
        imageView.setPreserveRatio(true);
        Text nameText = new Text(item.getName());
        nameText.setStyle("-fx-font: 30 arial;");
        HBox nameBox = new HBox(nameText);
        nameBox.setAlignment(Pos.CENTER);
        TextArea descriptionText = new TextArea("Description : " + item.getDescription());
        descriptionText.setStyle("-fx-font: 20 arial;");
        HBox descriptionBox = new HBox(descriptionText);
        descriptionBox.setAlignment(Pos.CENTER);
        Button finishBidButton = new Button("Finish Auction");
        VBox node = new VBox(nameBox, imageView, descriptionBox, finishBidButton);
        node.setSpacing(20);
        node.setMaxWidth(390);
        EventHandler<ActionEvent> event = e -> {
            try {
                // Finish the biding of the item
                getAdmin().finishBid(item);
                node.getChildren().remove(finishBidButton);
                node.getChildren().add(new Text("Auction Finished"));

            } catch (UserNotFoundException userNotFoundException) {
                userNotFoundException.printStackTrace();
                infoBox("If you think this is a mistake contact us at our mail", "Bidding wasn't stopped", "Something went wrong");
            }
        };
        finishBidButton.setOnAction(event);
        node.setAlignment(Pos.CENTER);
        return node;
    }

}
