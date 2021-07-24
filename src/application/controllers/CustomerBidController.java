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
import javafx.scene.control.TextArea;
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


public class CustomerBidController extends DynamicItemScrollController{
    @FXML
    private ScrollPane scrollPane;

    @FXML
    public void back(ActionEvent event){
        try {
            nextPage(event, "fxml/Home.fxml");
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
                Button bidButton = new Button("Bid");

                node = new VBox(nameBox, imageView, descriptionBox, prevBidBox, newBidBox, bidButton);

                VBox finalNode = node;
                EventHandler<ActionEvent> event = e -> {
                    int amount = Integer.parseInt(newBidField.getText());
                    try {
                        item.setBid(amount);
                        finalNode.getChildren().remove(bidButton);
                        finalNode.getChildren().add(new Text("You've bid on this"));

                    } catch (UserNotFoundException userNotFoundException) {
                        userNotFoundException.printStackTrace();
                    }
                };
                bidButton.setOnAction(event);

            } else {
                Text userInfoText = new Text("Your Bid ");
                userInfoText.setStyle("-fx-font: 20 arial;");
                HBox userInfoBox = new HBox(userInfoText);
                node = new VBox(nameBox, imageView, descriptionBox, prevBidBox, userInfoBox);
            }
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }

        return node;

    }
}
