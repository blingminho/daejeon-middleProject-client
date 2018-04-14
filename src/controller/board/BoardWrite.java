package controller.board;

import java.rmi.server.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import service.board.BoardWriteServiceInf;
import vo.BoardVO;
import vo.FileInfoVO;
import vo.FileVO;

public class BoardWrite {
	String localhost = LoginPage.getLocalhost();
	
	
	// 부모창
	private static AnchorPane anchorpane;
		
	// 자신을 호출한 부모창 받기
	public static void setParentPane(AnchorPane parentAnchor) {
		anchorpane = parentAnchor;
	}
	
	private static Stage primaryStae;
	
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStae = primaryStage;
	}
	
	public static void setParentStage(Stage parentStage) {
		primaryStae = parentStage;
	}
	
	int fileCnt = 0;

	private BoardWriteServiceInf service;
	
	// 말머리가 선택되면 저장
	public String headline;
	
	
	//db에 저장하는 파일이름
	String files;
	
	//사용자의 이미지파일의 경로가 들어간다.
//	List<Object> filePath = new ArrayList<Object>();
	File[] filePath = new File[5];
	

	String dir = "file:/C:/Image/"; // 파일을 저장할 경로
	
	BoardVO bvo = new BoardVO();
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> cmbHeadline;

    @FXML
    private TextField txtHeadline;
    
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private TextArea txtArea;

    @FXML
    private VBox vbox;
    
    @FXML
    private ImageView imgView1;
    
    @FXML
    private ImageView imgView2;

    @FXML
    private ImageView imgView3;

    @FXML
    private ImageView imgView4;

    @FXML
    private ImageView imgView5;
    
    @FXML
    private Button Filebtn;
    
    Image img = null;
    
    // 이미지경로를 저장
    String pathLoad = "";
    
    //말머리선택 콤보박스 설정
    Label data1;
    Label data2;
    Label data3;
    
    @FXML
    void btnFile(ActionEvent event) {
    	
    	if(fileCnt > 4) {
    		alert("게시할 사진을 초과했습니다.");
    		return;
    	}
    	
    	FileChooser fileRead = new FileChooser();
    	
    	fileRead.setInitialDirectory(new File("C://")); // 파일불러오기 초기 위치
    	fileRead.getExtensionFilters().add(new ExtensionFilter("Image Files","*.jpg", "*.png", "*.gif")); // 이미지파일들 확장자설정.
    	
    	
    	File selectedFile = fileRead.showOpenDialog(null);
    	

    	if(selectedFile != null) {	
    		// 파일경로 읽어오기
    		pathLoad = selectedFile.getPath();
    		img = new Image("file:/"+selectedFile.getPath());

    		filePath[fileCnt] = new File(selectedFile.getPath());

    		if(imgView1.getImage() == null) {
    			imgView1.setImage(img);
    		} else if(imgView2.getImage() == null) {
    			imgView2.setImage(img);
    		} else if(imgView3.getImage() == null) {
    			imgView3.setImage(img);
    		} else if(imgView4.getImage() == null) {
    			imgView4.setImage(img);
    		} else {
    			imgView5.setImage(img);
    		}
    		fileCnt +=1;
    	} else {
    		return;
    	}
    }
    
    @FXML
    void btnCancel(ActionEvent event) {
    	Node boardHome;
		try {
			boardHome = FXMLLoader.load(this.getClass().getResource("../../view/board/boardHome.fxml"));
			anchorpane.getChildren().setAll(boardHome);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void btnOk(ActionEvent event) {
    	String boardSeqNum = "";// 게시판 시퀀스 번호 얻어와서 저장
    	try {
    		//말머리가 선택되면 제목에 붙여주기 위함
            if(cmbHeadline.getValue().equals(data1.getText())) {
            	headline = data1.getText();
            } else if(cmbHeadline.getValue().equals(data2.getText())) {
            	headline = data2.getText();
            } else if(cmbHeadline.getValue().equals(data3.getText())){
            	headline = data3.getText();
            } else {
            	headline = "";
            }
            
    		// 게시글 저장
    		bvo.setBd_nm(headline+" "+txtHeadline.getText());
    		bvo.setBd_content(txtArea.getText());
    		bvo.setBd_cgr(headline);
    		bvo.setBd_us_id(MenuPage.getUserId());
    		bvo.setBd_tm_id(MenuPage.getTeamId());
    		
    		int cnt = service.setBoardWrite(bvo);
    		boardSeqNum = service.getBoardSeqId();// 게시판 시퀀스 번호 얻어와서 저장
    		
    		
    		String fileSeqNum=""; // 파일시퀀스 번호 얻어와서 저장 다음번호 얻어와야 되기 때문에 널값으로 지정
    		// 파일을 서버에 저장
    		FileInfoVO[] finfo = new FileInfoVO[fileCnt];
    		
    		// db에 저장
    		List<FileVO> fileList = new ArrayList<FileVO>();
    		if(fileCnt != 0) {
    			for(int i = 0; i<finfo.length; i++) {
    				FileVO fvo = new FileVO();
    				int len = (int) filePath[i].length();
    				byte[] data = new byte[len];
    				
    				FileInputStream fin = new FileInputStream(filePath[i]);
    				fin.read(data);
    				
    				fileSeqNum = service.getFileSeqId(); //시퀀스번호 얻어오기
    				String frontFileName = filePath[i].getName().substring(0, filePath[i].getName().indexOf('.'));
    				String afterFileName = filePath[i].getName().substring(filePath[i].getName().indexOf('.'));
    				
    				finfo[i] = new FileInfoVO();
    				finfo[i].setFileName(frontFileName+"_("+fileSeqNum+")"+afterFileName);
    				finfo[i].setFileData(data);
	
//    				fvo[i] = new FileVO();
    				fvo.setFi_path(dir+finfo[i].getFileName());
    				fvo.setFi_bd_id(boardSeqNum);
    				fvo.setFi_id(fileSeqNum);
    				System.out.println(fvo.getFi_bd_id());
    				System.out.println(fvo.getFi_id());
    				System.out.println(fvo.getFi_path());
    				fileList.add(fvo);
    			}
    			service.setFiles(finfo);
    			service.setBoardWriteFile(fileList);
    			
    			if(cnt != 0) {
        			Alert info = new Alert(AlertType.INFORMATION);
            		
            		info.setTitle("글쓰기 등록");
            		info.setHeaderText("게시글에 등록되었습니다.");
            		info.showAndWait();
        		} else {
        			alert("글쓰기 등록 실패");
        		}
    			
    		}
    		
			Node boardHome = FXMLLoader.load(this.getClass().getResource("../../view/board/boardHome.fxml"));
			anchorpane.getChildren().setAll(boardHome);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    	fileCnt = 0;
    }
    
    @FXML
    void imgClk1(MouseEvent event) {
    	try {
    		
    		if(imgView1.getImage() != null) {
    			BoardWriteImgCheck.setImgCnt(1);
    			BoardWriteImgCheck.setImg(imgView1.getImage());	
    		} else {
    			alert("띄울 사진이 없습니다.");
    			return;
    		}
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/board/BoardWriteImgCheck.fxml"));
			Parent root = loader.load();

			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(primaryStae);
			
			Scene scene = new Scene(root);
			stage.setTitle("이미지 확인");
			stage.setScene(scene);
			stage.showAndWait();
			
			if(BoardWriteImgCheck.getImg() == null) {
				imgView1.setImage(null);
				fileCnt -= 1;
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void imgClk2(MouseEvent event) {
    	try {
    		
    		if(imgView2.getImage() != null) {
    			BoardWriteImgCheck.setImgCnt(2);
    			BoardWriteImgCheck.setImg(imgView2.getImage());	
    		} else {
    			alert("띄울 사진이 없습니다.");
    			return;
    		}
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/board/BoardWriteImgCheck.fxml"));
    		Parent root = loader.load();

			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(primaryStae);
			
			Scene scene = new Scene(root);
			stage.setTitle("이미지 확인");
			stage.setScene(scene);
			stage.showAndWait();
			
			if(BoardWriteImgCheck.getImg() == null) {
				imgView2.setImage(null);
				fileCnt -= 1;
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void imgClk3(MouseEvent event) {
    	try {
    		
    		if(imgView3.getImage() != null) {
    			BoardWriteImgCheck.setImgCnt(3);
    			BoardWriteImgCheck.setImg(imgView3.getImage());	
    		} else {
    			alert("띄울 사진이 없습니다.");
    			return;
    		}
    		
    		BoardWriteImgCheck.setImg(imgView3.getImage());
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/board/BoardWriteImgCheck.fxml"));
			Parent root = loader.load();

			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(primaryStae);
			
			Scene scene = new Scene(root);
			stage.setTitle("이미지 확인");
			stage.setScene(scene);
			stage.showAndWait();
			
			if(BoardWriteImgCheck.getImg() == null) {
				imgView3.setImage(null);
				fileCnt -= 1;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void imgClk4(MouseEvent event) {
    	try {
    		
    		if(imgView4.getImage() != null) {
    			BoardWriteImgCheck.setImgCnt(4);
    			BoardWriteImgCheck.setImg(imgView4.getImage());	
    		} else {
    			alert("띄울 사진이 없습니다.");
    			return;
    		}
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/board/BoardWriteImgCheck.fxml"));
			Parent root = loader.load();

			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(primaryStae);
			
			Scene scene = new Scene(root);
			stage.setTitle("이미지 확인");
			stage.setScene(scene);
			stage.showAndWait();
			
			if(BoardWriteImgCheck.getImg() == null) {
				imgView4.setImage(null);
				fileCnt -= 1;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void imgClk5(MouseEvent event) {
    	try {
    		
    		if(imgView5.getImage() != null) {
    			BoardWriteImgCheck.setImgCnt(5);
    			BoardWriteImgCheck.setImg(imgView5.getImage());	
    		} else {
    			alert("띄울 사진이 없습니다.");
    			return;
    		}
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/board/BoardWriteImgCheck.fxml"));
			Parent root = loader.load();

			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(primaryStae);
			
			Scene scene = new Scene(root);
			stage.setTitle("이미지 확인");
			stage.setScene(scene);
			stage.showAndWait();
			
			if(BoardWriteImgCheck.getImg() == null) {
				imgView5.setImage(null);
				fileCnt -= 1;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void initialize() {
    	assert cmbHeadline != null : "fx:id=\"cmbHeadline\" was not injected: check your FXML file 'boardWrite.fxml'.";
        assert txtHeadline != null : "fx:id=\"txtHeadline\" was not injected: check your FXML file 'boardWrite.fxml'.";
        assert Filebtn != null : "fx:id=\"Filebtn\" was not injected: check your FXML file 'boardWrite.fxml'.";
        assert scrollPane != null : "fx:id=\"scrollPane\" was not injected: check your FXML file 'boardWrite.fxml'.";
        assert txtArea != null : "fx:id=\"txtArea\" was not injected: check your FXML file 'boardWrite.fxml'.";
        assert vbox != null : "fx:id=\"vbox\" was not injected: check your FXML file 'boardWrite.fxml'.";
        assert imgView1 != null : "fx:id=\"imgView1\" was not injected: check your FXML file 'boardWrite.fxml'.";
        assert imgView2 != null : "fx:id=\"imgView2\" was not injected: check your FXML file 'boardWrite.fxml'.";
        assert imgView3 != null : "fx:id=\"imgView3\" was not injected: check your FXML file 'boardWrite.fxml'.";
        assert imgView4 != null : "fx:id=\"imgView4\" was not injected: check your FXML file 'boardWrite.fxml'.";
        assert imgView5 != null : "fx:id=\"imgView5\" was not injected: check your FXML file 'boardWrite.fxml'.";
        
        try {
        	Registry reg = LocateRegistry.getRegistry(localhost, 9988);
        	service = (BoardWriteServiceInf) reg.lookup("BoardWrite");
        } catch (RemoteException e) {
        	e.printStackTrace();
        } catch (NotBoundException e) {
        	e.printStackTrace();
        }
        
        
        data1 = new Label("[여행 정보]");
        data1.setTextFill(Color.web("#C8D3B0")); //#5F497A, #5198AB
        
        data2 = new Label("[여행 후기]");
        data2.setTextFill(Color.web("5F497A"));
        
        data3 = new Label("[팀원 모집]");
        data3.setTextFill(Color.web("5198AB"));
        
        cmbHeadline.getItems().addAll(data1.getText(), data2.getText(), data3.getText());
        cmbHeadline.setValue("말머리 선택");
  
    }
    
    public void alert(String msg) {
    	Alert alertWarning = new Alert(AlertType.WARNING);
    	alertWarning.setTitle("WARNING");
    	alertWarning.setContentText(msg);
    	alertWarning.showAndWait();
    }
}
