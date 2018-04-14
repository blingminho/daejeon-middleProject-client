package controller.teamList;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import controller.main.LoginPage;
import controller.main.MenuPage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import service.teamList.TeamListServiceInf;
import vo.TeamListVO;
import vo.TeamVO;

public class TeamList {
	String localhost = LoginPage.getLocalhost();
	// 서비스 설정
	TeamListServiceInf service;
	
	// 테이블 행 수
	private int limit = 12;
	
	// 팀 리스트
	private List<TeamListVO> teamTableList = new ArrayList<>();
	
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Pagination tmListPagination;
    @FXML
    private TableView<TeamListVO> tableTmList;
    @FXML
    private TableColumn<?, ?> colNo;
    @FXML
    private TableColumn<?, ?> colNm;
    @FXML
    private TableColumn<?, ?> colPn;
    @FXML
    private TableColumn<?, ?> colSt;
    @FXML
    private TableColumn<?, ?> colDt;
    @FXML
    private TableColumn<?, ?> colPf;

    @FXML
    void tableTmClicked(MouseEvent event) {
    	if (tableTmList.getSelectionModel().getSelectedItem() == null) {
			return;
		}
    	
    	// 해당 팀의 홈페이지로 이동
    	String selectedTmID = tableTmList.getSelectionModel().getSelectedItem().getTm_id();
    	MenuPage.setTeamId(selectedTmID);
    	MenuPage.setSeleted("TeamHome");
    	 
		Node myTeamList;
		try {
			myTeamList = FXMLLoader.load(this.getClass().getResource("../../view/team/Team.fxml"));
			MenuPage.getMenuPane().getChildren().setAll(myTeamList);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }

    @FXML
    void initialize() {
    	Registry reg;
		try {
			reg = LocateRegistry.getRegistry(localhost, 9988);
			service = (TeamListServiceInf) reg.lookup("TeamList");
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
    	
    	// 컬럼 설정
		colNo.setCellValueFactory(new PropertyValueFactory<>("tm_no"));
        colNm.setCellValueFactory(new PropertyValueFactory<>("tm_nm"));
        colSt.setCellValueFactory(new PropertyValueFactory<>("tm_rec_st_dt"));
        colDt.setCellValueFactory(new PropertyValueFactory<>("tm_rec_ed_dt"));
        colPf.setCellValueFactory(new PropertyValueFactory<>("tm_pf"));
        colPn.setCellValueFactory(new PropertyValueFactory<>("tm_pn"));
    	
        
        reflesh();
    	
        // 페이지네이션 클릭시 진행되는 내부 메서드
	    tmListPagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
	        @Override
	        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
	        	changeTableView(newValue.intValue(), limit);
	        }
	    });
	    
	    init();
    }
    
    
    
    /**
     * 페이지네이션 초기화 메서드
     */
    public void init() {
        resetPage();
        tmListPagination.setCurrentPageIndex(0);
        changeTableView(0, limit);
    }

    /**
     * 페이지당 자료의 개수를 결정한다
     */
    public void resetPage() {
    	tmListPagination.setPageCount((int) (Math.ceil(teamTableList.size() * 1.0 / limit)));
    }
    
    /**
     * 클릭한 페이지에 맞추어 테이블 뷰를 변경한다
     * @param index
     * @param limit
     */
    public void changeTableView(int index, int limit) {
    	int newIndex = index * limit;
		List<TeamListVO> trans = teamTableList.subList(Math.min(newIndex, teamTableList.size()), Math.min(teamTableList.size(), newIndex + limit));
		ObservableList<TeamListVO> newList = FXCollections.observableArrayList(trans);
		tableTmList.setItems(newList);
    }
    
    /**
     * 팀 테이블에서 공개여부가 Y인 팀의 리스트를 가져온다
     */
    private void reflesh() {
    	List<TeamVO> list = null;
    	List<Map<String, String>> listPN = null;
    	try {
			list = service.getTeamList();
			listPN = service.getTeamPN();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    	
    	
    	int count = 1;
    	for (Map<String, String> map : listPN) {
			String tm_id = map.get("JO_TM_ID");
			String tm_pn =  String.valueOf(map.get("JO_PN"));
			
			for (TeamVO teamVO : list) {
				String list_tm_id = teamVO.getTm_id();
				
				if (tm_id.equals(list_tm_id)) {
		    		TeamListVO newVO = new TeamListVO();
		    		
		    		newVO.setTm_no(count++);
		    		newVO.setTm_id(teamVO.getTm_id());
		    		newVO.setTm_nm(teamVO.getTm_nm());
		    		newVO.setTm_pf(teamVO.getTm_pf());
		    		newVO.setTm_pn(tm_pn + "/" + teamVO.getTm_pn());
		    		newVO.setTm_rec_ed_dt(teamVO.getTm_rec_ed_dt().substring(0, 10));
		    		newVO.setTm_rec_st_dt(teamVO.getTm_rec_st_dt().substring(0, 10));
		    		
		    		teamTableList.add(newVO);
				}
			}
		}
    	
    	ObservableList<TeamListVO> obList = FXCollections.observableArrayList();
    	obList.addAll(teamTableList);
		tableTmList.setItems(obList);
		
    }
    
}
