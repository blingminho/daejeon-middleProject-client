package controller.admin;

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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import service.admin.UserManageServiceInf;
import vo.BlackListVO;
import vo.ReportVO;
import vo.UserVO;

public class AdminUserManage {
	String localhost = LoginPage.getLocalhost();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button userOutbt;

    @FXML
    private TableView<UserVO> userTable;

    @FXML
    private TableColumn<?, ?> userIDCol;

    @FXML
    private TableColumn<?, ?> userNMCol;

    @FXML
    private TableColumn<?, ?> userGnCol;

    @FXML
    private TableColumn<?, ?> userBirCol;

    @FXML
    private TableColumn<?, ?> userPhCol;

    @FXML
    private TableColumn<?, ?> userMailCol;

    @FXML
    private TableColumn<?, ?> userReportCol;

    @FXML
    private TableColumn<?, ?> userScsCol;

    @FXML
    private Button reportOkbt;

    @FXML
    private TableView<ReportVO> reportTable;

    @FXML
    private TableColumn<?, ?> reportIDCol;
    
    @FXML
    private TableColumn<?, ?> reportTeamIDCol;

    @FXML
    private TableColumn<?, ?> reportDTCol;

    @FXML
    private TableColumn<?, ?> reportUserIDCol;

    @FXML
    private TableColumn<?, ?> reportWhyCol;

    @FXML
    private TableView<BlackListVO> blackTable;

    @FXML
    private TableColumn<?, ?> blackIDCol;

    @FXML
    private TableColumn<?, ?> blackUserIDCol;

    @FXML
    private TableColumn<?, ?> blackDTCol;

    @FXML
    void userOutbtAction(ActionEvent event) {	// 강퇴 승인 버튼 눌렀을 때 이벤트 처리
    	boolean ok = alertConfirm(userTable.getSelectionModel().getSelectedItem().getUs_nm()+"님의 강퇴를 승인하시겠습니까??", "강퇴 승인");
    	int result = 0;
    	try {
    		if(ok == true) {	// 강퇴 승인했을때 처리할 데이터
    			String userId = userTable.getSelectionModel().getSelectedItem().getUs_id();
    			result = adminMainService.delUser(userId);
    			if(result>0) {
    				alert(userTable.getSelectionModel().getSelectedItem().getUs_id()+"님이 강퇴 되었습니다.", "승인 성공");
    			}else {
    				alert("승인 실패 하였습니다. 다시 시도해 주세요", "승인 실패");
    			}
    		}
		} catch (IOException e) {}
    	userOutbt.setDisable(true);		// alert창을 실행 후 다시 버튼 비활성화
    	
    	// user리스트 리셋 
    
        try {
        	userVoList.setAll(adminMainService.userInfoTable());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
        
        // tableview에 데이터 세팅
        userTable.setItems(userVoList);

    }

    @FXML
    void reportOkbtAction(ActionEvent event) {	// 신고 승인 버튼 눌렀을 때 이벤트 처리
    	
    	boolean ok;
    	int resultUp = 0;
    	int resultDel = 0; 
    	int resultBlack = 0;
    	
    	List<String> ifBlack = new ArrayList<>();
    	// user경고 횟수가 2회인 경우인 id를 list에 담음
    	for (int i = 0; i < userVoList.size(); i++) {	
			if(userVoList.get(i).getUs_rt_cn()>=2) {	
				ifBlack.add(userVoList.get(i).getUs_id());
			}
		}
    	
    	// 예비 블랙리스트와 신고당한 id중 일치하는 id가 있는지 확인
    	int tmp = 0;
    	
    	for (int i = 0; i < ifBlack.size(); i++) {
			if(ifBlack.get(i).toString().equals(reportTable.getSelectionModel().getSelectedItem().getRe_us_id().toString())){
				tmp++;
			}
		}
    	
    	// 일치하는 아이디의 경우 알림창 띄움
    	try {
	    	if(tmp!=0) {
	    		ok = alertConfirm(reportTable.getSelectionModel().getSelectedItem().getRe_us_id()+"님은 이미 신고를 2번 받았습니다.\n"
						+ "이 신고를 승인하시면 블랙리스트로 추가됩니다.\n계속 진행하시겠습니까?", "신고 승인");
		    		if(ok==true) {
						
		    			String userId = reportTable.getSelectionModel().getSelectedItem().getRe_us_id();
		    			String reprotId = reportTable.getSelectionModel().getSelectedItem().getRe_id();
		    			
		    			resultUp = adminMainService.reportUser(userId);		// 회원테이블update결과
						resultDel = adminMainService.reportDel(reprotId);	// 신고테이블delete결과
						resultBlack = adminMainService.bListUser(reportTable.getSelectionModel().getSelectedItem().getRe_us_id());		//블랙리스트insert결과
						
						}else {
							reportOkbt.setDisable(true);
							return;
						}
		    			
		    			if(resultUp>0 && resultDel>0 && resultBlack>0) {
		    				alert(reportTable.getSelectionModel().getSelectedItem().getRe_us_id()+"님이 신고승인 되었습니다.", "신고 승인 성공");
		    			}else {
		    				alert("승인 실패 하였습니다. \n다시 시도해 주세요", "승인 실패");
		    			}
	    	}else{
	    		ok = alertConfirm(reportTable.getSelectionModel().getSelectedItem().getRe_us_id()+"님을 신고 승인하시겠습니까??", "신고 승인");
	    		String userId = reportTable.getSelectionModel().getSelectedItem().getRe_us_id();
				String reprotId = reportTable.getSelectionModel().getSelectedItem().getRe_id();
				
				resultUp = adminMainService.reportUser(userId);		// 회원테이블update결과
				resultDel = adminMainService.reportDel(reprotId);	// 신고테이블delete결과
				
				if(resultUp>0 && resultDel>0) {
					alert(reportTable.getSelectionModel().getSelectedItem().getRe_us_id()+"님이 신고승인 되었습니다.", "신고 승인 성공");
				}else {
					alert("승인 실패 하였습니다. \n다시 시도해 주세요", "승인 실패");
				}
	    	}
    	} catch (RemoteException e) {
    		e.printStackTrace();
    	}	

    	reportOkbt.setDisable(true);		// alert창을 실행 후 다시 버튼 비활성화
    	
    	//신고테이블 리셋
    	try {
			userVoList.setAll(adminMainService.userInfoTable());
			reportVoList.setAll(adminMainService.reportInfoTable());
			blackVoList.setAll(adminMainService.blackInfoTable());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
        
        // tableview에 데이터 세팅
        userTable.setItems(userVoList);
        reportTable.setItems(reportVoList);
        blackTable.setItems(blackVoList);
        
    	
    }

    @FXML
    void selectUserInfo(MouseEvent event) {		// 회원테이블 행을 눌렀을 때 이벤트 처리
    	String id = userTable.getSelectionModel().getSelectedItem().getUs_id();
    	if(id!=null) {userOutbt.setDisable(false);}
    }

    @FXML
    void selectReportInfo(MouseEvent event) {	// 신고회원테이블 행을 눌렀을 때 이벤트 처리
    	String id = reportTable.getSelectionModel().getSelectedItem().getRe_us_id();
    	if(id!=null) {reportOkbt.setDisable(false);}
    }
    

    
    UserManageServiceInf adminMainService;
    ObservableList<UserVO> userVoList = FXCollections.observableArrayList();
    ObservableList<ReportVO> reportVoList = FXCollections.observableArrayList();
    ObservableList<BlackListVO> blackVoList = FXCollections.observableArrayList();
    
    
    @FXML
    void initialize() {		// 초기화
        assert userOutbt != null : "fx:id=\"userOutbt\" was not injected: check your FXML file 'AdminUserManage.fxml'.";
        assert reportOkbt != null : "fx:id=\"reportOkbt\" was not injected: check your FXML file 'AdminUserManage.fxml'.";


        // service객체 생성
        try {
        	Registry reg = LocateRegistry.getRegistry(localhost, 9988);
        	adminMainService = (UserManageServiceInf) reg.lookup("adminMain");
        } catch (RemoteException e) {
        	e.printStackTrace();
        } catch (NotBoundException e) {
        	e.printStackTrace();
        }
        
        try {
			userVoList.addAll(adminMainService.userInfoTable());
			reportVoList.addAll(adminMainService.reportInfoTable());
			blackVoList.addAll(adminMainService.blackInfoTable());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
        
        // 테이블 컬럼별 셋팅
        userIDCol.setCellValueFactory(new PropertyValueFactory<>("us_id"));
        userNMCol.setCellValueFactory(new PropertyValueFactory<>("us_nm"));
        userGnCol.setCellValueFactory(new PropertyValueFactory<>("us_gn"));
        userBirCol.setCellValueFactory(new PropertyValueFactory<>("us_bir"));
        userPhCol.setCellValueFactory(new PropertyValueFactory<>("us_ph"));
        userMailCol.setCellValueFactory(new PropertyValueFactory<>("us_mail"));
        userReportCol.setCellValueFactory(new PropertyValueFactory<>("us_rt_cn"));
        userScsCol.setCellValueFactory(new PropertyValueFactory<>("us_scs"));
        
        reportIDCol.setCellValueFactory(new PropertyValueFactory<>("re_id"));
        reportUserIDCol.setCellValueFactory(new PropertyValueFactory<>("re_us_id"));
        reportTeamIDCol.setCellValueFactory(new PropertyValueFactory<>("re_tm_id"));
        reportDTCol.setCellValueFactory(new PropertyValueFactory<>("re_elm"));
        reportWhyCol.setCellValueFactory(new PropertyValueFactory<>("re_cs"));

        blackIDCol.setCellValueFactory(new PropertyValueFactory<>("blist_id"));
        blackUserIDCol.setCellValueFactory(new PropertyValueFactory<>("blist_us_id"));
        blackDTCol.setCellValueFactory(new PropertyValueFactory<>("blist_elm_dt"));
        
        
        // tableview에 데이터 세팅
        userTable.setItems(userVoList);
        reportTable.setItems(reportVoList);
        blackTable.setItems(blackVoList);

        
    }
    
    public void alert(String msg, String title) {
    	Alert alertWarning = new Alert(AlertType.INFORMATION);
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
    
    
}
