package controller.board;

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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import service.board.BoardHomeServiceInf;
import service.board.BoardWriteServiceInf;
import vo.BoardVO;
import vo.FileInfoVO;
import vo.PhbVO;
import vo.TeamListVO;

public class BoardHome {
	String localhost = LoginPage.getLocalhost();
	
	
	// 부모창
	private static AnchorPane anchorpane;
			
	// 자신을 호출한 부모창 받기
	public static void setParentPane(AnchorPane parentAnchor) {
		anchorpane = parentAnchor;
	}
		
	private BoardHomeServiceInf service;
	
	ObservableList<BoardVO> boardList = FXCollections.observableArrayList();
	List<BoardVO> result = null;
	
	// 테이블 행 수
	private int limit = 15;
		
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text txtBoardHome;

    @FXML
    private ComboBox<String> cmbContent;

    @FXML
    private TextField txtSearch;

    @FXML
    private TableView<BoardVO> boardTable;
    
    @FXML
    private Pagination pagination;

    @FXML
    private TableColumn<BoardVO, String> num;

    @FXML
    private ComboBox<String> cmbHeadline;

    @FXML
    private TableColumn<BoardVO, String> headline;

    @FXML
    private TableColumn<BoardVO, String> write;

    @FXML
    private TableColumn<BoardVO, String> writeDate;

    @FXML
    private Button btnWrite;

    @FXML
    void NewWrite(ActionEvent event) {
    	try {
			Node write = FXMLLoader.load(this.getClass().getResource("../../view/board/boardWrite.fxml"));
			anchorpane.getChildren().setAll(write);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void btnSearch(ActionEvent event) {
    	if(txtSearch.getText().isEmpty()) {
    		alert("검색할 값을 입력해주세요.");
    	}
    	
    	if(cmbContent.getValue().equals("제목")) {
    		try {
				List<BoardVO> titleList = service.getBoardTitle(txtSearch.getText());
				
				boardList.clear();
				boardList.addAll(titleList);
				
			} catch (RemoteException e) {
				e.printStackTrace();
			}
    	} else if(cmbContent.getValue().equals("내용")) {
    		try {
				List<BoardVO> contentList = service.getBoardContent(txtSearch.getText());
				
				boardList.clear();
				boardList.addAll(contentList);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
    	} else if(cmbContent.getValue().equals("유저 아이디")) {
    		try {
				List<BoardVO> userIdList = service.getBoardUserId(txtSearch.getText());
				
				boardList.clear();
				boardList.addAll(userIdList);
				
			} catch (RemoteException e) {
				e.printStackTrace();
			}
    	} else {
    		alert("무엇을 검색할 지 선택해 주세요.");
    		txtSearch.getText();
    	}
    }

    @FXML
    void boardHome(MouseEvent event) {
    	try {
			Node home = FXMLLoader.load(this.getClass().getResource("../../view/board/boardHome.fxml"));
			anchorpane.getChildren().setAll(home);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void headlineCmb(ActionEvent event) {
    	if(cmbHeadline.getValue().equals("[여행 정보]")) {
    		try {
				List<BoardVO> titleList = service.getBoardTitle("[여행 정보]");
				
				boardList.clear();
				boardList.addAll(titleList);
				
			} catch (RemoteException e) {
				e.printStackTrace();
			}
    	} else if(cmbHeadline.getValue().equals("[여행 후기]")) {
    		try {
    			List<BoardVO> titleList = service.getBoardTitle("[여행 후기]");
				
				boardList.clear();
				boardList.addAll(titleList);
				
			} catch (RemoteException e) {
				e.printStackTrace();
			}
    	} else if(cmbHeadline.getValue().equals("[팀원 모집]")) {
    		try {
    			List<BoardVO> titleList = service.getBoardTitle("[팀원 모집]");
				
				boardList.clear();
				boardList.addAll(titleList);
				
			} catch (RemoteException e) {
				e.printStackTrace();
			}
    	}
    	boardTable.setItems(boardList);
    }
    
    @FXML
    void initialize() {
    	
        try {
        	Registry reg = LocateRegistry.getRegistry(localhost, 9988);
        	service = (BoardHomeServiceInf) reg.lookup("BoardHome");
        } catch (RemoteException e) {
        	e.printStackTrace();
        } catch (NotBoundException e) {
        	e.printStackTrace();
        }
        cmbContent.getItems().addAll("제목", "내용", "유저 아이디");
        cmbContent.setValue("세부사항 선택");
        
        cmbHeadline.getItems().addAll("[여행 정보]", "[여행 후기]", "[팀원 모집]");
        cmbHeadline.setValue("말머리 선택");
        
        
        num.setCellValueFactory(new PropertyValueFactory<>("bd_id"));
        headline.setCellValueFactory(new PropertyValueFactory<>("bd_nm"));
        write.setCellValueFactory(new PropertyValueFactory<>("bd_us_id"));
        writeDate.setCellValueFactory(new PropertyValueFactory<>("bd_dt"));
        
        try {
        	result = service.getAllBoard();
        	boardList.clear();
			boardList.addAll(result);
			boardTable.setItems(boardList);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
        
        boardTable.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				if(!boardTable.getSelectionModel().isEmpty()) {
					try {
						BoardVO board = boardTable.getSelectionModel().getSelectedItem();
						BoardComent.setBoard(board);
						Node write = FXMLLoader.load(this.getClass().getResource("../../view/board/boardComent.fxml"));
						anchorpane.getChildren().setAll(write);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			}
        	
		});
        
        pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
	        @Override
	        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
	        	changeTableView(newValue.intValue(), limit);
	        }
	    });
	    
	    init();
	    
	    // 다운
	    downPhoto();
    }
    /**
     * 페이지네이션 초기화 메서드
     */
    public void init() {
        resetPage();
        pagination.setCurrentPageIndex(0);
        changeTableView(0, limit);
    }

    /**
     * 페이지당 자료의 개수를 결정한다
     */
    public void resetPage() {
    	pagination.setPageCount((int) (Math.ceil(result.size() * 1.0 / limit)));
    }
 
    /**
     * 클릭한 페이지에 맞추어 테이블 뷰를 변경한다
     * @param index
     * @param limit
     */
    public void changeTableView(int index, int limit) {
    	int newIndex = index * limit;
		List<BoardVO> trans = result.subList(Math.min(newIndex, result.size()), Math.min(result.size(), newIndex + limit));
		ObservableList<BoardVO> newList = FXCollections.observableArrayList(trans);
		boardTable.setItems(newList);
    }

	public void alert(String msg) {
    	Alert alertWarning = new Alert(AlertType.WARNING);
    	alertWarning.setTitle("경고");
    	alertWarning.setContentText(msg);
    	alertWarning.showAndWait();
    }
    
	private static FileInfoVO[] fileInfoArray;
	
	/**
     * 사진다운
     */
    private void downPhoto() {
    	FileInfoVO[] fileinfoArray = null;
    	// 서버에서 이미지 읽어와 파일 생성
    	try {
    		fileinfoArray = service.getFiles();
    		fileinfoArray = setFiles(fileinfoArray);
    		fileInfoArray = fileinfoArray;
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    	
    	
//    	// 클라이언트 컴퓨터에서 이미지 읽어와 이미지view 초기화
//		try {
//			FileInputStream fis;
//			
//			for (FileInfoVO fileInfoVO : fileinfoArray) {
//				String path = fileInfoVO.getFileName();
//				System.out.println("path : " + path);
//				int bIndex = path.indexOf("client/") + 7;
//				int eIndex = path.lastIndexOf("/");
//				String tm_id = path.substring(bIndex, eIndex);// 팀아이디
//				
//				bIndex = path.lastIndexOf("/") + 1;
//				eIndex = path.lastIndexOf(".");
//				String phb_id = path.substring(bIndex, eIndex);// 사진아이디
//
//				if (tm_id.equals(phbVO.getPhb_tm_id()) && phb_id.equals(phbVO.getPhb_id())) {
//					fis = new FileInputStream(path);
//					Image img = new Image(fis);
//					photo.setImage(img);
//					fis.close();
//					break;
//				}
//				
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
    	
    }

	public static FileInfoVO[] getFileInfoArray() {
		return fileInfoArray;
	}

	public static void setFileInfoArray(FileInfoVO[] fileInfoArray) {
		BoardHome.fileInfoArray = fileInfoArray;
	}
	/**
	 * 서버로부터 받은 FileInfoVO[]과 팀아이디를 이용하여 파일을 만듬
	 * @param FileInfoVO[]
	 * @param 팀아이디
	 * @throws RemoteException
	 */
	public FileInfoVO[] setFiles(FileInfoVO[] info) throws RemoteException {
		FileOutputStream fout = null;
		String dir = "C:/clientImage/";
		
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
