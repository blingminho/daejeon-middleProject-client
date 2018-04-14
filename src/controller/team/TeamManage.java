package controller.team;

import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import controller.main.LoginPage;
import controller.main.MenuPage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import service.team.TeamManageServiceInf;
import vo.TeamVO;
import vo.UserVO;

public class TeamManage {
	String localhost = LoginPage.getLocalhost();
	// 서비스
	private TeamManageServiceInf service;
	// 팀 아이디
	private String teamId;
	// 클릭된 멤버ID
	private String selectedMemID;
	// 클릭된 유저ID
	private String selectedUserID;
	// 테이블 행 수
	private int limitMem = 12;
	private int limitUser = 16;
	
	// 멤버 리스트
	private List<UserVO> memberList;
	// 유저 리스트
	private List<UserVO> userList;
	
	
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Pagination memberListPagination;
    @FXML
    private TableView<UserVO> tableMemList;
    @FXML
    private TableColumn<?, ?> colMemNM;
    @FXML
    private TableColumn<?, ?> colMemID;
    @FXML
    private CheckBox checkTeamOP;
    @FXML
    private TextField labelTeamNM;
    @FXML
    private TextField labelTeamPN;
    @FXML
    private DatePicker datePickerST;
    @FXML
    private DatePicker datePickerED;
    @FXML
    private TextArea labelTeamPF;
    @FXML
    private TextField labelInviteID;
    @FXML
    private TableView<UserVO> tableJoinList;
    @FXML
    private TableColumn<?, ?> colUserNM;
    @FXML
    private TableColumn<?, ?> colUserID;
    @FXML
    private Pagination userListPagination;
   
    @FXML
    void btnDeleteTeam(ActionEvent event) {
    	boolean flag = alertConfirm("정말로 팀을 삭제하시겠습니까?", "팀 삭제 확인");
    	
    	if (flag) {
			// 팀 삭제 (팀 id 전송)
    		try {
    			boolean result = service.deleteTeam(teamId);
    			if (!result) {
    				alert("팀 삭제 에러 발생", "팀 삭제 에러");
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
    }

    @FXML
    void btnInviteMember(ActionEvent event) {
    	// 서비스에서 inviteId를 이용하여 가입테이블에 추가(jo_stt = 'I')
    	String inviteId = labelInviteID.getText();
    	try {
			boolean result = service.insertJoinMemberInvite(inviteId, teamId);
			if (!result) {
				alert("초대 에러 발생", "초대 에러");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    	reflesh("Y");
    	reflesh("W");
    }

    @FXML
    void btnJoinNo(ActionEvent event) {
    	// deleteJoin 서비스에서 selectedUserID를 이용하여 가입테이블에서 제거
    	try {
			boolean result = service.deleteJoin(selectedUserID);
			if (!result) {
				alert("거절 에러 발생", "거절 에러");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    	reflesh("Y");
    	reflesh("W");
    }
    @FXML
    void btnOutMember(ActionEvent event) {
    	// deleteJoin 서비스에서 selectedMemID를 이용하여 강퇴시킴 (가입테이블에서 제거)
    	try {
			boolean result = service.deleteJoin(selectedMemID);
			if (!result) {
				alert("추방 에러 발생", "추방 에러");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    	reflesh("Y");
    	reflesh("W");
    }

    @FXML
    void btnJoinYes(ActionEvent event) {
    	// 서비스에서 selectedUserID를 이용하여 가입시킴 (가입테이블 수정)
    	try {
    		// 인원수에 따른 가입 가능 여부
    		int maxPn = service.getTeamPN(teamId);
    		int curPn = memberList.size();
    		
    		boolean result = false;
    		if (maxPn > curPn) {
    			result = service.updateJoinMember(selectedUserID, teamId);
    		}
    		if (!result) {
    			alert("가입 에러 발생", "가입 에러");
    		}
    		
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    	reflesh("Y");
    	reflesh("W");
    }


    @FXML
    void tableMemClicked(MouseEvent event) {
    	selectedMemID = tableMemList.getSelectionModel().getSelectedItem().getUs_id();
    }

    @FXML
    void tableUserClicked(MouseEvent event) {
    	selectedUserID = tableJoinList.getSelectionModel().getSelectedItem().getUs_id();
    }

    
    @FXML
    void btnUpdateTeam(ActionEvent event) {
    	String tm_nm = labelTeamNM.getText();
    	String tm_pf = labelTeamPF.getText();
    	String tm_pn = labelTeamPN.getText();
    	boolean chkOP = checkTeamOP.isSelected();
    	String tm_op = null;
    	if (chkOP) {
			tm_op = "Y";
		} else {
			tm_op = "N";
		}
    	
    	if (datePickerST.getValue() == null || datePickerED.getValue() == null) {
    		// alert창 띄우기
    		alert("날짜를 입력해주세요", "날짜 미입력");
			return;
		}
    	int compareFlag = datePickerST.getValue().compareTo(datePickerED.getValue());
    	if (compareFlag > 0) {
    		alert("종료날짜를 시작날짜보다 이후로 설정하세요", "날짜 오류");
    		return;
    	}
    	
    	String tm_rec_st_dt = datePickerST.getValue().format(DateTimeFormatter.BASIC_ISO_DATE);
    	String tm_rec_ed_dt = datePickerED.getValue().format(DateTimeFormatter.BASIC_ISO_DATE);
    	
    	if (tm_nm.isEmpty() || tm_pf.isEmpty() || tm_pn.isEmpty() || tm_rec_ed_dt.isEmpty() || tm_rec_st_dt.isEmpty() || tm_op.isEmpty()) {
			// alert창 띄우기
    		alert("값을 다 입력해주세요", "팀 정보 수정 오류");
    		return;
		}
    	if (tm_nm == null || tm_pf == null || tm_pn == null || tm_rec_ed_dt == null || tm_rec_st_dt == null || tm_op == null) {
    		// alert창 띄우기
    		alert("값을 다 입력해주세요", "팀 정보 수정 오류");
    		return;
    	}
    	boolean flag = tm_pn.matches("[0-9]{1,2}");
    	if (!flag) {
    		alert("가능한 인원수는 최대 99명입니다", "팀 정보 인원수 정보 오류");
    		return;
    	}
    	
    	int tm_pnInt = Integer.valueOf(tm_pn);
    	
    	TeamVO teamVo = new TeamVO();
    	teamVo.setTm_nm(tm_nm);
    	teamVo.setTm_op(tm_op);
    	teamVo.setTm_pf(tm_pf);
    	teamVo.setTm_pn(tm_pnInt);
    	teamVo.setTm_rec_st_dt(tm_rec_st_dt);
    	teamVo.setTm_rec_ed_dt(tm_rec_ed_dt);
    	teamVo.setTm_id(teamId);
    	
    	// 서비스에 전송하여 update하기
    	try {
			boolean result = service.updateTeam(teamVo);
			if (!result) {
				alert("수정 에러 발생", "팀 정보 수정 에러");
			}
			alert("수정되었습니다", "팀 정보 수정 확인창");
			
			labelTeamNM.clear();
	    	labelTeamPF.clear();
	    	labelTeamPN.clear();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    	
    }

    @FXML
    void initialize() {
    	teamId = MenuPage.getTeamId();
    	Registry reg;
		try {
			reg = LocateRegistry.getRegistry(localhost, 9988);
			service = (TeamManageServiceInf) reg.lookup("TeamManage");
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
    	
    	// 컬럼 설정
        colMemID.setCellValueFactory(new PropertyValueFactory<>("us_id"));
        colMemNM.setCellValueFactory(new PropertyValueFactory<>("us_nm"));
        colUserID.setCellValueFactory(new PropertyValueFactory<>("us_id"));
        colUserNM.setCellValueFactory(new PropertyValueFactory<>("us_nm"));
    	
        reflesh("Y");
        reflesh("W");
        
        
        // 페이지네이션 클릭시 진행되는 내부 메서드
	    memberListPagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
	        @Override
	        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
	        	changeTableView(newValue.intValue(), limitMem, "mem");
	        }
	    });
	    // 페이지네이션 클릭시 진행되는 내부 메서드
	    userListPagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
	    	@Override
	    	public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
	    		changeTableView(newValue.intValue(), limitUser, "user");
	    	}
	    });
	    
	    init();
        
    }
    
    
    public void alert(String msg, String title) {
    	Alert alertWarning = new Alert(AlertType.WARNING);
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

    /**
     * 가입테이블에서 해당 팀의 멤버 리스트 불러옴(jo_stt='Y' 인 멤버)
     * 가입테이블에서 해당 팀에 가입 신청한(jo_stt='W' 인 유저) 유저 리스트 불러옴
     * @param 멤버리스트 초기화(Y), 가입신청리스트 초기화(W)
     */
    private void reflesh(String flag) {
    	List<UserVO> list = null;
    	try {
    		if (flag.equals("Y")) {
    			memberList = service.getTeamMemberTotal(teamId);
    			list = memberList;
    			
			} else if (flag.equals("W")) {
				userList = service.getTeamUserTotal(teamId);
				list = userList;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    	ObservableList<UserVO> obList = FXCollections.observableArrayList();
    	obList.addAll(list);
    	if (flag.equals("Y")) {
    		tableMemList.setItems(obList);
		} else if (flag.equals("W")) {
			tableJoinList.setItems(obList);
		}
    }
    
    
    /**
     * 페이지네이션 초기화 메서드
     */
    public void init() {
        resetPage();
        memberListPagination.setCurrentPageIndex(0);
        userListPagination.setCurrentPageIndex(0);
        changeTableView(0, limitMem, "mem");
        changeTableView(0, limitUser, "user");
    }

    /**
     * 페이지당 자료의 개수를 결정한다
     */
    public void resetPage() {
    	memberListPagination.setPageCount((int) (Math.ceil(memberList.size() * 1.0 / limitMem)));
    	userListPagination.setPageCount((int) (Math.ceil(userList.size() * 1.0 / limitUser)));
    }
    
    /**
     * 클릭한 페이지에 맞추어 테이블 뷰를 변경한다
     * @param index
     * @param limit
     */
    public void changeTableView(int index, int limit, String flag) {
    	int newIndex = index * limit;
    	if (flag.equals("mem")) {
    		List<UserVO> trans = memberList.subList(Math.min(newIndex, memberList.size()), Math.min(memberList.size(), newIndex + limit));
    		ObservableList<UserVO> newList = FXCollections.observableArrayList(trans);
    		tableMemList.setItems(newList);
		} else if (flag.equals("user")) {
			List<UserVO> trans = userList.subList(Math.min(newIndex, userList.size()), Math.min(userList.size(), newIndex + limit));
			ObservableList<UserVO> newList = FXCollections.observableArrayList(trans);
			tableJoinList.setItems(newList);
		}
    }
    
}
