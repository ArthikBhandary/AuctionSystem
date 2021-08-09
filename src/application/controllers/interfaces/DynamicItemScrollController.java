package application.controllers.interfaces;

import application.controllers.NextPageController;
import application.models.Item;
import javafx.application.Platform;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

/**
 * Abstract class to define a controller to scene which has a scroll pane which displays a list of objects
 */
public abstract class DynamicItemScrollController extends NextPageController {
    public void initialize() {
        scrollPaneUpdate();
    }

    void scrollPaneUpdate() {
        VBox content = new VBox();
        ArrayList<Item> itemArrayList = Item.getObjectList();
        for (Item item : itemArrayList) {
            new Thread(() -> {
                if (checkItem(item)) {
                    Platform.runLater(() -> {
                        content.getChildren().add(getItemNode(item));
                    });
                }
            }).start();

        }
        content.setSpacing(50);
        getScrollPane().setContent(content);
    }

    protected boolean checkItem(Item item) {
        return true;
    }

    abstract protected ScrollPane getScrollPane();

    abstract protected VBox getItemNode(Item item);

}
