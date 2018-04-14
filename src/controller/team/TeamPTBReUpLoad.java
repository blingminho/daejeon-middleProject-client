package controller.team;

import java.io.File;
import java.io.FileInputStream;
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
import service.team.TeamPHBUploadServiceInf;
import vo.FileInfoVO;
import vo.FileVO;
import vo.PhbVO;
import javafx.stage.Stage;

public class TeamPTBReUpLoad {
	
	String localhost = LoginPage.getLocalhost();
	
	TeamPHBUploadServiceInf teamPTBUpLoadService;
	
	private PhbVO phbVo;
	
	public void setPhbVo(PhbVO phbVo) {
		this.phbVo = phbVo;
		PTBTitle.setText(phbVo.getPhb_nm());
	}

	private VBox vbox;
	
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
    private ImageView image;

    @FXML
    private Button upPhotobt;
    
 // 파일 저장 경로
 	String dir = "C:/travelMaker/PHB/server/";
 	File filePath;

    @FXML
    void findClick(ActionEvent event) {
    	FileChooser fileChooser = new FileChooser();
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

    @FXML
    void upLoadClick(ActionEvent event) {
    	
    	FileVO fileVo  = new FileVO();
    	
    	if(!PTBAddPhoto.getText().isEmpty()) {
    		// fi_phb_id를 추출해서 fileVo에 삽입해야함
    		fileVo.setFi_path(PTBAddPhoto.getText());
    		fileVo.setFi_phb_id(phbVo.getPhb_id());
    		
    	}else {
    		alert("사진이 선택되지 않았습니다. 사진을 선택해 주세요.", "파일 오류");
    		return;
    	}
    	//System.out.println(phbVo.getPhb_id());
    	int fileResult =0;
    	int phbResult = 0;
    	try {
        	Registry reg = LocateRegistry.getRegistry(localhost, 9988);
        	teamPTBUpLoadService = (TeamPHBUploadServiceInf) reg.lookup("teamPTBReUpLoad");
        	fileResult = teamPTBUpLoadService.phbFileupdate(fileVo);
        } catch (RemoteException e) {
        	e.printStackTrace();
        } catch (NotBoundException e) {
        	e.printStackTrace();
        }
    	
    	if(fileResult !=0) {
    		alert("사진첩이 추가되었습니다.", "사진 올리기");
    		//사진 저장
    		try {
    			File newfile = new File(filePath.getPath());
				FileInputStream fin = new FileInputStream(filePath);
				int len = (int) newfile.length();
				byte[] data = new byte[len];
				fin.read(data);
				
				//PhbVO vo = teamPTBUpLoadService.selectPhbVo(newPhbId);
//				String tm_id = phbVo.getPhb_tm_id();
				String teamID =	phbVo.getPhb_tm_id();
				String newPhbId = phbVo.getPhb_id();
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
        PTBTitle.setEditable(false);
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
