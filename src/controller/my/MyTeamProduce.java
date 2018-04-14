package controller.my;

import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import controller.main.LoginPage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import service.my.MyTeamProduceServiceInf;
import vo.TeamVO;

public class MyTeamProduce {
	String localhost = LoginPage.getLocalhost();
	private Stage primaryStage;
	private MyTeamProduceServiceInf service;
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}


    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button btnTeamProduceJoinYes;  // 팀 생성  button
    @FXML
    private TextField txtFieldTeamName;   //팀 명
    @FXML
    private TextField txtFieldTatalNumberPeople;   // 팀 총인원수
    @FXML
    private TextArea tetAreaExplanation;  // 팀 소개
    @FXML
    private DatePicker datePickerStartDate; // 시작 날짜
    @FXML
    private DatePicker datePickerDeadline; // 마감 날짜
    @FXML
    private RadioButton Private; // 팀 비공개
    @FXML
    private ToggleGroup group;  //팀 공개 여부 선택 
    @FXML
    private RadioButton Open; // 팀 공개
    
    /**
     *  팀 생성 하여 DB로 전송
     * @param event
     * @return 
     */
    @FXML
    public LocalDate onbtnTeamProduceJoinYes(ActionEvent event) {
    	TeamVO insertTeam = new TeamVO();
    	insertTeam.setTm_nm(txtFieldTeamName.getText());
    	insertTeam.setTm_pn(txtFieldTatalNumberPeople.getAnchor());
    	
//    	LocalDate date1 = LocalDate.now() ;
//    	date1 = datePickerStartDate.getValue();
    	LocalDate date1 = datePickerDeadline.getValue();
    	if(date1 ==null) {
    		return date1;
    	}
    	
    	insertTeam.setTm_rec_ed_dt(date1.toString());
    	
    	LocalDate date2 = datePickerStartDate.getValue();
    	insertTeam.setTm_rec_st_dt(date2.toString());
    	insertTeam.setTm_pf(tetAreaExplanation.getText());
    	String visibility = "";
    	System.out.println("====================================");
    	if(group.getSelectedToggle() !=null) {
    		
    		visibility = group.getSelectedToggle().getUserData().toString();
    		System.out.println(visibility);
    		insertTeam.setTm_op(visibility);
    		
    		Alert produce = new Alert(AlertType.INFORMATION);
    		
    		produce.setTitle("팀이 생성 성공");
    		produce.setHeaderText("팀생성 성공");
    		produce.showAndWait();
    		
    		
    	}else {
//    		alert("팀 공개/비공개을 선택하세요!!");
    		return null;
    	}
    	try {
    		int result = service.insertTeam(insertTeam);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return date2;
    	
    	
    }

    @FXML
    void initialize() {
        assert btnTeamProduceJoinYes != null : "fx:id=\"btnTeamProduceJoinYes\" was not injected: check your FXML file 'MyTeamProduce.fxml'.";
        assert txtFieldTeamName != null : "fx:id=\"txtFieldTeamName\" was not injected: check your FXML file 'MyTeamProduce.fxml'.";
        assert txtFieldTatalNumberPeople != null : "fx:id=\"txtFieldTatalNumberPeople\" was not injected: check your FXML file 'MyTeamProduce.fxml'.";
        assert tetAreaExplanation != null : "fx:id=\"tetAreaExplanation\" was not injected: check your FXML file 'MyTeamProduce.fxml'.";
        assert datePickerStartDate != null : "fx:id=\"datePickerStartDate\" was not injected: check your FXML file 'MyTeamProduce.fxml'.";
        assert datePickerDeadline != null : "fx:id=\"datePickerDeadline\" was not injected: check your FXML file 'MyTeamProduce.fxml'.";
        assert Private != null : "fx:id=\"Private\" was not injected: check your FXML file 'MyTeamProduce.fxml'.";
        assert group != null : "fx:id=\"group\" was not injected: check your FXML file 'MyTeamProduce.fxml'.";
        assert Open != null : "fx:id=\"Open\" was not injected: check your FXML file 'MyTeamProduce.fxml'.";

    }
}
