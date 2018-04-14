package controller.team;

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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import service.team.TeamProfileServiceInf;
import vo.UserVO;

/**
 * 팀원 프로필 페이지
 * @author Jun
 *
 */
public class TeamMemberProfile {
	String localhost = LoginPage.getLocalhost();
	// 서비스
	TeamProfileServiceInf service;
	
	// login한 userId (부모 controller의 getUserId() 사용)
	private static String userId;
		
	// 팀 아이디
	private static String teamId;
	
	// pagination limit
	private int limit = 20;
	
	// 유저 목록
	private List<String> memberList;
	
	
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private ListView<String> memberListView;
    @FXML
    private Pagination memberListPagination;
    
    
    @FXML
    void memberListClicked(MouseEvent event) {
    	String selectedMember = memberListView.getSelectionModel().getSelectedItem();
    	String selectedMemberId = selectedMember.substring(selectedMember.indexOf("(")+1, selectedMember.indexOf(")"));
    	// 팝업창에 선택한 아이디를 셋팅
    	TeamMemberProfilePopup.setUserId(selectedMemberId);
    	
    	Stage popUpStage = new Stage();
		try {
			Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../../view/team/TeamMemberProfilePopup.fxml")));
			popUpStage.setTitle("멤버 상세 프로필");
			popUpStage.setScene(scene);
			popUpStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    
    @FXML
    void initialize() {
        // userId 초기화
        userId = MenuPage.getUserId();
        
        // teamId 초기화
        teamId = MenuPage.getTeamId();
        
        // service와 연결
        try {
        	Registry reg = LocateRegistry.getRegistry(localhost, 9988);
        	service = (TeamProfileServiceInf) reg.lookup("TeamProfile");
        } catch (RemoteException e) {
        	e.printStackTrace();
        } catch (NotBoundException e) {
        	e.printStackTrace();
        }
        
        // ListView에 담을 데이터 가져오기
        List<UserVO> userVOs = null;
        Object obj = null;
		try {
			obj = service.getTeamMemberList(teamId);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
        if (obj != null) {
        	userVOs = (List<UserVO>)obj;
		}
        
        // ListView에 들어갈 데이터List 선언 및 초기화
        memberList = new ArrayList<>();
        for (int i = 0; i < userVOs.size(); i++) {
        	memberList.add(userVOs.get(i).getUs_nm() + "(" + userVOs.get(i).getUs_id() + ")");
		}
        
        // 페이지네이션 클릭시 진행되는 내부 메서드
        memberListPagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            	changeListView(newValue.intValue(), limit);
            }
        });
        
        init();
        
    }
    /**
     * 페이지네이션 초기화 메서드
     */
    public void init() {
        resetPage();
        memberListPagination.setCurrentPageIndex(0);
        changeListView(0, limit);
    }

    /**
     * 페이지당 자료의 개수를 결정한다
     */
    public void resetPage() {
    	memberListPagination.setPageCount((int) (Math.ceil(memberList.size() * 1.0 / limit)));
    }
    
    /**
     * 클릭한 페이지에 맞추어 리스트 뷰를 변경한다
     * @param index
     * @param limit
     */
    public void changeListView(int index, int limit) {
    	int newIndex = index * limit;
    	
    	List<String> trans = memberList.subList(Math.min(newIndex, memberList.size()), Math.min(memberList.size(), newIndex + limit));
    	ObservableList<String> newList = FXCollections.observableArrayList(trans);
    	memberListView.setItems(newList);
    }
    
    
}
