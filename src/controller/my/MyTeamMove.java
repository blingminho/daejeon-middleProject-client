package controller.my;

import java.net.URL;
import java.util.ResourceBundle;

import controller.main.LoginPage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MyTeamMove {
	String localhost = LoginPage.getLocalhost();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnApplicableTeamJoinYes;

    @FXML
    void nobtnApplicableTeamJoinYesClicked(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert btnApplicableTeamJoinYes != null : "fx:id=\"btnApplicableTeamJoinYes\" was not injected: check your FXML file 'MyTeamMove.fxml'.";

    }
}
