package controller.admin;

import java.net.URL;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import controller.main.LoginPage;
import controller.main.MenuPage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import service.team.ChatServiceClient;
import service.team.ChatServiceServerInf;
import vo.ChattingVO;

public class AdminSandNotice {
	String localhost = LoginPage.getLocalhost();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox adminPane;

    @FXML
    private TextArea noticeText;
    
    @FXML
    private TextField chatFild;

    @FXML
    private Button btn;
    
    private ChatServiceServerInf server;
    
    private ChattingVO vo;
    
    private ChatServiceClient client;
    
    private String ct_tm_id;
    
    private SimpleDateFormat format;
    
    private Date date;
    
    public void setChat(ChatServiceServerInf server, ChatServiceClient client, String ct_tm_id) {
		this.ct_tm_id = ct_tm_id;
		this.client =client;
		this.server = server;
		
		
		this.client.setTextArea(noticeText);
		
		
		
		chat();
		
	}
    void chat() {
    	SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd hh:mm");
    	try {
			ArrayList<ChattingVO> list = 
					(ArrayList<ChattingVO>) server.getTeamChat(MenuPage.getTeamId());
//			chatView.appendText("");
			for(ChattingVO chat : list) {
				noticeText.appendText(chat.getCt_us_id()+"\n"
						+chat.getCt_content()+"  "+chat.getCt_dt()+"\n");
				
			}
			//chatView.setScrollTop(Double.MAX_VALUE);
			noticeText.selectPositionCaret(noticeText.getLength());
			noticeText.deselect(); //removes the highlighting
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    	
    }
    @FXML
    void SandNoticeClick(ActionEvent event) {
    	System.out.println("InsertChat==>"+MenuPage.getUserId());
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
    	date = new Date();
    	ChattingVO vo = new ChattingVO();
    			
    		vo.setCt_us_id(MenuPage.getUserId());
    		vo.setCt_tm_id(MenuPage.getTeamId());
    		vo.setCt_content(chatFild.getText());
    		
    		if (server != null) {
    			String msg = vo.getCt_us_id() + "\n" + chatFild.getText() + "  " + format.format(date);
    			
    			try {
    				server.insertChat(vo);
    				server.setMessage(MenuPage.getTeamId(), msg);

    			} catch (RemoteException e) {
    				e.printStackTrace();
    			}
    			chatFild.clear();
    			excel();
    			
    		}
    	System.out.println(vo.getCt_tm_id());
    }
    
    
    void excel() {
    	try {
			ArrayList<ChattingVO> list = (ArrayList<ChattingVO>) server.getTeamChat(MenuPage.getTeamId());
			
			server.excel(MenuPage.getTeamId(), list);
    	} catch (RemoteException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void initialize() {
        System.out.println(MenuPage.getTeamId());

    }
}
