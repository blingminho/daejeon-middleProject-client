package controller.team;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import controller.main.LoginPage;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import service.team.TeamProfileServiceInf;
import vo.UserVO;
/**
 * 해당 유저의 상세 프로필 조회
 * @author Jun
 *
 */
public class TeamMemberProfilePopup {
	String localhost = LoginPage.getLocalhost();
	private TeamProfileServiceInf service;
	private static String selectedUserId;
	
	/**
	 * 선택한 user의 id를 받아옴
	 * @param 선택한 user의 id
	 */
	public static void setUserId(String userId) {
		selectedUserId = userId;
	}
	
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Label labelID;
    @FXML
    private Label labelNM;
    @FXML
    private Label labelGN;
    @FXML
    private Label labelMAIL;
    @FXML
    private Label labelPH;
    @FXML
    private Label labelBIR;
    @FXML
    private Label labelPF;
    @FXML
    private Label labelRTCN;

    @FXML
    void initialize() {
        // selectedUserId를 이용하여 해당 유저의 UserVO 받아옴
        UserVO userVO = null;
        
        // service와 연결
        try {
        	Registry reg = LocateRegistry.getRegistry(localhost, 9988);
        	service = (TeamProfileServiceInf) reg.lookup("TeamProfile");
        	
        	userVO = service.getTeamMemberProfile(selectedUserId);
        	if (userVO == null) {
        		labelID.setText("오류");
		        labelNM.setText("오류");
		        labelGN.setText("오류");
		        labelMAIL.setText("오류");
		        labelPH.setText("오류");
		        labelBIR.setText("오류");
		        labelPF.setText("오류");
		        labelRTCN.setText("오류");
			} else {
				// Label 초기화
		        labelID.setText(userVO.getUs_id());
		        labelNM.setText(userVO.getUs_nm());
		        labelGN.setText(userVO.getUs_gn());
		        labelMAIL.setText(userVO.getUs_mail());
		        labelPH.setText(userVO.getUs_ph());
		        labelBIR.setText(userVO.getUs_bir());
		        labelPF.setText(userVO.getUs_pf());
		        labelRTCN.setText(userVO.getUs_rt_cn()+"");
			}
        } catch (RemoteException e) {
        	e.printStackTrace();
        } catch (NotBoundException e) {
        	e.printStackTrace();
        }
    }
}