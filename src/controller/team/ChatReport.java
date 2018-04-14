package controller.team;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import controller.main.MenuPage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import service.team.ChatServiceServerInf;
import vo.ReportVO;

public class ChatReport {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox vContent;

    @FXML
    private TextField userIdTx;

    @FXML
    private TextArea txtArea;

    @FXML
    private Button back;

    @FXML
    private Button report;
    
    private String re_us_id;
    
    private ChatServiceServerInf server;
    
    public void setUsId(String re_us_id) {
		this.re_us_id = re_us_id;
		userIdTx.setText(re_us_id);
		System.out.println(re_us_id);
	}
    
    public void setServie(ChatServiceServerInf server) {
		this.server = server;
	}
    
    
    @FXML
    void back(ActionEvent event) {
    	Stage st = (Stage)back.getScene().getWindow();
    	st.close();
    }

    @FXML
    void userReport(ActionEvent event) {
    	
    	ReportVO vo = new ReportVO();
    	
    	vo.setRe_cs(txtArea.getText());
    	vo.setRe_us_id(re_us_id);
    	vo.setRe_tm_id(MenuPage.getTeamId());
    	
    	try {
			boolean result = server.userReport(vo);
			if(result == true) {
				alert("신고가 완료 되었습니다");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    	back(null);
    	
    	
    }
    
    
    void alert(String msg) {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	
    	alert.setTitle("확인");
    	alert.setHeaderText("확인");
    	alert.setContentText(msg);
    	
    	alert.showAndWait();
    }
    
    @FXML
    void initialize() {
        

    }
}
