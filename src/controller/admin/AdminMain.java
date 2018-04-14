package controller.admin;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import controller.main.LoginPage;
import controller.main.MenuPage;
import controller.team.Chatting;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import service.team.ChatServiceClient;
/**
 * adminMain client controller
 * @author sk.baek 
 *
 */
import service.team.ChatServiceServerInf;
public class AdminMain {
	String localhost = LoginPage.getLocalhost();
	private static Stage myStage;
	/**
	 * 현재 사용하는 controller의 stage 가져옴(로그아웃시 사용)
	 * @param input_myStage
	 */
	public static void setMyStage(Stage input_myStage) {
		myStage = input_myStage;
	}
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnUserManage;

    @FXML
    private Button btnPTBManage;

    @FXML
    private Button btnBoardManage;

    @FXML
    private Button btnSendNotice;

    @FXML
    private VBox paneContent;

    @FXML
    void btnBoardManageClick(ActionEvent event) {
    	// 아직 미정
    }

    @FXML
    void btnPTBManageClick(ActionEvent event) {
    	try {
         	AdminMenu.setParentPane(paneContent);
         	Node menu = FXMLLoader.load(this.getClass().getResource("../../view/admin/AdminMenu.fxml"));
         	Node bottom = FXMLLoader.load(this.getClass().getResource("../../view/admin/AdminPTB.fxml"));
     		
         	paneContent.getChildren().setAll(menu, bottom);
 			
 		} catch (IOException e) {
 			e.printStackTrace();
 		}
    }
    
    ChatServiceServerInf server;
    
    FXMLLoader loader;
	
	@FXML
    void btnSendNoticeClick(ActionEvent event) {
    	loader = new FXMLLoader(getClass().getResource("../../view/admin/ChatAdmin.fxml"));
    	System.out.println(MenuPage.getUserId());
    	try {
    		ChatServiceClient client = new ChatServiceClient();
    		
    		boolean is = server.makeChat("ad");
    		
    		server.joinChat("ad", client);
    		
    		Parent root = loader.load();
    		
    		Chatting controller = (Chatting)loader.getController();
    		controller.setChat(server, client,MenuPage.getUserId());
    		
    		Stage stage = new Stage();
    		stage.initModality(Modality.NONE);
    		
    		Scene scene = new Scene(root);
    		stage.setTitle("관리자 공지");
    		stage.setScene(scene);
    		stage.show();
    		
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void btnUserManageClick(ActionEvent event) {
    	 try {
         	
         	AdminMenu.setParentPane(paneContent);
         	Node menu = FXMLLoader.load(this.getClass().getResource("../../view/admin/AdminMenu.fxml"));
         	Node bottom = FXMLLoader.load(this.getClass().getResource("../../view/admin/AdminUserManage.fxml"));
     		
         	paneContent.getChildren().setAll(menu, bottom);
 			
 		} catch (IOException e) {
 			e.printStackTrace();
 		}
    }

    @FXML
    void logoHomeClick(MouseEvent event) {
    	btnUserManageClick(null);
    }

    @FXML
    void logoutClick(MouseEvent event) {
    	Scene scene;
		try {
			scene = new Scene(FXMLLoader.load(getClass().getResource("../../view/main/LoginPage.fxml")));
			LoginPage.setParentStage(myStage);
			myStage.setScene(scene);
			myStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void initialize() {
        assert btnUserManage != null : "fx:id=\"btnUserManage\" was not injected: check your FXML file 'AdminMain.fxml'.";
        assert btnPTBManage != null : "fx:id=\"btnPTBManage\" was not injected: check your FXML file 'AdminMain.fxml'.";
        assert btnBoardManage != null : "fx:id=\"btnBoardManage\" was not injected: check your FXML file 'AdminMain.fxml'.";
        assert paneContent != null : "fx:id=\"paneContent\" was not injected: check your FXML file 'AdminMain.fxml'.";
        
        Registry reg;
		
        try {
			reg = LocateRegistry.getRegistry(localhost,9988);
			server = (ChatServiceServerInf) reg.lookup("Chat");
			
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
        
        
        try {
        	
        	AdminMenu.setParentPane(paneContent);
        	Node menu = FXMLLoader.load(this.getClass().getResource("../../view/admin/AdminMenu.fxml"));
        	Node bottom = FXMLLoader.load(this.getClass().getResource("../../view/admin/AdminUserManage.fxml"));
    		
        	paneContent.getChildren().setAll(menu, bottom);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    }
}
