package controller.team;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import controller.main.LoginPage;
import controller.main.MenuPage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.DataFormat;
import service.team.TeamHomeServiceInf;
import vo.TeamVO;

/**
 * 팀의 홈페이지
 * @author Jun
 *
 */
public class TeamHome {
	String localhost = LoginPage.getLocalhost();
	// 서비스
	private TeamHomeServiceInf service;
	
	// login한 userId (부모 controller의 getUserId() 사용)
	private static String userId;
	
	// 팀 아이디
	private static String teamId;
	
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Label LabelTeamName;
    @FXML
    private Label LabelTeamMemberCount;
    @FXML
    private Label LabelTeamST_DT;
    @FXML
    private Label LabelTeamED_DT;
    @FXML
    private Label LabelTeamDday;
    @FXML
    private Label LabelTeamPtofile;
    @FXML
    private Button btnTeamJoinYes;
    @FXML
    private Button btnTeamInviteYes;
    @FXML
    private Button btnTeamJoinNo;
    @FXML
    private Button btnTeamInviteNo;

    /**
     * 초대 승낙시
     * @param event
     */
    @FXML
    void btnTeamInviteYesClicked(ActionEvent event) {
    	try {
    		if (service.updateJoinMember(teamId, userId) == true) {
    			String state = service.getJoinMemberState(teamId, userId);
    			// 버튼 설정
    			btnDisable(state);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 초대 거절시
     * @param event
     */
    @FXML
    void btnTeamInviteNoClicked(ActionEvent event) {
    	try {
    		if (service.deleteJoinMember(teamId, userId) == true) {
    			String state = service.getJoinMemberState(teamId, userId);
    			// 버튼 설정
    			btnDisable(state);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    }

    /**
     * 가입 취소시
     * @param event
     */
    @FXML
    void btnTeamJoinNoClicked(ActionEvent event) {
    	btnTeamInviteNoClicked(null);
    }

    /**
     * 가입 신청시
     * @param event
     */
    @FXML
    void btnTeamJoinYesClicked(ActionEvent event) {
    	try {
    		if (service.insertJoinMember(teamId, userId) == true) {
    			String state = service.getJoinMemberState(teamId, userId);
    			// 버튼 설정
    			btnDisable(state);
			}
		} catch (RemoteException e) {
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
        	service = (TeamHomeServiceInf) reg.lookup("TeamHome");
        } catch (RemoteException e) {
        	e.printStackTrace();
        } catch (NotBoundException e) {
        	e.printStackTrace();
        }
        
        // 팀 소개 초기화
        try {
        	// 데이터 가져옴
			TeamVO teamVo = service.getTeamInfo(teamId);
			int memberCount = service.getTeamMemberTotalCount(teamId);
			String state = service.getJoinMemberState(teamId, userId);
			
			// 라벨에 들어갈 데이터 초기화
			String tm_nm = teamVo.getTm_nm();
			String tm_pf = teamVo.getTm_pf();
			String tm_rec_st_dt = teamVo.getTm_rec_st_dt();
			String tm_rec_ed_dt = teamVo.getTm_rec_ed_dt();
			int tm_pn = teamVo.getTm_pn();
			String dDay = "";
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			try {
				Date startDate = sdf.parse(tm_rec_st_dt);
				Date endDate = sdf.parse(tm_rec_ed_dt);
				tm_rec_st_dt = sdf2.format(startDate);
				tm_rec_ed_dt = sdf2.format(endDate);
				
				String today = sdf2.format(new Date());
				Date todayD = sdf2.parse(today);
				
				long between = endDate.getTime() - todayD.getTime();
				
				int dday = (int) (between/(24*60*60*1000));
				if (dday < 0) {
					dDay = "+ " + (dday*-1);
				} else {
					dDay = "- " + dday;
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			// 라벨 초기화
			LabelTeamName.setText(tm_nm);
			LabelTeamPtofile.setText(tm_pf);
			LabelTeamST_DT.setText(tm_rec_st_dt);
			LabelTeamED_DT.setText(tm_rec_ed_dt);
			LabelTeamMemberCount.setText(memberCount + "/" + tm_pn);
			LabelTeamDday.setText("D " + dDay);
			// 버튼 설정
			btnDisable(state);

		} catch (RemoteException e) {
			e.printStackTrace();
		}
  
    }
    
    /**
     * 회원의 가입테이블의 상태에 따른 버튼 disable
     * @param state
     */
    private void btnDisable(String state) {
    	// Y(가입중), W(신청), I(초대)
		if (state == null) {
			state = "";
		}
    	if (state.equals("Y")) {
			btnTeamInviteYes.setDisable(true);
			btnTeamInviteNo.setDisable(true);
			btnTeamJoinYes.setDisable(true);
			btnTeamJoinNo.setDisable(true);
		} else if (state.equals("W")) {
			btnTeamInviteYes.setDisable(true);
			btnTeamInviteNo.setDisable(true);
			btnTeamJoinYes.setDisable(true);
			btnTeamJoinNo.setDisable(false);
		} else if (state.equals("I")) {
			btnTeamInviteYes.setDisable(false);
			btnTeamInviteNo.setDisable(false);
			btnTeamJoinYes.setDisable(true);
			btnTeamJoinNo.setDisable(true);
		} else {
			btnTeamInviteYes.setDisable(true);
			btnTeamInviteNo.setDisable(true);
			btnTeamJoinYes.setDisable(false);
			btnTeamJoinNo.setDisable(true);
		}
    }
}