package application.controllers;

import application.State.State;
import application.controllers.interfaces.DynamicItemScrollController;
import application.exceptions.UserNotFoundException;
import application.models.Item;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
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
import static application.messages.MessageDisplay.showAlert;


/**
 * Controller for CustomerBid.fxml
 * Creates a page which displays the list of items on which the customers can bid
 */
public class CustomerBidController extends DynamicItemScrollController{
    @FXML
    private ScrollPane scrollPane;

    @FXML
    public void back(ActionEvent event){
        try {
            nextPage(event, "../view/Home.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected ScrollPane getScrollPane() {
        return scrollPane;
    }

    @Override
    protected boolean checkItem(Item item){
        return !item.isSold();
    }

    protected VBox getItemNode(Item item){
        System.out.println("resources/" + item.getImagePath());
        File file = new File(PROJECT_DIR + RESOURCE_DIR + item.getImagePath());
        Image image = new Image(file.getPath());
        ImageView imageView= new ImageView(image);
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
        descriptionBox.setMaxWidth(390);
        descriptionBox.setAlignment(Pos.CENTER);

        Text prevBidText =  new Text("Current Bid : " + item.getBid());
        prevBidText.setStyle("-fx-font: 20 arial;");
        HBox prevBidBox = new HBox(prevBidText);
        prevBidBox.setAlignment(Pos.CENTER);

        VBox node = new VBox();
        node.setMaxWidth(390);
        node.setSpacing(20);
        node.setAlignment(Pos.CENTER);
        try {
            if (State.getUser().getId() != item.getUserId()) {

                Text newBidText = new Text("Your Bid ");
                newBidText.setStyle("-fx-font: 20 arial;");
                TextField newBidField = new TextField();
                HBox newBidBox = new HBox(newBidText, newBidField);
                newBidBox.setAlignment(Pos.CENTER);
                newBidBox.setMaxWidth(390);
                Button bidButton = new Button("Bid");

                node = new VBox(nameBox, imageView, descriptionBox, prevBidBox, newBidBox, bidButton);

                VBox finalNode = node;
                EventHandler<ActionEvent> event = e -> {
                    int amount = Integer.parseInt(newBidField.getText());
                    try {
                        if(item.setBid(amount)) {
                            finalNode.getChildren().remove(bidButton);
                            finalNode.getChildren().remove(newBidBox);
                            finalNode.getChildren().add(new Text("Succesffuly placed a bid"));
                            prevBidText.setText("Current Bid : " + item.getBid());
                        } else {
                            infoBox("Please reload the page and try again", "Bid Failed", "Something went wrong");
                        }
                    } catch (UserNotFoundException userNotFoundException) {
                        userNotFoundException.printStackTrace();
                    }
                };
                bidButton.setOnAction(event);

            } else {
                Text userInfoText = new Text("Your bid is the highest bid now  ");
                userInfoText.setStyle("-fx-font: 20 arial;");
                HBox userInfoBox = new HBox(userInfoText);
                userInfoBox.setMaxWidth(390);
                userInfoBox.setAlignment(Pos.CENTER);
                node = new VBox(nameBox, imageView, descriptionBox, prevBidBox, userInfoBox);
            }
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }

        return node;

    }
}
