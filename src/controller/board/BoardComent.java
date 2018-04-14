package controller.board;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.ResourceBundle;

import controller.main.LoginPage;
import controller.main.MenuPage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import service.board.BoardComentServiceInf;
import vo.BoardVO;
import vo.ComentVO;
import vo.FileInfoVO;
import vo.FileVO;
import vo.UserVO;

public class BoardComent {
	String localhost = LoginPage.getLocalhost();
	
                   	
	// 부모창
	private static AnchorPane anchorpane;
				
	// 자신을 호출한 부모창 받기
	public static void setParentPane(AnchorPane parentAnchor) {
		anchorpane = parentAnchor;
	}
	
	private static Stage primaryStage;
	
	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
	public static void setParentStage(Stage parentStage) {
		primaryStage = parentStage;
	}
		
	private static BoardVO board;
		
	public static void setBoard(BoardVO bvo) {
		board = bvo;
	}

	private BoardComentServiceInf service;
	
	List<ComentVO> comentList = null;
	
	ObservableList<String> list = FXCollections.observableArrayList();
	
	private BoardComent bc;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label boardHeadline;

    @FXML
    private Label boardDate;

    @FXML
    private Label boardUsId;

    @FXML
    private TextArea boardTxtArea;// 게시판 내용

    @FXML
    private ListView<String> boardComent; // 댓글

    @FXML
    private TextArea txtComent; //댓글

    @FXML
    private Button comentInsertBtn;

    @FXML
    private Button updateBtn;

    @FXML
    private Button delBtn;

    @FXML
    private ImageView imageView1;

    @FXML
    private ImageView imageView2;

    @FXML
    private ImageView imageView3;

    @FXML
    private ImageView imageView4;

    @FXML
    private ImageView imageView5;

    @FXML
    void btnBoardList(ActionEvent event) {
    	try {
			Node home = FXMLLoader.load(this.getClass().getResource("../../view/board/boardHome.fxml"));
			anchorpane.getChildren().setAll(home);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    // 게시글 삭제
    @FXML
    void btnDel(ActionEvent event) {
    	Alert confirm = new Alert(AlertType.CONFIRMATION);
    	
    	confirm.setTitle("게시글 삭제");
    	confirm.setHeaderText("게시글을 삭제하시겠습니까?");
    	
    	ButtonType btnResult = confirm.showAndWait().get();
    	
    	if(btnResult == ButtonType.OK) {
    		try {
				int result = service.deleteBoard(board.getBd_id());
				if(result >0) {
					
					Node home = FXMLLoader.load(this.getClass().getResource("../../view/board/boardHome.fxml"));
					anchorpane.getChildren().setAll(home);
					
				} else {
					alert("삭제 실패");
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	} else if(btnResult == ButtonType.CANCEL){
    		return;
    	}
    }

    // 게시글 수정
    @FXML
    void btnUpdate(ActionEvent event) {
    	BoardVO bvo = new BoardVO();
    	
    	bvo.setBd_content(boardTxtArea.getText());
    	bvo.setBd_id(board.getBd_id());
    	
    	try {
			int result = service.updateBoard(bvo);
			
			if(result != 0) {
				Alert info = new Alert(AlertType.INFORMATION);
        		
        		info.setTitle("글쓰기 수정");
        		info.setHeaderText("게시글이 수정되었습니다.");
        		info.showAndWait();
        		
        		Node home = FXMLLoader.load(this.getClass().getResource("../../view/board/boardHome.fxml"));
    			anchorpane.getChildren().setAll(home);
    			
			} else {
				alert("글쓰기 수정 실패!!");
				return;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    // 댓글 등록
    @FXML
    void insertComentBtn(ActionEvent event) {
    	
    	ComentVO cvo = new ComentVO();
    	
    	try {
    		List<UserVO> userList = service.getUserInfo(MenuPage.getUserId());
    		
    		cvo.setCm_bd_id(board.getBd_id());
    		for (UserVO userVO : userList) {
    			cvo.setCm_us_id(userVO.getUs_id());
    			cvo.setCm_us_nm(userVO.getUs_nm());
			}
    		cvo.setCm_content(txtComent.getText());
    		
			int result = service.setComent(cvo);
			
			if(result == 1) {
				Alert info = new Alert(AlertType.INFORMATION);
				
				info.setTitle("댓글등록");
				info.setHeaderText("댓글이 등록되었습니다.");
				info.showAndWait();
				
				txtComent.clear();
				
				Node write = FXMLLoader.load(this.getClass().getResource("../../view/board/boardComent.fxml"));
				anchorpane.getChildren().setAll(write);				
			} else {
				alert("등록 실패");
			}
			
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void imgClk1(MouseEvent event) {
    	try {
    		
    		if(imageView1.getImage() != null) {
    			BoardImgCheck.setImg(imageView1.getImage());	
    		} else {
    			alert("띄울 사진이 없습니다.");
    			return;
    		}
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/board/BoardImgCheck.fxml"));
			Parent root = loader.load();

			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(primaryStage);
			
			Scene scene = new Scene(root);
			stage.setTitle("이미지 확인");
			stage.setScene(scene);
			stage.showAndWait();
			
			if(BoardImgCheck.getImg() == null) {
				imageView1.setImage(null);
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void imgClk2(MouseEvent event) {
    	try {
    		
    		if(imageView2.getImage() != null) {
    			BoardImgCheck.setImg(imageView2.getImage());	
    		} else {
    			alert("띄울 사진이 없습니다.");
    			return;
    		}
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/board/BoardImgCheck.fxml"));
			Parent root = loader.load();

			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(primaryStage);
			
			Scene scene = new Scene(root);
			stage.setTitle("이미지 확인");
			stage.setScene(scene);
			stage.showAndWait();
			
			if(BoardImgCheck.getImg() == null) {
				imageView2.setImage(null);
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void imgClk3(MouseEvent event) {
    	try {
    		
    		if(imageView3.getImage() != null) {
    			BoardImgCheck.setImg(imageView3.getImage());	
    		} else {
    			alert("띄울 사진이 없습니다.");
    			return;
    		}
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/board/BoardImgCheck.fxml"));
			Parent root = loader.load();

			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(primaryStage);
			
			Scene scene = new Scene(root);
			stage.setTitle("이미지 확인");
			stage.setScene(scene);
			stage.showAndWait();
			
			if(BoardImgCheck.getImg() == null) {
				imageView3.setImage(null);
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void imgClk4(MouseEvent event) {
    	try {
    		
    		if(imageView4.getImage() != null) {
    			BoardImgCheck.setImg(imageView4.getImage());	
    		} else {
    			alert("띄울 사진이 없습니다.");
    			return;
    		}
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/board/BoardImgCheck.fxml"));
			Parent root = loader.load();

			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(primaryStage);
			
			Scene scene = new Scene(root);
			stage.setTitle("이미지 확인");
			stage.setScene(scene);
			stage.showAndWait();
			
			if(BoardImgCheck.getImg() == null) {
				imageView4.setImage(null);
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void imgClk5(MouseEvent event) {
    	try {
    		
    		if(imageView5.getImage() != null) {
    			BoardImgCheck.setImg(imageView1.getImage());	
    		} else {
    			alert("띄울 사진이 없습니다.");
    			return;
    		}
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/board/BoardImgCheck.fxml"));
			Parent root = loader.load();

			Stage stage = new Stage(StageStyle.UTILITY);
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(primaryStage);
			
			Scene scene = new Scene(root);
			stage.setTitle("이미지 확인");
			stage.setScene(scene);
			stage.showAndWait();
			
			if(BoardImgCheck.getImg() == null) {
				imageView5.setImage(null);
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    
    @FXML
    void initialize() {
    	  assert boardHeadline != null : "fx:id=\"boardHeadline\" was not injected: check your FXML file 'boardComent.fxml'.";
          assert boardDate != null : "fx:id=\"boardDate\" was not injected: check your FXML file 'boardComent.fxml'.";
          assert boardUsId != null : "fx:id=\"boardUsId\" was not injected: check your FXML file 'boardComent.fxml'.";
          assert boardTxtArea != null : "fx:id=\"boardTxtArea\" was not injected: check your FXML file 'boardComent.fxml'.";
          assert boardComent != null : "fx:id=\"boardComent\" was not injected: check your FXML file 'boardComent.fxml'.";
          assert txtComent != null : "fx:id=\"txtComent\" was not injected: check your FXML file 'boardComent.fxml'.";
          assert comentInsertBtn != null : "fx:id=\"comentInsertBtn\" was not injected: check your FXML file 'boardComent.fxml'.";
          assert delBtn != null : "fx:id=\"delBtn\" was not injected: check your FXML file 'boardComent.fxml'.";
          assert updateBtn != null : "fx:id=\"updateBtn\" was not injected: check your FXML file 'boardComent.fxml'.";
          assert imageView1 != null : "fx:id=\"imageView1\" was not injected: check your FXML file 'boardComent.fxml'.";
          assert imageView2 != null : "fx:id=\"imageView2\" was not injected: check your FXML file 'boardComent.fxml'.";
          assert imageView3 != null : "fx:id=\"imageView3\" was not injected: check your FXML file 'boardComent.fxml'.";
          assert imageView4 != null : "fx:id=\"imageView4\" was not injected: check your FXML file 'boardComent.fxml'.";
          assert imageView5 != null : "fx:id=\"imageView5\" was not injected: check your FXML file 'boardComent.fxml'.";

        try {
        	Registry reg = LocateRegistry.getRegistry(localhost, 9988);
        	service = (BoardComentServiceInf) reg.lookup("BoardComent");
        } catch (RemoteException e) {
        	e.printStackTrace();
        } catch (NotBoundException e) {
        	e.printStackTrace();
        }
        
        
        
        // 게시글을 선택하면 정보들 뽑아와서 각 라벨에 제목, 날짜, 유저아이디, 내용이 출력
        try {
        	// 게시글 내용 뽑아오기
			String content = service.getBoardInfo(board.getBd_id());
			
			boardHeadline.setText(board.getBd_nm());
			boardDate.setText(board.getBd_dt());
			boardUsId.setText(board.getBd_us_id());
			boardTxtArea.setText(content);
			
			if(!board.getBd_us_id().equals(MenuPage.getUserId())) {
				delBtn.setDisable(true);
				updateBtn.setDisable(true);
				boardTxtArea.setDisable(true);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
        ImageView[] imageview = {imageView1, imageView2,imageView3,imageView4,imageView5};
        try {
			List<FileVO> fileList = service.getFile(board.getBd_id());
			int i = 0;
			for (FileVO fileVO : fileList) {
				String id = fileVO.getFi_path();
				
				String result = id.substring(id.lastIndexOf("/")+1);
				System.out.println(result);
				FileInputStream fis;
				FileInfoVO[] fileinfoArray = BoardHome.getFileInfoArray();
				for (FileInfoVO fileInfoVO : fileinfoArray) {
					String path = fileInfoVO.getFileName();
					System.out.println("path : " + path);
					int bIndex = path.indexOf("Image/") + 6;
					int eIndex = path.lastIndexOf("/");
					String tm_id = path.substring(bIndex);// 팀아이디
					System.out.println(tm_id);

					if (tm_id.equals(result)) {
						System.out.println("성공");
						
						try {
							fis = new FileInputStream(path);
							System.out.println(i);
							Image img = new Image(fis);
							imageview[i].setImage(img);
							
							fis.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					}
					
				}
				i++;
				
			}
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
        
        
        // 등록된 댓글들 게시글선택하면 뽑아오기
        try {
			comentList = service.getComentInfo(board.getBd_id());
			for (ComentVO comentVO : comentList) {
				list.addAll(comentVO.getCm_us_id()+" : "+comentVO.getCm_content()+" ||     "+comentVO.getCm_dt());
				boardComent.setItems(list);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
        
    }
    
    public void alert(String msg) {
    	Alert alertWarning = new Alert(AlertType.WARNING);
    	alertWarning.setTitle("WARNING");
    	alertWarning.setContentText(msg);
    	alertWarning.showAndWait();
    }
}
