package application.controllers.interfaces;

import application.controllers.NextPageController;
import application.models.Item;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public abstract class DynamicItemScrollController extends NextPageController {

    public void initialize(){
        Platform.runLater(this::scrollPaneUpdate);
    }

    void scrollPaneUpdate(){
        VBox content = new VBox();
        ArrayList<Item> itemArrayList = Item.getObjectList();
        for(Item item: itemArrayList){
            System.out.println();
            if(checkItem(item)){
                content.getChildren().add(getItemNode(item));
            }
        }
        content.setSpacing(50);
        getScrollPane().setContent(content);
    }

    protected boolean checkItem(Item item){
        return true;
    }
    abstract protected ScrollPane getScrollPane();

    abstract protected VBox getItemNode(Item item);

}
