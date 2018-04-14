package controller.team;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import controller.main.LoginPage;
import controller.main.MenuPage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import service.admin.UserManageServiceInf;
import service.team.TeamPHBUploadServiceInf;
import vo.FileInfoVO;
import vo.FileVO;
import vo.PhbVO;
import javafx.stage.Stage;

public class TeamPTBUpLoad {
	
	String localhost = LoginPage.getLocalhost();
	
	TeamPHBUploadServiceInf teamPTBUpLoadService;
	
	String fileName;	//서버에 저장하는 파일이름
	
	String newPhbId;	//새로생성된 사진첩 ID
	
	// 사용자의 이미지 파일 경로
	File filePath;
	
	// 파일 저장 경로
	String dir = "C:/travelMaker/PHB/server/";
	
	
    @FXML
    private ImageView image;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox PTBPane;

    @FXML
    private TextField PTBTitle;

    @FXML
    private TextField PTBAddPhoto;
    
    @FXML
    private Button upPhotobt;

    @FXML
    void findClick(ActionEvent event) {	//이미지 파일 불러오기
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setInitialDirectory(new File("C://"));
    	fileChooser.getExtensionFilters().addAll(
    			new ExtensionFilter("Image File","*.png","*.jpg")
    			);
    	Stage stage = (Stage) PTBTitle.getScene().getWindow();
    	File selectedFile  = fileChooser.showOpenDialog(stage);
    	Image userimage;
		try {
			userimage = new Image(selectedFile.toURI().toURL().toString());
			PTBAddPhoto.setText(selectedFile.toURI().toURL().toString());	//이미지 파일 경로 표시
			filePath = new File(selectedFile.getPath());
			image.setImage(userimage);		//이미지파일 띄우기
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
    	if(PTBTitle.getText()!=null && selectedFile != null) {
    		upPhotobt.setDisable(false);
    	}
    }
    private VBox vbox;
    @FXML
    void upLoadClick(ActionEvent event) {
    	PhbVO phbVo = new PhbVO();
    	phbVo.setPhb_tm_id(MenuPage.getTeamId());
    	phbVo.setPhb_us_id(MenuPage.getUserId());
    	phbVo.setPhb_nm(PTBTitle.getText());
    	
    	FileVO fileVo  = new FileVO();
    	if(PTBAddPhoto.getText()!=null) {
    		fileVo.setFi_path(PTBAddPhoto.getText());
    	}else {
    		alert("사진이 선택되지 않았습니다. 사진을 선택해 주세요.", "파일 오류");
    		return;
    	}
    	
    	int phbResult = 0;
    	int fileResult = 0;
    	try {
        	Registry reg = LocateRegistry.getRegistry(localhost, 9988);
        	teamPTBUpLoadService = (TeamPHBUploadServiceInf) reg.lookup("teamPTBUpLoad");
        	phbResult = teamPTBUpLoadService.phbInsert(phbVo);
        	newPhbId = teamPTBUpLoadService.getPhbID();
        	fileVo.setFi_phb_id(newPhbId);
        	fileResult = teamPTBUpLoadService.phbFileInsert(fileVo);
        } catch (RemoteException e) {
        	e.printStackTrace();
        } catch (NotBoundException e) {
        	e.printStackTrace();
        }
    	
    	if(phbResult != 0 && fileResult !=0) {
    		alert("사진첩이 추가되었습니다.", "사진 올리기");
    		//사진 저장
    		try {
    			File newfile = new File(filePath.toString());
				FileInputStream fin = new FileInputStream(filePath);
				int len = (int) newfile.length();
				byte[] data = new byte[len];
				fin.read(data);
				
				PhbVO vo = teamPTBUpLoadService.selectPhbVo(newPhbId);
				String teamID =	vo.getPhb_tm_id();
				// 여기까지 (할일: 파일 이름 지정해서 저장하기)
				String path = fileVo.getFi_path();
				String last =  path.substring(path.lastIndexOf("."));
				String pathname = dir + teamID + "/" + newPhbId + last;
				
				FileInfoVO newFileInfo = new FileInfoVO();
				newFileInfo.setFileName(pathname);
				newFileInfo.setFileData(data);
				teamPTBUpLoadService.setFiles(newFileInfo, teamID);

				Node content = FXMLLoader.load(this.getClass().getResource("../../view/team/TeamPTB.fxml"));
	    		vbox.getChildren().set(1, content);
			} catch (IOException e) {
				e.printStackTrace();
			}	
    		
    	}else{
    		alert("오류가 발생되어 정상 처리 하지 못하였습니다.", "알수없는 오류");
    		return;
    	}
    	
    }

    @FXML
    void initialize() {
    	vbox = Team.getPane();

    }
    
    public void alert(String msg, String title) {
    	Alert alertWarning = new Alert(AlertType.INFORMATION);
    	alertWarning.setTitle(title);
    	alertWarning.setContentText(msg);
    	alertWarning.showAndWait();
    }
    
    public boolean alertConfirm(String msg, String title) {
    	Alert alertConfirm = new Alert(AlertType.CONFIRMATION);
    	alertConfirm.setTitle(title);
    	alertConfirm.setContentText(msg);
    	alertConfirm.showAndWait();
    	if (alertConfirm.getResult() == ButtonType.OK) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
}
