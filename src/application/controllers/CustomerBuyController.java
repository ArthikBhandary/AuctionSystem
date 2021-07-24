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
import static application.messages.MessageDisplay.infoBox;

public class CustomerBuyController extends DynamicItemScrollController{

    @FXML
    protected ScrollPane scrollPane;

    @Override
    protected ScrollPane getScrollPane() {
        return scrollPane;
    }


    @Override
    protected boolean checkItem(Item item){
        try {
            return item.isSold() && item.getUserId() == State.getUser().getId() ;
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    public void back(ActionEvent event){
        try {
            nextPage(event, "fxml/Home.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected VBox getItemNode(Item item){
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

        Text prevBidText =  new Text("Price : " + item.getBid());
        prevBidText.setStyle("-fx-font: 20 arial;");
        HBox prevBidBox = new HBox(prevBidText);
        prevBidBox.setAlignment(Pos.CENTER);

        VBox node = new VBox();
        node.setMaxWidth(390);
        node.setSpacing(20);
        node.setAlignment(Pos.CENTER);
        if (item.isPaid()) {

            Text payText = new Text("Gpay number ");
            payText.setStyle("-fx-font: 20 arial;");
            TextField payField = new TextField();
            HBox newBidBox = new HBox(payText, payField);
            Button bidButton = new Button("Pay");

            node = new VBox(nameBox, imageView, descriptionBox, prevBidBox, newBidBox, bidButton);

            VBox finalNode = node;
            EventHandler<ActionEvent> event = e -> {
                String number = payField.getText();

                if(number.isEmpty() || number.matches("\\d{10}")){
                    infoBox("Enter your gpay number", "Gpay number is invalid", null);
                    return;
                }

                try {
                    item.buyItem(number);
                    finalNode.getChildren().remove(bidButton);
                    finalNode.getChildren().add(new Text("Paid"));

                } catch (UserNotFoundException userNotFoundException) {
                    userNotFoundException.printStackTrace();
                    infoBox("If you think this is a mistake contact us at our mail","The item isn't sold to your account", "Illegal Buy");
                }
            };
            bidButton.setOnAction(event);

        } else {
            Text userInfoText = new Text("Bought ");
            descriptionText.setStyle("-fx-font: 20 arial;");
            HBox userInfoBox = new HBox(userInfoText);
            node = new VBox(nameBox, imageView, descriptionBox, prevBidBox, userInfoBox);
        }

        return node;
    }
}
