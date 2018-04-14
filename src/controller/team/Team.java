package controller.team;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import controller.main.LoginPage;
import controller.main.MenuPage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import service.team.TeamMenuServiceInf;

/**
 * 팀 페이지를 담는 controller
 * @author Jun
 *
 */
public class Team {
	String localhost = LoginPage.getLocalhost();
	private static VBox pane;

	/**
	 * 팀 페이지의 Vbox 제공
	 * @return
	 */
	public static VBox getPane() {
		return pane;
	}
	
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private VBox teamPane;

    @FXML
    void initialize() {
    	pane = teamPane;
        try {
        	Node menu = FXMLLoader.load(this.getClass().getResource("../../view/team/TeamMenu.fxml"));
        	Node bottom = null;
        	
        	String teamId = MenuPage.getTeamId();
        	String userId = MenuPage.getUserId();
        	
        	// 해당 유저의 해당 팀에 대한 가입 상태 확인
            // service와 연결
            String grade = "";
            try {
            	Registry reg = LocateRegistry.getRegistry(localhost, 9988);
            	TeamMenuServiceInf service = (TeamMenuServiceInf) reg.lookup("TeamMenu");
            	String state = service.getJoinMemberState(teamId, userId);
            	String leaderId = service.getTeamLeaderId(teamId);
            	
            	if (userId.equals(leaderId)) {
    				grade = "leader";
    			} else if (state.equals("Y")) {
    				grade = "member";
    			} else {
    				grade = "N";
    			}
            	
            } catch (RemoteException e) {
            	e.printStackTrace();
            } catch (NotBoundException e) {
            	e.printStackTrace();
            }
            
            // 고른 메뉴에 접근 및 가입 상태에 따른 접근 제어
            bottom = gradeSelect(grade, bottom);
            
            String selectedItem = MenuPage.getSelected();
            // 페이지 셋팅
            if (selectedItem.equals("SearchTravel")) {
            	Node searchNode = FXMLLoader.load(this.getClass().getResource("../../view/team/TeamTravelList.fxml"));
				teamPane.getChildren().setAll(searchNode);
			} else {
				teamPane.getChildren().setAll(menu, bottom);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 회원의 등급(팀멤버 member, 팀리더 leader, 비 멤버 N)에 따른 클릭 버튼 활성화
     * 검색페이지 이동
     * @param grade
     * @throws IOException 
     */
    private Node gradeSelect(String grade, Node bottom) throws IOException {
    	String selectedItem = MenuPage.getSelected();
    	
    	if (grade.equals("N")) {// 해당 팀 멤버가 아닌 경우
    		if (!selectedItem.equals("TeamSchedule")) {
    			bottom = FXMLLoader.load(this.getClass().getResource("../../view/team/TeamHome.fxml"));
    			return bottom;
    		}
    	} else if (grade.equals("member")) {// 해당 팀 멤버인 경우
    		if (selectedItem.equals("TeamOut")) {
    			bottom = FXMLLoader.load(this.getClass().getResource("../../view/team/TeamOut.fxml"));
    			return bottom;
			}
		} else if (grade.equals("leader")) {// 해당 팀 리더인 경우
			if (selectedItem.equals("TeamManage")) {
				bottom = FXMLLoader.load(this.getClass().getResource("../../view/team/TeamManage.fxml"));
				return bottom;
			}
		}
    	if (selectedItem.equals("TeamMemberList")) {
			bottom = FXMLLoader.load(this.getClass().getResource("../../view/team/TeamMemberProfile.fxml"));
		} else if (selectedItem.equals("TeamPhb")) {
			bottom = FXMLLoader.load(this.getClass().getResource("../../view/team/TeamPTB.fxml"));
		} else if (selectedItem.equals("TeamSchedule")) {
			bottom = FXMLLoader.load(this.getClass().getResource("../../view/team/TeamSchedule.fxml"));
		} else if (selectedItem.equals("TravelList")) {
			bottom = FXMLLoader.load(this.getClass().getResource("../../view/team/TeamTravelList.fxml"));
		} else if (selectedItem.equals("TeamChat")) {
//			bottom = FXMLLoader.load(this.getClass().getResource("../../view/team/TeamTravelList.fxml"));
		} else if (selectedItem.equals("TeamBoard")) {
//			bottom = FXMLLoader.load(this.getClass().getResource("../../view/team/TeamTravelList.fxml"));
		} else {
			bottom = FXMLLoader.load(this.getClass().getResource("../../view/team/TeamHome.fxml"));
		}
    	
    	
		return bottom;
    }
    

}