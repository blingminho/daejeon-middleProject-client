package controller.admin;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class Admin {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox adminPane;

    @FXML
    void initialize() {
        assert adminPane != null : "fx:id=\"adminPane\" was not injected: check your FXML file 'Admin.fxml'.";

    }
}
