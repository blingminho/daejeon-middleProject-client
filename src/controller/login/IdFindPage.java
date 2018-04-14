package controller.login;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
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
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import service.login.IdFindServiceInf;
import service.login.JoinServiceInf;
import vo.UserVO;

public class IdFindPage {
	String localhost = LoginPage.getLocalhost();
	private Stage primaryStae;
	
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStae = primaryStage;
	}
	
	private IdFindServiceInf service;
	

	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtBrth;

    @FXML
    private TextField txtEmail;

    @FXML
    private Button buttonOk;

    @FXML
    void btnOk(ActionEvent event) {
//    	UserVO uvo = new UserVO();
//    	uvo.setUs_nm(txtName.getText());
//    	uvo.setUs_bir(txtBrth.getText());
//    	uvo.setUs_mail(txtEmail.getText());
    	
    	try {
//    		int cnt = service.getUserInfo(uvo);
//			if(cnt == 0) {
//				alert("검색된 회원이 없습니다.");
//				return;
//			} else {
//				Alert info = new Alert(AlertType.INFORMATION);
//				
//				info.setTitle("E-MAIL 인증");
//				info.setHeaderText("E-MAIL을 확인하세요.");
//				info.showAndWait();
//				
//				Stage ok = (Stage) buttonOk.getScene().getWindow();
//		    	ok.close();
//			}
    		List<UserVO> userList = service.getUserInfo(txtName.getText());
    		if(userList == null || userList.size() == 0) {
    			alert("검색된 회원이 없습니다.");
    		} else {
    			for(UserVO uvo : userList) {
    				if(!uvo.getUs_bir().equals(txtBrth.getText())){
    					alert("회원의 생일 정보가 올바르지 않습니다.");
    				}
    				if(!uvo.getUs_mail().equals(txtEmail.getText())) {
    					alert("회원의 이메일 정보가 올바르지 않습니다.");
    				}
    				if(uvo.getUs_bir().equals(txtBrth.getText()) 
    						&& uvo.getUs_mail().equals(txtEmail.getText())
    						&& uvo.getUs_nm().equals(txtName.getText())) {
    					
    					String host	= "smtp.naver.com"; // 보내는 쪽의 메일 설정부분
    			    	final String user = "email_ID"; // 인증메일을 보내는 아이디
    			    	final String password = "email_PW"; // 인증메일을 보내는 아이디 비밀번호
    			    	
    			    	String to = txtEmail.getText();
    			    	
    			    	Properties props = new Properties();
    			    	props.put("mail.smtp.host", host);
    			    	props.put("mail.smtp.auth", "true");
    			    	
    			    	Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
    			    		protected PasswordAuthentication getPasswordAuthentication() {
    			    			return new PasswordAuthentication(user, password);
    			    		}
    			    	});
    			    	
    			    	try {
    						MimeMessage message = new MimeMessage(session);
    						message.setFrom(new InternetAddress(user));
    						message.setSubject("Travel Maker 아이디 찾기");
    						
    						message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
    						
    						Multipart mp = new javax.mail.internet.MimeMultipart();
    						MimeBodyPart mbp1 = new MimeBodyPart();
    						
    						mbp1.setText("Travel Maker 회원 ID : "+uvo.getUs_id());
    						mp.addBodyPart(mbp1);
    						message.setContent(mp);
    						Transport.send(message);
    						
    					} catch (MessagingException e) {
    						e.printStackTrace();
    					}
    					
    					Alert info = new Alert(AlertType.INFORMATION);
    					
    					info.setTitle("E-MAIL 인증");
    					info.setHeaderText("E-MAIL을 확인하세요.");
    					info.showAndWait();
    					
    					Stage ok = (Stage) buttonOk.getScene().getWindow();
    			    	ok.close();
    				}
    			}    			
    		}
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void initialize() {
        assert txtName != null : "fx:id=\"txtName\" was not injected: check your FXML file 'IdFind.fxml'.";
        assert txtBrth != null : "fx:id=\"txtBrth\" was not injected: check your FXML file 'IdFind.fxml'.";
        assert txtEmail != null : "fx:id=\"txtEmail\" was not injected: check your FXML file 'IdFind.fxml'.";
        assert buttonOk != null : "fx:id=\"buttonOk\" was not injected: check your FXML file 'IdFind.fxml'.";

        try {
        	Registry reg = LocateRegistry.getRegistry(localhost, 9988);
        	service = (IdFindServiceInf) reg.lookup("IdFind");
        } catch (RemoteException e) {
        	e.printStackTrace();
        } catch (NotBoundException e) {

        }
        Platform.runLater(new Runnable() {
        	@Override
        	public void run() {
        		txtName.requestFocus();
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
