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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
/**
 * AdminMain의 자녀controller 가로 메뉴바
 * @author sk Baek
 *
 */
import service.main.MainLoginServiceInf;
import service.team.ChatServiceClient;
import service.team.ChatServiceServerInf;

public class AdminMenu {
	String localhost = LoginPage.getLocalhost();
	// login한 userId (부모 controller의 getUserId() 사용)
		private static String userId;
		
		// 부모창
		private static VBox vbox;
		
		// 자신을 호출한 부모창 받기
		public static void setParentPane(VBox parentVboxPane) {
			vbox = parentVboxPane;
		}
		
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
    private Label LabelUserManage;

    @FXML
    private Label LabelPTBManage;

//    @FXML
//    private Label LabelBoardManage;

    @FXML
    private Label LabelSendNotice;

//    @FXML
//    void LabelBoardManageClick(MouseEvent event) {
//    	// 아직 미정
//    }

    @FXML
    void LabelPTBManageClick(MouseEvent event) {
    	try {
			effectLastCliked(lastClickedLabeled, LabelPTBManage);
	    	lastClickedLabeled = LabelPTBManage;
			Node content = FXMLLoader.load(this.getClass().getResource("../../view/admin/AdminPTB.fxml"));
			vbox.getChildren().set(1, content);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    ChatServiceServerInf server;
    
    FXMLLoader loader;
    
    @FXML
    void LabelSendNoticeClick(MouseEvent event) {
    	loader = new FXMLLoader(getClass().getResource("../../view/admin/ChattingAdmin.fxml"));
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/admin/ChattingAdmin.fxml"));
    	System.out.println(MenuPage.getUserId());
    	try {
    		ChatServiceClient client = new ChatServiceClient();
    		
    		boolean is = server.makeChat(MenuPage.getTeamId());
    		
    		server.joinChat(MenuPage.getTeamId(), client);
    		
    		Parent root = loader.load();
    		
    		ChatAdmin controller = (ChatAdmin)loader.getController();
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
    void LabelUserManageClick(MouseEvent event) {
    	try {
			effectLastCliked(lastClickedLabeled, LabelUserManage);
	    	lastClickedLabeled = LabelUserManage;
			Node content = FXMLLoader.load(this.getClass().getResource("../../view/admin/AdminUserManage.fxml"));
			vbox.getChildren().set(1, content);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void initialize() {
        assert LabelUserManage != null : "fx:id=\"LabelUserManage\" was not injected: check your FXML file 'AdminMenu.fxml'.";
        assert LabelPTBManage != null : "fx:id=\"LabelPTBManage\" was not injected: check your FXML file 'AdminMenu.fxml'.";
//        assert LabelBoardManage != null : "fx:id=\"LabelBoardManage\" was not injected: check your FXML file 'AdminMenu.fxml'.";
        assert LabelSendNotice != null : "fx:id=\"LabelSendNotice\" was not injected: check your FXML file 'AdminMenu.fxml'.";
        
        Registry reg;
		try {
			reg = LocateRegistry.getRegistry(localhost,9988);
			server = (ChatServiceServerInf) reg.lookup("Chat");
			
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
    	System.out.println(server);
    }

}
