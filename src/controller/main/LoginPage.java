package controller.main;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.ResourceBundle;

import controller.admin.AdminMain;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import service.main.MainLoginServiceInf;
import vo.JoinVO;
import vo.UserVO;

public class LoginPage {
//	public static String localhost = "192.168.0.64";		//백선경 ip주소
	public static String localhost = "localhost";
	public static String getLocalhost() {
		return localhost;
	}
//	public static String localhost = "localhost";
	
	
	
	private static Stage primaryStae;
	
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStae = primaryStage;
	}
	
	public static void setParentStage(Stage parentStage) {
		primaryStae = parentStage;
	}
	
	private MainLoginServiceInf service;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtId;

    @FXML
    private Button btnLogin;

    @FXML
    private PasswordField txtPwd;

    UserVO uvo = new UserVO();
    @FXML
    void IdFind(MouseEvent event) {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/login/IdFind.fxml"));
			Parent root = loader.load();

			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(primaryStae);
			
			Scene scene = new Scene(root);
			stage.setTitle("아이디 찾기");
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }

    @FXML
    void Join(MouseEvent event) {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/login/Join.fxml"));
			Parent root = loader.load();

			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(primaryStae);
			
			Scene scene = new Scene(root);
			stage.setTitle("회원가입");
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void Login(ActionEvent event) {
    	Stage mainPage = new Stage();
		try {
//			if(txtId.getText().isEmpty()) {
//			alert("ID를 입력해 주세요!!!!");
//			return;
//		}
//		
//		int id = service.getLoginId(txtId.getText());
//		int pwd = service.getLoginPwd(txtPwd.getText());
//		
//		if(id == 0) {
//			alert("존재하지 않는 ID입니다.");
//			txtId.clear();
//			txtPwd.clear();
//			return;
//		}
//		
//		if(id>0 && pwd == 0) {
//			alert("비밀번호가 틀립니다.");
//			txtPwd.clear();
//			return;
//		}
			List<UserVO> userList = service.getLoginUser(txtId.getText());
			List<JoinVO> joinList = service.getJoinInfo(txtId.getText());

			if(userList == null || userList.size() == 0) {
				alert("아이디정보가 일치하지 않습니다.");
			} else {
				for (UserVO userVO : userList) {
					if(userVO.getUs_id().equals("ad")) {
						if(userVO.getUs_id().equals(txtId.getText()) && userVO.getUs_pw().equals(txtPwd.getText())) {
							Alert info = new Alert(AlertType.INFORMATION);
	    					
	    					info.setTitle("관리자");
	    					info.setHeaderText("관리자님 환영합니다.");
	    					info.showAndWait();
	    					AdminMain.setMyStage(primaryStae);
	    					Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../../view/admin/AdminMain.fxml")));
	    					MenuPage.setUserId(txtId.getText());
	    					primaryStae.setScene(scene);
	    					primaryStae.show();
							
//							primaryStae.close();
						}
					} else {
						if(!userVO.getUs_pw().equals(txtPwd.getText())) {
							alert("비밀번호가 틀립니다.");
						} else if(userVO.getUs_id().equals(txtId.getText())&&userVO.getUs_rt_cn() == 3){
							alert("경고 3회 누적으로 로그인이 불가합니다.");
							Platform.runLater(new Runnable() {
					            @Override
					            public void run() {
					            	txtId.requestFocus();
					            }
					        });
							txtId.clear();
							txtPwd.clear();
						} else if(userVO.getUs_id().equals(txtId.getText())&&userVO.getUs_rt_cn() >= 1 && userVO.getUs_rt_cn() < 4){
							
							alert("경고 횟수 : "+userVO.getUs_rt_cn()+"\n"
									+ "경고 횟수가 3이 되면 강퇴처리됩니다.");
//							MenuPage.setUserId(txtId.getText());
							
							// 메뉴에 아이디와 stage 넘김
							MenuPage.setUserId(txtId.getText());
							MenuPage.setMyStage(primaryStae);
							
							for(JoinVO jvo : joinList) {
								// 메뉴에 팀id를 넘김
								MenuPage.setTeamId(jvo.getJo_tm_id());
							}
							
							Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../../view/main/MenuPage.fxml")));
							primaryStae.setTitle("메인 홈페이지");
							primaryStae.setScene(scene);
							primaryStae.show();
							
//							primaryStae.close();
						} else if(userVO.getUs_scs().equals("Y")) {
							alert("해당 회원님은 탈퇴처리가 되어있습니다.");
						} else if(userVO.getUs_id().equals(txtId.getText()) && userVO.getUs_pw().equals(txtPwd.getText())
								&& userVO.getUs_rt_cn() != 2 && !userVO.getUs_scs().equals("Y")){
							MenuPage.setUserId(txtId.getText());
							MenuPage.setMyStage(primaryStae);
							
							for(JoinVO jvo : joinList) {
								// 메뉴에 팀id를 넘김
								MenuPage.setTeamId(jvo.getJo_tm_id());
							}
							
							Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../../view/main/MenuPage.fxml")));
							primaryStae.setTitle("메인 홈페이지");
							primaryStae.setScene(scene);
							primaryStae.show();
						}	
					}
				}			

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void PwdFind(MouseEvent event) {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/login/PwdFind.fxml"));			
    		Parent root = loader.load();

			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(primaryStae);
			
			Scene scene = new Scene(root);
			stage.setTitle("비밀번호 찾기");
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void onEnterPressed(KeyEvent event) {
    	if(event.getCode() ==KeyCode.ENTER) {
    		Login(null);
    	}
    }
    
    @FXML
    void initialize() {
        assert txtId != null : "fx:id=\"txtId\" was not injected: check your FXML file 'LoginPage.fxml'.";
        assert btnLogin != null : "fx:id=\"btnLogin\" was not injected: check your FXML file 'LoginPage.fxml'.";
        assert txtPwd != null : "fx:id=\"txtPwd\" was not injected: check your FXML file 'LoginPage.fxml'.";

        try {
        	Registry reg = LocateRegistry.getRegistry(localhost, 9988);
        	service = (MainLoginServiceInf) reg.lookup("login");
        } catch (RemoteException e) {
        	e.printStackTrace();
        } catch (NotBoundException e) {
        	e.printStackTrace();
        }
        
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
            	txtId.requestFocus();
            }
        });
    }
    
    public void alert(String msg) {
    	Alert alertWarning = new Alert(AlertType.WARNING);
    	alertWarning.setTitle("WARNING");
    	alertWarning.setContentText(msg);
    	alertWarning.showAndWait();
    }
}
