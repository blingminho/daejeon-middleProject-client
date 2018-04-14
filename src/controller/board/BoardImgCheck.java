package controller.board;

import java.net.URL;
import java.util.ResourceBundle;

import controller.main.LoginPage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class BoardImgCheck {
	String localhost = LoginPage.getLocalhost();
	

	private static Stage primaryStage;
	
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
	public static void setParentStage(Stage parentStage) {
		primaryStage = parentStage;
	}
	
	private static Image img;
	
	/**
	 * 글쓰기에서 올린 이미지를 확인하기 위해 제공
	 * @return 글쓰기에 블러온 이미지
	 */
	public static Image getImg() {
		return img;
	}
	
	/**
	 * 글쓰기에서 불러온 이미지를 저장.
	 * @param 글쓰기에서 불러온 이미지
	 */
	public static void setImg(Image inputImg) {
		img = inputImg;
	}

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView imageView;

    @FXML
    private Button ok;

    @FXML
    void btnOk(ActionEvent event) {
    	Stage ok = (Stage) this.ok.getScene().getWindow();
    	ok.close();
    }

    @FXML
    void initialize() {
        assert imageView != null : "fx:id=\"imageView\" was not injected: check your FXML file 'BoardImgCheck.fxml'.";
        assert ok != null : "fx:id=\"ok\" was not injected: check your FXML file 'BoardImgCheck.fxml'.";

        imageView.setImage(img);
    }
}
