package controller.my;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import controller.main.MenuPage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class My {
	
	// 내창
	private static VBox vbox;
	public static VBox getParentNode() {
		return vbox;
	}
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox myPane;

    @FXML
    void initialize() {
    	vbox = myPane;
    	
        try {
        	String selectedItem = MenuPage.getSelected();
//        	MyMenu.setParentPane(myPane);
        	
        	Node menu = FXMLLoader.load(this.getClass().getResource("../../view/my/MyMenu.fxml"));
        	Node button = null;
        	if(selectedItem.equals("MyHome")) {
        		//프로필 관리
        		button = FXMLLoader.load(this.getClass().getResource("../../view/my/MyHome.fxml"));
        		
        	} else if(selectedItem.equals("MySchedule")) {
        		//내일정 적용 예정
        		button = FXMLLoader.load(this.getClass().getResource("../../view/team/TeamSchedule.fxml"));
        		
        	} else if(selectedItem.equals("MyList")) {
        		//참여 팀 목록
        		button = FXMLLoader.load(this.getClass().getResource("../../view/my/MyList.fxml"));
        	}
        	
        	myPane.getChildren().setAll(menu, button);

        	
		} catch (IOException e) {
			e.printStackTrace();
		}

    }
}
