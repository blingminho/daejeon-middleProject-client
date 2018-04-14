package controller.login;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import service.login.JoinServiceInf;
import vo.JoinVO;
import vo.UserVO;

public class JoinPage {
	String localhost = LoginPage.getLocalhost();
	private Stage primaryStage;
	
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtEmailCheck;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtTel;

    @FXML
    private DatePicker txtBrth;

    @FXML
    private TextField txtName;

    @FXML
    private Button buttonOk;

    @FXML
    private Button buttonCancel;

    @FXML
    private PasswordField txtPwd;

    @FXML
    private RadioButton radioMen;

    @FXML
    private ToggleGroup gender;

    @FXML
    private RadioButton radioFemale;
    
    private JoinServiceInf service;

    UserVO uvo = new UserVO();
    JoinVO jvo = new JoinVO();
    String num = "";
    String result=""; //인증번호 축적 방지

    @FXML
    void btnCancel(ActionEvent event) {
    	Stage ok = (Stage) buttonOk.getScene().getWindow();
    	ok.close();
    }

    @FXML
    void btnEmailAuthentication(ActionEvent event) {
    	String host	= "smtp.naver.com"; // 보내는 쪽의 메일 설정부분
    	final String user = "email_ID"; // 인증메일을 보내는 아이디
    	final String password = "email_PW"; // 인증메일을 보내는 아이디 비밀번호
    	
    	String to = txtEmail.getText();
    	if (to.equals("") || to == null) {
			alert("이메일을 입력해주세요");
			return;
		}
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
			message.setSubject("Travel Maker 회원가입 인증번호");
			
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
			
			Multipart mp = new javax.mail.internet.MimeMultipart();
			MimeBodyPart mbp1 = new MimeBodyPart();
			
			for(int i=1; i<=6; i++) {
				num+=((int)(Math.random()*6)+1)+"";
			}
			
			mbp1.setText("인증번호 : "+num);
			mp.addBodyPart(mbp1);
			message.setContent(mp);
			Transport.send(message);
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
    	
    	result = num;
    	num="";
    	Alert info = new Alert(AlertType.INFORMATION);
		
		info.setTitle("이메일 인증");
		info.setHeaderText("해당 이메일로 인증번호가 전송되었습니다.");
		info.showAndWait();
    }

    @FXML
    void btnEmailAuthenticationChk(ActionEvent event) {
    	if(txtEmailCheck.getText().equals(result)) {
    		Alert info = new Alert(AlertType.INFORMATION);
    		
    		info.setTitle("이메일 인증");
    		info.setHeaderText("인증 성공!!!");
    		info.showAndWait();
    		buttonOk.setDisable(false);
    	} else {
    		alert("인증 실패!!!+n"
    				+ "다시 인증번호를 받아주세요.");
    		txtEmailCheck.clear();
    		result="";
    	}
    }

    @FXML
    void btnIdOverlap(ActionEvent event) {
    	try {
    		if(txtId.getText().isEmpty()) {
        		alert("사용하실 ID를 입력해주세요.");
        		return;
        	}
    		
			int idChk = service.getSearchId(txtId.getText());
			
			if(idChk != 0 ) {
				alert("이미 존재하는 ID입니다.");
				txtId.clear();
				return;
			}
			
			if(idChk == 0) {
				Alert alertConfirm = new Alert(AlertType.CONFIRMATION);
				alertConfirm.setTitle("중복확인");
				alertConfirm.setHeaderText(txtId.getText()+"은 사용 가능한 ID입니다.");
				alertConfirm.setContentText("사용하시겠습니까?");
				
				ButtonType result = alertConfirm.showAndWait().get();
				
				if(result == ButtonType.OK) {
					uvo.setUs_id(txtId.getText());
				} else if(result == ButtonType.CANCEL) {
					txtId.clear();
					return;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void btnOk(ActionEvent event) {
    	
    	uvo.setUs_id(txtId.getText());
    	uvo.setUs_pw(txtPwd.getText());
    	uvo.setUs_nm(txtName.getText());
    	
    	String sung = "";
    	
    	if(gender.getSelectedToggle() != null) {
    		sung = gender.getSelectedToggle().getUserData().toString();
    		uvo.setUs_gn(sung);
    	} else {
    		alert("성별을 선택하세요!!");
    		return;
    	}
    	if (txtBrth.getValue() == null) {
			alert("생년월일를 입력하세요!!");
			return;
		}
    	
    	String birth = txtBrth.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE);
    	uvo.setUs_bir(birth);
    	uvo.setUs_ph(txtTel.getText());
    	uvo.setUs_mail(txtEmail.getText());
    	uvo.setUs_rt_cn(0);
    	uvo.setUs_scs("N");
    	
    	jvo.setJo_us_id(txtId.getText());
    	jvo.setJo_tm_id("1");
    	jvo.setJo_ct_id(" ");
    	jvo.setJo_stt("Y");
    	
    	
    	try {
			int result = service.InsertJoinInfo(uvo);
			service.setJoinTable(jvo);
			if(result > 0) {
				Alert info = new Alert(AlertType.INFORMATION);
				
				info.setTitle("회원가입");
				info.setHeaderText("회원가입이 되었습니다.\n"
						+ "자동적으로 유저팀에 가입됩니다.");
				info.showAndWait();
				
				Stage ok = (Stage) buttonOk.getScene().getWindow();
		    	ok.close();
			}else {
				alert("회원가입 실패");
				return;
			}
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void initialize() {
        assert txtId != null : "fx:id=\"txtId\" was not injected: check your FXML file 'Join.fxml'.";
        assert txtEmailCheck != null : "fx:id=\"txtEmailCheck\" was not injected: check your FXML file 'Join.fxml'.";
        assert txtEmail != null : "fx:id=\"txtEmail\" was not injected: check your FXML file 'Join.fxml'.";
        assert txtTel != null : "fx:id=\"txtTel\" was not injected: check your FXML file 'Join.fxml'.";
        assert txtBrth != null : "fx:id=\"txtBrth\" was not injected: check your FXML file 'Join.fxml'.";
        assert txtName != null : "fx:id=\"txtName\" was not injected: check your FXML file 'Join.fxml'.";
        assert buttonOk != null : "fx:id=\"buttonOk\" was not injected: check your FXML file 'Join.fxml'.";
        assert buttonCancel != null : "fx:id=\"buttonCancel\" was not injected: check your FXML file 'Join.fxml'.";
        assert txtPwd != null : "fx:id=\"txtPwd\" was not injected: check your FXML file 'Join.fxml'.";
        assert radioMen != null : "fx:id=\"radioMen\" was not injected: check your FXML file 'Join.fxml'.";
        assert gender != null : "fx:id=\"gender\" was not injected: check your FXML file 'Join.fxml'.";
        assert radioFemale != null : "fx:id=\"radioFemale\" was not injected: check your FXML file 'Join.fxml'.";

        try {
        	Registry reg = LocateRegistry.getRegistry(localhost, 9988);
        	service = (JoinServiceInf) reg.lookup("Join");
        } catch (RemoteException e) {
        	e.printStackTrace();
        } catch (NotBoundException e) {
        	e.printStackTrace();
        }
        
        radioMen.setUserData("남자");
        radioFemale.setUserData("여자");
        
        Platform.runLater(new Runnable() {
        	@Override
        	public void run() {
        		txtId.requestFocus();
        	}
        });
        
        buttonOk.setDisable(true);

    }
    
    public void alert(String msg) {
    	Alert alertWarning = new Alert(AlertType.WARNING);
    	alertWarning.setTitle("WARNING");
    	alertWarning.setContentText(msg);
    	alertWarning.showAndWait();
    }
}
