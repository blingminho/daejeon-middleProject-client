package controller.team;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import controller.main.LoginPage;
import controller.main.MenuPage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import service.team.TeamPHBServiceInf;
import vo.FileInfoVO;
import vo.PhbVO;
/**
 * 팀페이지의 사진첩 및 관리자페이지의 사진첩관리
 * @author Jun
 *
 */
public class TeamPTB {
	private static FileInfoVO[] fileInfoArray;
	
	Stage primaryStage = MenuPage.getMyStage();
	String localhost = LoginPage.getLocalhost();
	private TeamPHBServiceInf service;
	
	private List<PhbVO> phbList = new ArrayList<>();
	private ObservableList<PhbVO> phbObList = FXCollections.observableArrayList();
	private FileInfoVO[] fileinfoArray;
	
	private String teamId = MenuPage.getTeamId();
	private String phb_id;
	private String actor;
	private PhbVO selectedItem;
	private VBox vbox;
	
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private VBox PTBPane;
    @FXML
    private Label titleName;
    @FXML
    private Label userName;
    @FXML
    private ImageView photo;
    @FXML
    private Button btnDown;
    @FXML
    private Button btnphotoReup;
    @FXML
    private Button btnphotoDel;
    @FXML
    private Label countGD;
    @FXML
    private Button btnUp;
    @FXML
    private TableView<PhbVO> tablePHB;
    @FXML
    private TableColumn<?, ?> colDT;
    @FXML
    private TableColumn<?, ?> colNM;
    @FXML
    private TableColumn<?, ?> colUser;
    @FXML
    private TableColumn<?, ?> colGD;
    @FXML
    private Button likeLogo;
    
    @FXML
    void LikeClick(ActionEvent event) {
    	// 서비스에서 like 수 update 하기
    	boolean flag;
		try {
			flag = service.updateGood(selectedItem);
			if (flag == false) {
				return;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		reflesh();
		for (PhbVO phbListVO : phbList) {
			if (phbListVO.getPhb_id().equals(selectedItem.getPhb_id())) {
				setPhotoZone(phbListVO);
			}
		}
    }

    @FXML
    void photoDel(ActionEvent event) {
    	// 서비스에서 해당 사진첩 지우기
    	boolean flag;
		try {
			flag = service.deletePhb(selectedItem);
			if (flag == false) {
				return;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    	// 데이터 및 테이블 새로고침
    	reflesh();
    	if (phbList == null || phbList.size() == 0) {
    		photo.setImage(null);
			return;
		}
    	selectedItem = phbList.get(0);
    	if (selectedItem == null) {
    		photo.setImage(null);
			return;
		}
    	setPhotoZone(selectedItem);
    }

    @FXML
    void photoDown(ActionEvent event) {
    	DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(primaryStage);
         
        if(selectedDirectory == null){
            return;
        }else{
        	System.out.println(selectedDirectory.getAbsolutePath());
        	PhbVO phbVO = selectedItem;
        	for (FileInfoVO fileInfoVO : fileinfoArray) {
        		FileInputStream fis = null;
				String path = fileInfoVO.getFileName();
				
				int bIndex = path.indexOf("client/") + 7;
				int eIndex = path.lastIndexOf("/");
				String tm_id = path.substring(bIndex, eIndex);// 팀아이디
				
				bIndex = path.lastIndexOf("/") + 1;
				eIndex = path.lastIndexOf(".");
				String phb_id = path.substring(bIndex, eIndex);// 사진아이디
				
				bIndex = path.lastIndexOf(".");
				String fileInfo = path.substring(bIndex);// 확장자

				if (tm_id.equals(phbVO.getPhb_tm_id()) && phb_id.equals(phbVO.getPhb_id())) {
					try {
						String name = selectedDirectory.getAbsolutePath() + "/downLoadImage" + fileInfo;
						FileOutputStream fout = new FileOutputStream(name);
						fout.write(fileInfoVO.getFileData());
						fout.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				}
			}
        }
    }

    @FXML
    void photoReup(ActionEvent event) {
    	try {
    		FXMLLoader loader = new FXMLLoader(this.getClass().getResource("../../view/team/TeamPTBReUpLoad.fxml"));
			Node content = loader.load();
			TeamPTBReUpLoad reUpload = loader.getController();
			reUpload.setPhbVo(selectedItem);
			vbox.getChildren().set(1, content);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void photoUp(ActionEvent event) {
		try {
			Node content = FXMLLoader.load(this.getClass().getResource("../../view/team/TeamPTBUpLoad.fxml"));
			vbox.getChildren().set(1, content);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    /**
     * 테이블 클릭시 액션
     * @param event
     */
    @FXML
    void tableCliked(MouseEvent event) {
    	if (tablePHB.getSelectionModel() == null) {
    		return;
		}
    	selectedItem = tablePHB.getSelectionModel().getSelectedItem();
    	if (selectedItem == null) {
			return;
		}
    	setPhotoZone(selectedItem);
    }

    @FXML
    void initialize() {
    	vbox = Team.getPane();
    	
    	// service와 연결
        try {
        	Registry reg = LocateRegistry.getRegistry(localhost, 9988);
        	service = (TeamPHBServiceInf) reg.lookup("TeamPHB");
        } catch (RemoteException e) {
        	e.printStackTrace();
        } catch (NotBoundException e) {
        	e.printStackTrace();
        }

        
    	// actor별 페이지 처리
    	if (MenuPage.getSelected() == null) {// 관리자의 사진첩
    		btnphotoReup.setVisible(false);
    		btnUp.setVisible(false);
    		actor = "admin";
    	} else if (MenuPage.getSelected().equals("TeamPhb")) {// 팀페이지내의 사진첩
    		actor = "user";
    	}
    	
    	// 컬럼 설정
    	setTable();
    	
    	// 테이블 새로고침
    	reflesh();
    	
    }
    
    /**
     * 테이블의 데이터를 초기화 및 새로고침
     */
    private void reflesh() {
    	// 사진첩리스트 초기화
    	if (actor.equals("user")) {
    		PhbVO newVO = new PhbVO();
    		newVO.setPhb_tm_id(teamId);
    		try {
				phbList = service.getPhb(newVO);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		} else if (actor.equals("admin")) {
			try {
				phbList = service.getAllPhb();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		
		// 테이블에 들어갈 observableList 초기화
    	phbObList.setAll(phbList);
		tablePHB.getItems().setAll(phbObList);
    }
    
    /**
     * 포토존을 초기화
     * @param 사진첩아이디
     */
    private void setPhotoZone(PhbVO phbVO) {
    	phb_id = phbVO.getPhb_id();
    	System.out.println("setPhotoZone의 phb_id : " + phb_id);
    	// 라벨 초기화
    	userName.setText(phbVO.getPhb_us_id());
    	titleName.setText(phbVO.getPhb_nm());
    	countGD.setText(phbVO.getPhb_gd()+"");
    	
    	// 서버에서 이미지 읽어와 파일 생성
    	try {
    		fileinfoArray = service.getFiles(phbVO.getPhb_tm_id());
    		fileinfoArray = setFiles(fileinfoArray, phbVO.getPhb_tm_id());
    		setFileInfoArray(fileinfoArray);
    	} catch (RemoteException e) {
			e.printStackTrace();
		}
    	
    	
    	// 클라이언트 컴퓨터에서 이미지 읽어와 이미지view 초기화
		try {
			
			photo.setImage(null);
			
			FileInputStream fis;
			
			for (FileInfoVO fileInfoVO : fileinfoArray) {
				String path = fileInfoVO.getFileName();
				System.out.println("path : " + path);
				int bIndex = path.indexOf("client/") + 7;
				int eIndex = path.lastIndexOf("/");
				String tm_id = path.substring(bIndex, eIndex);// 팀아이디
				
				bIndex = path.lastIndexOf("/") + 1;
				eIndex = path.lastIndexOf(".");
				String phb_id = path.substring(bIndex, eIndex);// 사진아이디

				if (tm_id.equals(phbVO.getPhb_tm_id()) && phb_id.equals(phbVO.getPhb_id())) {
					fis = new FileInputStream(path);
					Image img = new Image(fis);
					photo.setImage(img);
					fis.close();
					break;
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
    
    /**
     * 테이블의 컬럼을 설정
     */
    private void setTable() {
    	colDT.setCellValueFactory(new PropertyValueFactory<>("phb_dt"));
    	colGD.setCellValueFactory(new PropertyValueFactory<>("phb_gd"));
    	colNM.setCellValueFactory(new PropertyValueFactory<>("phb_nm"));
    	colUser.setCellValueFactory(new PropertyValueFactory<>("phb_us_id"));
    }

	public static FileInfoVO[] getFileInfoArray() {
		return fileInfoArray;
	}

	public static void setFileInfoArray(FileInfoVO[] fileInfoArray) {
		TeamPTB.fileInfoArray = fileInfoArray;
	}
	
	/**
	 * 서버로부터 받은 FileInfoVO[]과 팀아이디를 이용하여 파일을 만듬
	 * @param FileInfoVO[]
	 * @param 팀아이디
	 * @throws RemoteException
	 */
	public FileInfoVO[] setFiles(FileInfoVO[] info, String tm_id) throws RemoteException {
		FileOutputStream fout = null;
		String dir = "C:/travelMaker/PHB/client/" + tm_id + "/";
		
		File file = new File(dir);
		
		if(!file.exists()) {
			file.mkdirs();
		}
		
		for (int i = 0; i < info.length; i++) {
			try {
				String path = dir+info[i].getFileName();
				fout = new FileOutputStream(path);
				fout.write(info[i].getFileData());
				fout.close();
				info[i].setFileName(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return info;
	}
}
