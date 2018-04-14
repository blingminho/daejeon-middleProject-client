package controller.team;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import controller.main.LoginPage;
import controller.main.MenuPage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import service.team.ChatServiceClient;
import service.team.ChatServiceServerInf;
import service.team.TeamMenuServiceInf;
/**
 * TeamMenu.fxml과 연결된 controller
 * @author Jun
 *
 */
public class TeamMenu {
	String localhost = LoginPage.getLocalhost();
	// 가입 상태에 따른 메뉴 선택권 서비스
	TeamMenuServiceInf service;
	ChatServiceServerInf server;
	
	// login한 userId (부모 controller의 getUserId() 사용)
	private static String userId;
	// 접근한 teamId (부모 controller의 geTeamId() 사용)
	private static String teamId;
	
	// 부모창
	private static VBox vbox;
	
	// 마지막으로 눌린 Label 저장
	private Label lastClickedLabeled = null;
	
	/**
	 * 지금 누른 메뉴 항목 글씨 색 파란색
	 * @param 전에 눌린 메뉴 항목
	 * @param 지금 누른 메뉴 항목
	 */
	private void effectLastCliked(Label lastClickedLabel, Label nowClickedLabel) {
		if (lastClickedLabel != null) {
			lastClickedLabel.setTextFill(Paint.valueOf("Black"));
		}
		nowClickedLabel.setTextFill(Paint.valueOf("Blue"));
	}

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Label LabelTeamHome;
    @FXML
    private Label LabelTeamMemberProfile;
    @FXML
    private Label LabelTeamChat;
    @FXML
    private Label LabelTeamBoard;
    @FXML
    private Label LabelTeamPhb;
    @FXML
    private Label LabelTeamTravelList;
    @FXML
    private Label LabelTeamSchedule;
    @FXML
    private Label LabelTeamManage;
    @FXML
    private Label LabelTeamSecession;
    
    
    
    @FXML
    void LabelTeamBoardClicked(MouseEvent event) {
    	effectLastCliked(lastClickedLabeled, LabelTeamBoard);
    	lastClickedLabeled = LabelTeamBoard;
    }

    @FXML
    void LabelTeamChatClicked(MouseEvent event) {
    	effectLastCliked(lastClickedLabeled, LabelTeamChat);
    	lastClickedLabeled = LabelTeamChat;

    	String selectedItem = MenuPage.getSelected();
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/team/Chatting.fxml"));
    	
    	try {
    		ChatServiceClient client = new ChatServiceClient();
    		boolean is = server.makeChat(MenuPage.getTeamId());
    		
    		server.joinChat(MenuPage.getTeamId(), client);
    		
    		Parent root = loader.load();
    		
    		Chatting controller = (Chatting)loader.getController();
    		controller.setChat(server, client, MenuPage.getTeamId());
    		
			Stage stage = new Stage();
			stage.initModality(Modality.NONE);
			
			
			Scene scene = new Scene(root);
			stage.setTitle("팀 채팅");
			stage.setScene(scene);
			stage.show();
			
		
			
		} catch (IOException e) {
			e.printStackTrace();
		}

    	
    	
    	
//    	상준~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//    	다른 페이지 이동 예제
//    	Node myTeamList;
//		try {
//			MenuPage.setTeamId("3");
//			myTeamList = FXMLLoader.load(this.getClass().getResource("../../view/team/Team.fxml"));
//			MenuPage.getMenuPane().getChildren().setAll(myTeamList);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
    	
    }

    @FXML
    void LabelTeamHomeClicked(MouseEvent event) {
    	try {
    		effectLastCliked(lastClickedLabeled, LabelTeamHome);
    		lastClickedLabeled = LabelTeamHome;
			Node content = FXMLLoader.load(this.getClass().getResource("../../view/team/TeamHome.fxml"));
			vbox.getChildren().set(1, content);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void LabelTeamManageClicked(MouseEvent event) {
    	try {
    		effectLastCliked(lastClickedLabeled, LabelTeamManage);
        	lastClickedLabeled = LabelTeamManage;
			Node content = FXMLLoader.load(this.getClass().getResource("../../view/team/TeamManage.fxml"));
			vbox.getChildren().set(1, content);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void LabelTeamMemberProfileClicked(MouseEvent event) {
		try {
			effectLastCliked(lastClickedLabeled, LabelTeamMemberProfile);
	    	lastClickedLabeled = LabelTeamMemberProfile;
			Node content = FXMLLoader.load(this.getClass().getResource("../../view/team/TeamMemberProfile.fxml"));
			vbox.getChildren().set(1, content);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void LabelTeamPhbClicked(MouseEvent event) {
    	try {
    		effectLastCliked(lastClickedLabeled, LabelTeamPhb);
    		lastClickedLabeled = LabelTeamPhb;
    		MenuPage.setSeleted("TeamPhb");
    		Node content = FXMLLoader.load(this.getClass().getResource("../../view/team/TeamPTB.fxml"));
    		vbox.getChildren().set(1, content);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void LabelTeamScheduleClicked(MouseEvent event) {
    	try {
			effectLastCliked(lastClickedLabeled, LabelTeamSchedule);
	    	lastClickedLabeled = LabelTeamSchedule;
	    	MenuPage.setSeleted("TeamSchedule");
			Node content = FXMLLoader.load(this.getClass().getResource("../../view/team/TeamSchedule.fxml"));
			vbox.getChildren().set(1, content);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void LabelTeamSecessionClicked(MouseEvent event) {
    	effectLastCliked(lastClickedLabeled, LabelTeamSecession);
    	lastClickedLabeled = LabelTeamSecession;
    	alertConfirm("팀에서 탈퇴하시겠습니까?", "탈퇴 확인창");
    }

    @FXML
    void LabelTeamTravelListClicked(MouseEvent event) {
		try {
			effectLastCliked(lastClickedLabeled, LabelTeamTravelList);
			lastClickedLabeled = LabelTeamTravelList;
			Node content = FXMLLoader.load(this.getClass().getResource("../../view/team/TeamTravelList.fxml"));
			vbox.getChildren().set(1, content);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void initialize() {
        // userId 초기화
        userId = MenuPage.getUserId();
        // teamId 초기화
        teamId = MenuPage.getTeamId();
        
        // 자신을 호출한 부모창 받기
        vbox = Team.getPane();
        
        // 해당 유저의 해당 팀에 대한 가입 상태 확인
        // service와 연결
        String grade = "";
        try {
        	Registry reg = LocateRegistry.getRegistry(localhost, 9988);
        	service = (TeamMenuServiceInf) reg.lookup("TeamMenu");
        	server = (ChatServiceServerInf) reg.lookup("Chat");
        	String state = service.getJoinMemberState(teamId, userId);
        	String leaderId = service.getTeamLeaderId(teamId);
        	if (userId.equals(leaderId)) {
				grade = "leader";
			} else if (state.equals("Y")) {
				grade = "member";
			} else {
				grade = "N";
			}
        	
        } catch (RemoteException e) {
        	e.printStackTrace();
        }
        catch (NotBoundException e) {
        	e.printStackTrace();
        }
        
        String selectedItem = MenuPage.getSelected();
        if (selectedItem.equals("")) {
        	effectLastCliked(lastClickedLabeled, LabelTeamPhb);
        	lastClickedLabeled = LabelTeamPhb;
		}
        if (selectedItem.equals("TeamHome")) {
        	effectLastCliked(lastClickedLabeled, LabelTeamHome);
        	lastClickedLabeled = LabelTeamHome;
		} else if (selectedItem.equals("TeamMemberList")) {
			effectLastCliked(lastClickedLabeled, LabelTeamMemberProfile);
			lastClickedLabeled = LabelTeamMemberProfile;
		} else if (selectedItem.equals("TeamPhb")) {
			effectLastCliked(lastClickedLabeled, LabelTeamPhb);
			lastClickedLabeled = LabelTeamPhb;
		} else if (selectedItem.equals("TeamSchedule")) {
			effectLastCliked(lastClickedLabeled, LabelTeamSchedule);
			lastClickedLabeled = LabelTeamSchedule;
		} else if (selectedItem.equals("TravelList")) {
			effectLastCliked(lastClickedLabeled, LabelTeamTravelList);
			lastClickedLabeled = LabelTeamTravelList;
		} else if (selectedItem.equals("TeamOut")) {
			effectLastCliked(lastClickedLabeled, LabelTeamSecession);
			lastClickedLabeled = LabelTeamSecession;
		} else if (selectedItem.equals("TeamManage")) {
			effectLastCliked(lastClickedLabeled, LabelTeamManage);
			lastClickedLabeled = LabelTeamManage;
		} else if (selectedItem.equals("TeamBoard")) {
			effectLastCliked(lastClickedLabeled, LabelTeamBoard);
			lastClickedLabeled = LabelTeamBoard;
		} else if (selectedItem.equals("TeamChat")) {
			effectLastCliked(lastClickedLabeled, LabelTeamChat);
			lastClickedLabeled = LabelTeamChat;
		}
        
        
        // 고른 메뉴에 접근 및 가입 상태에 따른 접근 제어
        gradeSelect(grade);
    }
    
    /**
     * 회원의 등급(팀멤버 member, 팀리더 leader, 비 멤버 N)에 따른 클릭 버튼 비활성화
     * @param grade
     */
    private void gradeSelect(String grade) {
    	if (grade.equals("N")) {// 해당 팀 멤버가 아닌 경우
    		LabelTeamMemberProfile.setDisable(true);
    		LabelTeamChat.setDisable(true);
    		LabelTeamPhb.setDisable(true);
    		LabelTeamBoard.setDisable(true);
    		LabelTeamManage.setDisable(true);
    		LabelTeamSecession.setDisable(true);
    		LabelTeamTravelList.setDisable(true);
    	} else if (grade.equals("member")) {// 해당 팀 멤버인 경우
    		LabelTeamManage.setDisable(true);
		} else if (grade.equals("leader")) {// 해당 팀 리더인 경우
			LabelTeamSecession.setDisable(true);
		}
    }
    /**
     * 멤버 탈퇴시 이용하는 얼럿창
     * @param 내용
     * @param 제목
     */
    public void alertConfirm(String msg, String title) {
    	Alert alertConfirm = new Alert(AlertType.CONFIRMATION);
    	alertConfirm.setTitle(title);
    	alertConfirm.setContentText(msg);
    	alertConfirm.showAndWait();
    	if (alertConfirm.getResult() == ButtonType.OK) {
    		// Join테이블에서 delete시킴
    		try {
				service.deleteTeamMemberSelf(teamId, userId);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
    		gradeSelect("N");
        	LabelTeamHomeClicked(null);
    	}
    }
    
}
