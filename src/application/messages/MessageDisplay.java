package application.messages;

import javafx.scene.control.Alert;
import javafx.stage.Window;

/**
 * Class that contains methods to create windows which display messages
 */
public class MessageDisplay {
    /**
     * To create a window to display some information or message.
     * @param infoMessage More information
     * @param headerText Header text of the information
     * @param title title of the window
     */
    public static void infoBox(String infoMessage, String headerText, String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

    /**
     * To display a alert or window to showcase any warnings or errors
     * @param alertType The type of alert
     * @param owner   The window which calls this
     * @param title Tile of the alert
     * @param message   Message/information on the alert
     */
    public static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}
