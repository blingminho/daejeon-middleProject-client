package controller.my;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.Scene;
import javafx.scene.control.Button;import javafx.scene.layout.GridPane;

import javafx.stage.Stage;
import service.my.MyHomeServiceInf;

public class MyTeamSecession {
	String localhost = LoginPage.getLocalhost();
	MyHomeServiceInf service;
	// 로그인 Stage
	Stage myStage = MenuPage.getMyStage();
	
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;
    
    @FXML
    private GridPane gridPane;


    @FXML
    private Button btnSecessionJoinYes;   // 탈퇴 button

    @FXML
    private Button btnCancelJoinYes;   // 취소 button
    
    /**
     *  취소
     * @param event
     */
    @FXML
    void onbtnCancelJoinYesClicked(ActionEvent event) {
 
     	Stage stage = (Stage) btnCancelJoinYes.getScene().getWindow(); 
    	stage.close();
    }
    
    /**
     *  탈퇴
     * @param event
     */
    @FXML
    void onbtnSecessionJoinYesClicked(ActionEvent event) {
    	boolean flag = false;
    	try {
    		flag = service.deleteUser(MenuPage.getUserId());
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
    	
    	if (flag == false) {
			return;
		}
    	Scene scene;
    	try {
    		myStage.close();
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
    	try {
        	Registry reg = LocateRegistry.getRegistry(localhost, 9988);
        	service = (MyHomeServiceInf) reg.lookup("MyHome");
        } catch (RemoteException e) {
        	e.printStackTrace();
        } catch (NotBoundException e) {
        	e.printStackTrace();
        }
    }
}
