package controller.my;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import controller.main.LoginPage;
import controller.main.MenuPage;
import controller.main.MenuPage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import service.my.MyListServiceInf;
import service.teamList.TeamListServiceInf;
import vo.JoinVO;
import vo.TeamListVO;
import vo.TeamStatusVO;

public class MyList {
	

//	private Stage primaryStage;
//	String localhost = LoginPage.getLocalhost();
//
//private Stage primaryStage;
//	

	// 서비스 설정
	MyListServiceInf service;
		
	// 테이블 행 수
	private int limit = 12;
		
	// 팀 
	private ObservableList<TeamStatusVO> joiningList = FXCollections.observableArrayList();
	private ObservableList<TeamStatusVO> invitedList = FXCollections.observableArrayList();
	private ObservableList<TeamStatusVO> signedUpLsit = FXCollections.observableArrayList();
	

	private Stage primaryStage;
	
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnTeamProduce;

    @FXML
    private TableView<TeamStatusVO> tabeSignedUpTeam;  // 가입 신청한 팀 목록 table

//    @FXML
//    private TableColumn<?, ?> no1Col;  // 가입 신청한 팀 목록 no

    @FXML
    private TableColumn<?, ?> teamName1Col;  // 가입 신청한 팀 목록

    @FXML
    private TableView<TeamStatusVO> tableInvitedTeam;   // 초대받은 팀 목록 table

//    @FXML
//    private TableColumn<?, ?> no2Col;  //초대받은 팀 목록 no

    @FXML
    private TableColumn<?, ?> teamName2Col;   // 초대받은 팀 몰록

    @FXML
    private TableView<TeamStatusVO> tableEnrolledTeam;  //가입한 팀 목록 table 

//    @FXML
//    private TableColumn<?, ?> no3Col;  // 가입한 팀 목록 no

    @FXML
    private TableColumn<?, ?> teamName3Col; // 가입한 팀 목록 
    
    /**
     * 가입한 팀으로 이동
     * @param event
     */
    @FXML
    void onEnrolledTeamClicked(MouseEvent event) {
    	TeamStatusVO vo = tableEnrolledTeam.getSelectionModel().getSelectedItem();
    	MenuPage.setTeamId(vo.getTm_id());
    	MenuPage.setSeleted("TeamHome");
		Node myTeamList;
		try {
			myTeamList = FXMLLoader.load(this.getClass().getResource("../../view/team/Team.fxml"));
			MenuPage.getMenuPane().getChildren().setAll(myTeamList);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 초대받은 팀으로 이동
     * @param event
     */
    @FXML
    void onInvitedTeamClicked(MouseEvent event) {
    	TeamStatusVO vo = tableInvitedTeam.getSelectionModel().getSelectedItem();
    	MenuPage.setTeamId(vo.getTm_id());
    	
    	MenuPage.setSeleted("TeamHome");
		Node myTeamList;
		try {
			myTeamList = FXMLLoader.load(this.getClass().getResource("../../view/team/Team.fxml"));
			MenuPage.getMenuPane().getChildren().setAll(myTeamList);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 가입 신청한 팀으로 이동
     * @param event
     */
    @FXML
    void onSignedUpTeamClicked(MouseEvent event) {
    	TeamStatusVO vo = tabeSignedUpTeam.getSelectionModel().getSelectedItem();
    	MenuPage.setTeamId(vo.getTm_id());
    	MenuPage.setSeleted("TeamHome");
		Node myTeamList;
		try {
			myTeamList = FXMLLoader.load(this.getClass().getResource("../../view/team/Team.fxml"));
			MenuPage.getMenuPane().getChildren().setAll(myTeamList);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
    
    /**
     *  팀 생성으로 이동
     * @param event
     */
    @FXML
    void onbtnTeamProduce(ActionEvent event) {
    	try {
			Parent myTeamProduce = FXMLLoader.load(getClass().getResource("../../view/my/MyTeamProduce.fxml"));
			Stage myTeamStage = new Stage(StageStyle.UTILITY);
			myTeamStage.initModality(Modality.WINDOW_MODAL);
			myTeamStage.initOwner(primaryStage);
			
			Scene scene = new Scene(myTeamProduce);
			myTeamStage.setTitle("MyTeamProduce");
			myTeamStage.setScene(scene);
			myTeamStage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    MyListServiceInf myListService;
    
    @FXML
    void initialize() {
        
        //service 객체 생성
    	Registry reg;
        try {
			 reg  = LocateRegistry.getRegistry("localhost", 9988);
			service = (MyListServiceInf)reg.lookup("MyList");
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
        
        //컬럼 설정
//    	no1Col.setCellValueFactory(new PropertyValueFactory<>("jo_id"));
    	teamName1Col.setCellValueFactory(new PropertyValueFactory<>("tm_nm"));
    	
//    	no2Col.setCellValueFactory(new PropertyValueFactory<>("jo_id"));
    	teamName2Col.setCellValueFactory(new PropertyValueFactory<>("tm_nm"));
    	
//    	no3Col.setCellValueFactory(new PropertyValueFactory<>("jo_id"));
    	teamName3Col.setCellValueFactory(new PropertyValueFactory<>("tm_nm"));
        
    	 reflesh();
    }
    
    private void reflesh() {
    	System.out.println(service);
    	try {
			List<TeamStatusVO> joiningvoList = service.tableEnrolledTeam(MenuPage.getUserId());
			List<TeamStatusVO> invitedvoList = service.tableInvitedTeam(MenuPage.getUserId());
	    	List<TeamStatusVO> signedUpvoLsit = service.tabeSignedUpTeam(MenuPage.getUserId());
			
			
			invitedList.addAll(invitedvoList);
	    	joiningList.addAll(joiningvoList);
	    	signedUpLsit.addAll(signedUpvoLsit);
	    	
	    	tableEnrolledTeam.setItems(joiningList);
	    	tabeSignedUpTeam.setItems(signedUpLsit);
	    	tableInvitedTeam.setItems(invitedList);
	    	
	    	
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	

		
    }
    

	
    
}
