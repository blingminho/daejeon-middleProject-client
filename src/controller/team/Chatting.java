package controller.team;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.text.html.HTMLDocument.HTMLReader.FormAction;

import controller.main.LoginPage;
import controller.main.MenuPage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import service.team.ChatServiceClient;
import service.team.ChatServiceClientInf;
import service.team.ChatServiceServerInf;
import vo.ChattingVO;

public class Chatting {
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane parent;

    @FXML
    private Label chatName;

    @FXML
    private Button memList;

    @FXML
    private TextArea chatView;

    @FXML
    private TextField chatFild;

    @FXML
    private Button setFile;

    @FXML
    private Button setChat;
    
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
		
		
		this.client.setTextArea(chatView);
		
		
		
		chat();
		
	}


	@FXML
    void InsertChat(ActionEvent event) {
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

    @FXML
    void enterEvent(KeyEvent event) {
    	if(event.getCode() ==KeyCode.ENTER) {
    		InsertChat(null);
    	}
    }

    @FXML
    void fileInsert(ActionEvent event) {

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
    void getMemList(ActionEvent event) {
    	try {
			Node memPage = FXMLLoader.load(this.getClass().getResource("../../view/team/ChatMem.fxml"));
			
			
			parent.getChildren().setAll(memPage);
    	} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    void chat() {
    	SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd hh:mm");
    	try {
			ArrayList<ChattingVO> list = 
					(ArrayList<ChattingVO>) server.getTeamChat(MenuPage.getTeamId());
//			chatView.appendText("");
			for(ChattingVO chat : list) {
				chatView.appendText(chat.getCt_us_id()+"\n"
						+chat.getCt_content()+"  "+chat.getCt_dt()+"\n");
				
			}
			//chatView.setScrollTop(Double.MAX_VALUE);
			chatView.selectPositionCaret(chatView.getLength());
	    	chatView.deselect(); //removes the highlighting
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    	
    }

    
    
    @FXML
    void initialize() {
        System.out.println(MenuPage.getTeamId());
//        chat();
    }
}
