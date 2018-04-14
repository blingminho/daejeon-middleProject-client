package controller.board;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import controller.main.LoginPage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import service.board.BoardWriteServiceInf;

public class BoardWriteImgCheck {
	String localhost = LoginPage.getLocalhost();
	
	
	private static Stage primaryStae;
	
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStae = primaryStage;
	}
	
	public static void setParentStage(Stage parentStage) {
		primaryStae = parentStage;
	}
	
	
	private static Image img;
		
	/**
	 * 글쓰기에서 올린 이미지를 확인하기 위해 제공
	 * @return 글쓰기에 블러온 이미지
	 */
	public static Image getImg() {
		return img;
	}
	
	/**
	 * 글쓰기에서 불러온 이미지를 저장.
	 * @param 글쓰기에서 불러온 이미지
	 */
	public static void setImg(Image inputImg) {
		img = inputImg;
	}
	
	/**
	 * 삭제시 몇번째 사진을 클릭했는지 확인하기 위해 사용
	 */
	private static int imgCnt;
	
	/**
	 * 삭제시 몇번째 사진을 클릭해서 들어온지 확인하기 위해 사용
	 * @param 몇번째 사진을 클릭해서 들어온지 확인하기위해 사용
	 */
	public static void setImgCnt(int cnt) {
		imgCnt = cnt;
	}
	
	private BoardWriteServiceInf service;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView imageView;

    @FXML
    private Button ok;

    @FXML
    private Button delete;
    
    private BoardWrite bw;

    @FXML
    void btnDelete(ActionEvent event) {
    	
    	if(imgCnt == 1) {
    		BoardWriteImgCheck.setImg(null);
    	} else if(imgCnt ==2){
    		BoardWriteImgCheck.setImg(null);
    	} else if(imgCnt ==3){
    		BoardWriteImgCheck.setImg(null);
    	} else if(imgCnt ==4){
    		BoardWriteImgCheck.setImg(null);
    	} else {
    		BoardWriteImgCheck.setImg(null);
    	}
    	
    	Stage ok = (Stage) this.ok.getScene().getWindow();
    	ok.close();
    }

    @FXML
    void btnOk(ActionEvent event) {
    	Stage ok = (Stage) this.ok.getScene().getWindow();
    	ok.close();
    }

    @FXML
    void initialize() {
    	assert imageView != null : "fx:id=\"imageView\" was not injected: check your FXML file 'BoardWriteImgCheck.fxml'.";
        assert ok != null : "fx:id=\"ok\" was not injected: check your FXML file 'BoardWriteImgCheck.fxml'.";
        assert delete != null : "fx:id=\"delete\" was not injected: check your FXML file 'BoardWriteImgCheck.fxml'.";
        
        try {
        	Registry reg = LocateRegistry.getRegistry(localhost, 9988);
        	service = (BoardWriteServiceInf) reg.lookup("BoardWrite");
        } catch (RemoteException e) {
        	e.printStackTrace();
        } catch (NotBoundException e) {
        	e.printStackTrace();
        }
        
        imageView.setImage(img);
    }
    
    public void getController(BoardWrite bw) {
    	this.bw = bw;
    }
}