package controller.my;

import java.io.IOException;
import java.net.URL;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.chrono.Chronology;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import controller.main.LoginPage;
import controller.main.MenuPage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.AccessibleRole;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.StringConverter;
import service.admin.UserManageServiceInf;
import service.login.JoinServiceInf;
import service.my.MyHomeServiceInf;
import vo.UserVO;


public class MyHome {
	String localhost = LoginPage.getLocalhost();
	private Stage primaryStage;
	private MyHomeServiceInf service;
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button btnPrivacyYes;   // 개인 정보 변경 button
    @FXML
    private Button btnMemberSecession;  // 회원 탈퇴 button
    @FXML
    private TextField txtFieldE_mail;   // E-mail
    @FXML
    private TextField txtFieldHpone;   // 핸드폰 번호
    @FXML
    private TextField txtFieldPassword;  // 비밀번호
    @FXML
    private TextArea txtAreaAboutMe;   //  자기소개
    @FXML
    private DatePicker datePicker;  // 생년월일 
    
    /**
     *  회원 탈퇴 이동
     * @param event
     */
    @FXML
    void btnMemberSecessionMove(ActionEvent event) {
    	try {
    		Parent btnTeamMemberSecession = FXMLLoader.load(this.getClass().getResource("../../view/my/MyTeamSecession.fxml"));
    		Stage btnStage = new Stage(StageStyle.UTILITY);
    		btnStage.initModality(Modality.WINDOW_MODAL);
    		btnStage.initOwner(primaryStage);
    		
    		Scene scene = new Scene(btnTeamMemberSecession);
    		btnStage.setTitle("MyTeamSecession");
    		btnStage.setScene(scene);
    		btnStage.show();
    	
		} catch (IOException e) {
			e.printStackTrace();
		}	
    }
    
    /**
     *  개인 정보 변경
     * @param event
     */
    @FXML
    void btnPrivacyYesClicked(ActionEvent event) {
    	UserVO userInfo = new UserVO();
    	userInfo.setUs_id(MenuPage.getUserId());
    	userInfo.setUs_pf(txtAreaAboutMe.getText());
    	userInfo.setUs_mail(txtFieldE_mail.getText());
    	userInfo.setUs_ph(txtFieldHpone.getText());
    	userInfo.setUs_pw(txtFieldPassword.getText());
    	LocalDate date = datePicker.getValue();
    	System.out.println(date.toString());
    	userInfo.setUs_bir(date.toString());
    	try {
			int result = service.updateUser(userInfo);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
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
	    
	    try {
			UserVO userInfoList =(UserVO) service.getUserInfo(MenuPage.getUserId());
			txtAreaAboutMe.setText(userInfoList.getUs_pf());
			txtFieldE_mail.setText(userInfoList.getUs_mail());
			txtFieldHpone.setText(userInfoList.getUs_ph());
			txtFieldPassword.setText(userInfoList.getUs_pw());
			String bir = userInfoList.getUs_bir();
			int year = Integer.valueOf(bir.substring(0, 4));
			int mon = Integer.valueOf(bir.substring(5, 7));
			int day = Integer.valueOf(bir.substring(8, 10));
			LocalDate ldate = LocalDate.of(year, mon, day);
			datePicker.setValue(ldate);
		
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}
}

