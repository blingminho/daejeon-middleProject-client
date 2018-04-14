package controller.login;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import service.login.IdFindServiceInf;
import service.login.PwdFindServiceInf;
import vo.UserVO;

public class PwdFindPage {
	String localhost = LoginPage.getLocalhost();
	private Stage primaryStae;
	
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStae = primaryStage;
	}
	
	private PwdFindServiceInf service;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView imgHome;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtBrth;

    @FXML
    private Button buttonOk;

    @FXML
    void btnOk(ActionEvent event) {
    	try {
    		List<UserVO> userList = service.getUserInfo(txtId.getText());
    		if(userList == null || userList.size() == 0) {
    			alert("검색된 회원이 없습니다.");
    		} else {
    			for(UserVO uvo : userList) {
    				if(!uvo.getUs_nm().equals(txtName.getText())){
    					alert("회원의 이름이 올바르지 않습니다.");
    				}
    				if(!uvo.getUs_bir().equals(txtBrth.getText())) {
    					alert("회원의 생일 정보가 올바르지 않습니다.");
    				}
    				if(uvo.getUs_nm().equals(txtName.getText()) 
    						&& uvo.getUs_bir().equals(txtBrth.getText())) {
    					
    					String host	= "smtp.naver.com"; // 보내는 쪽의 메일 설정부분
    			    	final String user = "email_ID"; // 인증메일을 보내는 아이디
    			    	final String password = "email_PW"; // 인증메일을 보내는 아이디 비밀번호
    			    	
    			    	String to = uvo.getUs_mail();
    			    	
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
    						message.setSubject("Travel Maker 비밀번호 찾기");
    						
    						message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
    						
    						Multipart mp = new javax.mail.internet.MimeMultipart();
    						MimeBodyPart mbp1 = new MimeBodyPart();
    						
    						String num = "";
    						
    						for(int i=1; i<=6; i++) {
    							num+=((int)(Math.random()*6)+1)+"";
    						}
    						
    						mbp1.setText("Travel Maker 임시비밀번호 : "+num);
    						mp.addBodyPart(mbp1);
    						message.setContent(mp);
    						Transport.send(message);
    						
    						Alert info = new Alert(AlertType.INFORMATION);
        					
        					info.setTitle("E-MAIL 인증");
        					info.setHeaderText("E-MAIL을 확인하세요.");
        					info.setContentText("임시비밀번호로 로그인해주세요.");
        					info.showAndWait();
    						
        					// 임시비밀번호를 db에 저장
    						uvo.setUs_pw(num);
    						service.updatePwd(uvo);
    						num="";
    					} catch (MessagingException e) {
    						e.printStackTrace();
    					}
    			    	
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
        assert imgHome != null : "fx:id=\"imgHome\" was not injected: check your FXML file 'PwdFind.fxml'.";
        assert txtId != null : "fx:id=\"txtId\" was not injected: check your FXML file 'PwdFind.fxml'.";
        assert txtName != null : "fx:id=\"txtName\" was not injected: check your FXML file 'PwdFind.fxml'.";
        assert txtBrth != null : "fx:id=\"txtBrth\" was not injected: check your FXML file 'PwdFind.fxml'.";
        assert buttonOk != null : "fx:id=\"buttonOk\" was not injected: check your FXML file 'PwdFind.fxml'.";

        try {
        	Registry reg = LocateRegistry.getRegistry(localhost, 9988);
        	service = (PwdFindServiceInf) reg.lookup("PwdFind");
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
