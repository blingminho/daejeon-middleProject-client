package controller.team;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.ResourceBundle;

import controller.main.LoginPage;
import controller.main.MenuPage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import service.team.ChatServiceClient;
import service.team.ChatServiceServerInf;

public class ChatMem {
	String localhost = LoginPage.getLocalhost();
    

	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox vContent;

    @FXML
    private ListView<String> mem_list;

    @FXML
    private Button report;

    @FXML
    private Button back;
    
    private ChatServiceServerInf server;
    
    private Stage primaryStage;
    
    public void setPrimaryStage(Stage primaryStage) {
    	this.primaryStage = primaryStage;
    }
    
    
    private void memList() {
    	ArrayList<String> list= null;
    	try {
			list = (ArrayList<String>) server.memList(MenuPage.getTeamId());
			ArrayList<String> id = new ArrayList<String>();
			for(int i = 0; i<list.size(); i++) {
				if(!id.contains(list.get(i))) {
					id.add(list.get(i));
				}
			}
			for(String mem : id) {
				mem_list.getItems().addAll(mem);
			}
			
    	} catch (RemoteException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void back(ActionEvent event) {
    	try {
			Node chatPage = FXMLLoader.load(this.getClass().getResource("../../view/team/Chatting.fxml"));
			
			vContent.getChildren().setAll(chatPage);
    	} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void userReport(ActionEvent event) {
    	
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/team/ChatReport.fxml"));
			Parent root = loader.load();
			
			
	    	
			ChatReport cr = loader.getController();
			cr.setUsId(mem_list.getSelectionModel().getSelectedItem());
			cr.setServie(server);
			
			Stage childSatage = new Stage();

			childSatage.initModality(Modality.WINDOW_MODAL);
			childSatage.initOwner(primaryStage);
			
			Scene scene = new Scene(root);
			childSatage.setScene(scene);
			childSatage.show();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void initialize() {
        assert vContent != null : "fx:id=\"vContent\" was not injected: check your FXML file 'ChatReport.fxml'.";
        assert mem_list != null : "fx:id=\"mem_list\" was not injected: check your FXML file 'ChatReport.fxml'.";
        assert report != null : "fx:id=\"report\" was not injected: check your FXML file 'ChatReport.fxml'.";
        assert back != null : "fx:id=\"back\" was not injected: check your FXML file 'ChatReport.fxml'.";
        
        Registry reg;
        
        try {
			reg = LocateRegistry.getRegistry(localhost,9988);
			server = (ChatServiceServerInf) reg.lookup("Chat");
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        memList();
        
       
    }
}
