package controller.admin;

import java.net.URL;
import java.util.ResourceBundle;

import controller.main.LoginPage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class TeamPTBUpLoad {
	String localhost = LoginPage.getLocalhost();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox PTBPane;

    @FXML
    private TextField PTBTitle;

    @FXML
    private TextField PTBAddPhoto;

    @FXML
    void findClick(ActionEvent event) {

    }

    @FXML
    void upLoadClick(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert PTBPane != null : "fx:id=\"PTBPane\" was not injected: check your FXML file 'TeamPTBUpLoad.fxml'.";
        assert PTBTitle != null : "fx:id=\"PTBTitle\" was not injected: check your FXML file 'TeamPTBUpLoad.fxml'.";
        assert PTBAddPhoto != null : "fx:id=\"PTBAddPhoto\" was not injected: check your FXML file 'TeamPTBUpLoad.fxml'.";

    }
}
