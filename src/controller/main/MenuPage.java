package controller.main;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import controller.board.BoardComent;
import controller.board.BoardHome;
import controller.board.BoardWrite;
import controller.team.Chatting;
import controller.team.TeamMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import service.team.ChatServiceClient;
import service.team.ChatServiceClientInf;
import service.team.ChatServiceServerInf;
/**
 * MenuPage.fxml과 연결된 controller
 * @author Jun
 *
 */
public class MenuPage {
	String localhost = LoginPage.getLocalhost();
	
	// 로그인 Stage
	private static Stage myStage;
	
	/**
	 * 현재 사용하는 controller의 stage 가져옴(로그아웃시 사용)
	 * @param input_myStage
	 */
	public static void setMyStage(Stage input_myStage) {
		myStage = input_myStage;
	}
	
	/**
	 * 탈퇴시 자동로그아웃시 사용
	 * @return 로그인Stage
	 */
	public static Stage getMyStage() {
		return myStage;
	}
	
	
	// login한 userId (부모 controller의 getUserId() 사용)
	private static String userId;
	
	/**
	 * 로그인한 유저아이디 제공
	 * @return 로그인한 유저아이디
	 */
	public static String getUserId() {
		return userId;
	}
	
	/**
	 * 로그인한 유저아이디 셋팅
	 * @param 로그인한 유저아이디
	 */
	public static void setUserId(String inputUserId) {
		userId = inputUserId;
	}
	
	// 팀 아이디
	private static String teamId = "3";
	
	/**
	 * 마지막으로 접근한 팀아이디 제공
	 * @return 팀아이디
	 */
	public static String getTeamId() {
		return teamId;
	}
	/**
	 * 마지막으로 접근한 팀아이디 셋팅
	 * @param 팀아이디
	 */
	public static void setTeamId(String inputTeamId) {
		teamId = inputTeamId;
	}
	// 선택한 메뉴를 담는 변수
	private static String selectedItem;
	/**
	 * 메뉴에서 선택한 항목에 대한 정보 제공
	 * @return 선택한 항목 정보
	 */
	public static String getSelected() {
		return selectedItem;
	}
	public static void setSeleted(String select) {
		selectedItem = select;
	}
	
	private static AnchorPane MenuPane;
	public static AnchorPane getMenuPane(){
		return MenuPane;
	}
	
	//===================================================================================================
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ImageView btnLogo;

    @FXML
    private ImageView imgLogoMy;

    @FXML
    private ImageView imgLogoLogout;

    @FXML
    private AnchorPane paneContent;

    @FXML
    void btnBoardClicked(ActionEvent event) {
    	try {
    		Node boardPage = FXMLLoader.load(this.getClass().getResource("../../view/board/BoardHome.fxml"));
    		paneContent.getChildren().setAll(boardPage);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void btnLogoClicked(MouseEvent event) {
    	btnSearchPageClicked(null);
    }
    
    /**
     * 마이페이지
     * @param event
     */
    @FXML
    void btnMyClicked(ActionEvent event) {
    	try {
    		selectedItem = "MyHome";
    		Node myPage = FXMLLoader.load(this.getClass().getResource("../../view/my/My.fxml"));
    		paneContent.getChildren().setAll(myPage);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    /**
     *  프로필 관리
     * @param event
     */
    @FXML
    void btnMyProfileClicked(ActionEvent event) {
    	try {
    		selectedItem = "MyHome";
    		Node myProfile = FXMLLoader.load(this.getClass().getResource("../../view/my/My.fxml"));
    		paneContent.getChildren().setAll(myProfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     *  내 일정(수정 예정)
     * @param event
     */
    @FXML
    void btnMyScheduleClicked(ActionEvent event) {
    	try {
    		selectedItem = "MySchedule";
    		Node mySchedule = FXMLLoader.load(this.getClass().getResource("../../view/my/My.fxml"));
    		paneContent.getChildren().setAll(mySchedule);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     *  참여팀 목록
     * @param event
     */
    @FXML
    void btnMyTeamListClicked(ActionEvent event) {
    	try {
    		selectedItem = "MyList";
    		Node myTeamList = FXMLLoader.load(this.getClass().getResource("../../view/my/My.fxml"));
    		paneContent.getChildren().setAll(myTeamList);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 모집중인 팀 목록보기
     * @param event
     */
    @FXML
    void btnRecruitTeamListClicked(ActionEvent event) {
    	try {
    		Node teamListPage = FXMLLoader.load(this.getClass().getResource("../../view/teamList/TeamList.fxml"));
    		paneContent.getChildren().setAll(teamListPage);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    /**
     * 검색하기 페이지
     * @param event
     */
    @FXML
    void btnSearchPageClicked(ActionEvent event) {
    	try {
    		selectedItem = "SearchTravel";
    		Node teamPage = FXMLLoader.load(this.getClass().getResource("../../view/team/Team.fxml"));
    		paneContent.getChildren().setAll(teamPage);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void btnTeamBoardClicked(ActionEvent event) {
    	selectedItem = "TeamBoard";
    	
    	
    	
    }
    
    ChatServiceServerInf server;
    
    @FXML
    void btnTeamChatClicked(ActionEvent event) {
    	selectedItem = "TeamChat";
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("../../view/team/Chatting.fxml"));
    	
    	try {
    		ChatServiceClient client = new ChatServiceClient();
    		boolean is = server.makeChat(getTeamId());
    		
    		server.joinChat(getTeamId(), client);
    		
    		Parent root = loader.load();
    		
    		Chatting controller = (Chatting)loader.getController();
    		controller.setChat(server, client, getTeamId());
    		
			Stage stage = new Stage();
			stage.initModality(Modality.NONE);
			
			
			Scene scene = new Scene(root);
			stage.setTitle("팀 채팅");
			stage.setScene(scene);
			stage.show();
			
		
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    /**
     * 팀페이지 -> 팀 홈페이지로 이동
     * @param event
     */
    @FXML
    void btnTeamClicked(ActionEvent event) {
    	btnTeamHomeClicked(event);
    }

    /**
     * 팀 홈페이지
     * @param event
     */
    @FXML
    void btnTeamHomeClicked(ActionEvent event) {
    	try {
    		selectedItem = "TeamHome";
    		Node teamPage = FXMLLoader.load(this.getClass().getResource("../../view/team/Team.fxml"));
    		paneContent.getChildren().setAll(teamPage);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    /**
     * 팀 관리 페이지
     * @param event
     */
    @FXML
    void btnTeamManageClicked(ActionEvent event) {
    	try {
    		selectedItem = "TeamManage";
    		Node teamPage = FXMLLoader.load(this.getClass().getResource("../../view/team/Team.fxml"));
    		paneContent.getChildren().setAll(teamPage);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    /**
     * 팀원 프로필 페이지
     * @param event
     */
    @FXML
    void btnTeamMemberListClicked(ActionEvent event) {
    	try {
    		selectedItem = "TeamMemberList";
    		Node teamPage = FXMLLoader.load(this.getClass().getResource("../../view/team/Team.fxml"));
    		paneContent.getChildren().setAll(teamPage);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    /**
     * 팀 탈퇴 팝업창
     * @param event
     */
    @FXML
    void btnTeamOutClicked(ActionEvent event) {
    	try {
    		selectedItem = "TeamOut";
    		Node teamPage = FXMLLoader.load(this.getClass().getResource("../../view/team/Team.fxml"));
    		paneContent.getChildren().setAll(teamPage);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    /**
     * 팀 사진첩 페이지
     * @param event
     */
    @FXML
    void btnTeamPhbClicked(ActionEvent event) {
    	try {
    		selectedItem = "TeamPhb";
    		Node teamPage = FXMLLoader.load(this.getClass().getResource("../../view/team/Team.fxml"));
    		paneContent.getChildren().setAll(teamPage);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 팀 일정관리 페이지
     * @param event
     */
    @FXML
    void btnTeamScheduleClicked(ActionEvent event) {
    	try {
    		selectedItem = "TeamSchedule";
//    		selectedItem = "MySchedule";
    		Node teamPage = FXMLLoader.load(this.getClass().getResource("../../view/team/Team.fxml"));
    		paneContent.getChildren().setAll(teamPage);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void btnTeamTravelListClicked(ActionEvent event) {
    	try {
    		selectedItem = "TravelList";
    		Node teamPage = FXMLLoader.load(this.getClass().getResource("../../view/team/Team.fxml"));
    		paneContent.getChildren().setAll(teamPage);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void imgLogoLogoutClicked(MouseEvent event) {
    	Scene scene;
		try {
			scene = new Scene(FXMLLoader.load(getClass().getResource("../../view/main/LoginPage.fxml")));
			LoginPage.setParentStage(myStage);
			myStage.setScene(scene);
			myStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void imgLogoMyClicked(MouseEvent event) {
    	btnMyClicked(null);
    }

    @FXML
    void initialize() throws IOException {
    	Registry reg;
		try {
			reg = LocateRegistry.getRegistry(localhost,9988);
			server = (ChatServiceServerInf) reg.lookup("Chat");
			
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
    	System.out.println(server);
        BoardHome.setParentPane(paneContent);
        BoardWrite.setParentPane(paneContent);
        BoardComent.setParentPane(paneContent);
        MenuPane = paneContent;
        
        btnSearchPageClicked(null);
    }
}
