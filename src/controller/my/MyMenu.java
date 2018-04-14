package controller.my;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import controller.main.MenuPage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

public class MyMenu {
	//부모창
    private static VBox vbox;
    
	
	// 마지막으로 눌린 Label 저장
	private Label lastClickedLabeled = null;
	
	private void effectLastCliked(Label lastClickedLabel, Label nowClickedLabel) {
		if(lastClickedLabel != null) {
			lastClickedLabel.setTextFill(Paint.valueOf("Black"));
		}
		nowClickedLabel.setTextFill(Paint.valueOf("Blue"));
	}
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label lblProfile;  //포로필 관리

    @FXML
    private Label lblMyCalendar;  //내 일정

    @FXML
    private Label lblParticipationList;  //참여 팀 목록
    
    /**
     *  내 일정(수정 진행 할 예정)
     * @param event
     */
    @FXML
    void LabelMyCalendarClicked(MouseEvent event) {
    	try {
    		effectLastCliked(lastClickedLabeled, lblMyCalendar);
        	lastClickedLabeled = lblMyCalendar;
        	MenuPage.setSeleted("MySchedule");
        	Node content = FXMLLoader.load(this.getClass().getResource("../../view/team/TeamSchedule.fxml"));
        	vbox.getChildren().set(1, content);
        	lastClickedLabeled.getChildrenUnmodifiable().setAll(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 참여팀 목록
     * @param event
     */
    @FXML
    void LabelParticipationListClicked(MouseEvent event) {
    	try {
    		effectLastCliked(lastClickedLabeled, lblParticipationList);
        	lastClickedLabeled = lblParticipationList;
        	Node content = FXMLLoader.load(this.getClass().getResource("../../view/my/MyList.fxml"));
        	
        	vbox.getChildren().set(1, content);
        	lastClickedLabeled.getChildrenUnmodifiable().addAll(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     *  프로필 관리
     * @param event
     */
    @FXML
    void onLabelProfileClicked(MouseEvent event) {
    	try {
    		effectLastCliked(lastClickedLabeled, lblProfile);
    		lastClickedLabeled = lblProfile;
    		Node content = FXMLLoader.load(this.getClass().getResource("../../view/my/MyHome.fxml"));
    		vbox.getChildren().set(1, content);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void initialize() {
    	vbox = My.getParentNode();
    	
    	String selectedItem = MenuPage.getSelected();
        if (selectedItem.equals("")) {
        	effectLastCliked(lastClickedLabeled, lblProfile);
        	lastClickedLabeled = lblProfile;
		}
        if (selectedItem.equals("MySchedule")) {
        	effectLastCliked(lastClickedLabeled, lblMyCalendar);
        	lastClickedLabeled = lblMyCalendar;
		} else if (selectedItem.equals("MyList")) {
			effectLastCliked(lastClickedLabeled, lblParticipationList);
			lastClickedLabeled = lblParticipationList;
		} else if (selectedItem.equals("MyHome")) {
			effectLastCliked(lastClickedLabeled, lblProfile);
        	lastClickedLabeled = lblProfile;
		}
        
        
    }
    
    

	
	
	
}
