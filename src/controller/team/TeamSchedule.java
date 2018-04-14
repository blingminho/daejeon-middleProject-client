package controller.team;

import java.io.IOException;
import java.net.URL;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
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
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import service.team.TeamCalendarServiceInf;
import service.team.TeamMenuServiceInf;
import service.team.TeamTravelServiceInf;
import vo.CalendarVO;
import vo.ScheduleVO;
import vo.TeamVO;
import vo.TravelListVO;

/**
 * 일정관리 페이지
 * @author Jun
 *
 */
public class TeamSchedule {
	String localhost = LoginPage.getLocalhost();
	// 서비스 설정
	private TeamTravelServiceInf serviceTravel;
	private TeamMenuServiceInf serviceMenu;
	private TeamCalendarServiceInf service;
	
	// 오른쪽테이블의 선택한 값을 저장할 변수
	private ScheduleVO scheduleSelected;
	
	
	private Calendar calendar;
	private int year;
	private int month;
	
	private String teamId;
	private String userId;
	private String selectedItem;// 메뉴에서 선택한 이름
	private List<TeamVO> teamList;
	private List<TravelListVO> travelList;
	private List<ScheduleVO> rigthList;
	private List<List<CalendarVO>> teamCalendarList;// 0번째 팀의 일정목록
	private ObservableList<ScheduleVO> rightObList = FXCollections.observableArrayList();
	private List<Background> backgroundList = new ArrayList<>();
	
	private List<VBox> vbox_1;
	private List<VBox> vbox_2;
	private List<VBox> vbox_3;
	private List<VBox> vbox_4;
	private List<VBox> vbox_5;
	private List<VBox> vbox_6;
	
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Label labelTravelList;
    @FXML
    private Label labelMonth;
    @FXML
    private TableView<ScheduleVO> tableViewRight;
    @FXML
    private TableColumn<?, ?> colTitleRight;
    @FXML
    private TableColumn<?, ?> colColor;
    @FXML
    private TableColumn<?, ?> colTeamNM;
    @FXML
    private VBox v_1_1;
    @FXML
    private VBox v_1_2;
    @FXML
    private VBox v_1_3;
    @FXML
    private VBox v_1_4;
    @FXML
    private VBox v_1_5;
    @FXML
    private VBox v_1_6;
    @FXML
    private VBox v_1_7;
    @FXML
    private VBox v_2_1;
    @FXML
    private VBox v_2_2;
    @FXML
    private VBox v_2_3;
    @FXML
    private VBox v_2_4;
    @FXML
    private VBox v_2_5;
    @FXML
    private VBox v_2_6;
    @FXML
    private VBox v_2_7;
    @FXML
    private VBox v_3_1;
    @FXML
    private VBox v_3_2;
    @FXML
    private VBox v_3_3;
    @FXML
    private VBox v_3_4;
    @FXML
    private VBox v_3_5;
    @FXML
    private VBox v_3_6;
    @FXML
    private VBox v_3_7;
    @FXML
    private VBox v_4_1;
    @FXML
    private VBox v_4_2;
    @FXML
    private VBox v_4_3;
    @FXML
    private VBox v_4_4;
    @FXML
    private VBox v_4_5;
    @FXML
    private VBox v_4_6;
    @FXML
    private VBox v_4_7;
    @FXML
    private VBox v_5_1;
    @FXML
    private VBox v_5_2;
    @FXML
    private VBox v_5_3;
    @FXML
    private VBox v_5_4;
    @FXML
    private VBox v_5_5;
    @FXML
    private VBox v_5_6;
    @FXML
    private VBox v_5_7;
    @FXML
    private VBox v_6_1;
    @FXML
    private VBox v_6_2;
    @FXML
    private VBox v_6_3;
    @FXML
    private VBox v_6_4;
    @FXML
    private VBox v_6_5;
    @FXML
    private VBox v_6_6;
    @FXML
    private VBox v_6_7;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnRm;
    @FXML
    private DatePicker datePicker;
    
    @FXML
    void btnAddAction(ActionEvent event) {
    	// DatePicker를 이용하여 날짜를 고르고 추가하기를 누르면 일정에 추가하기
    	if (datePicker.getValue() == null) {
			return;
		}
    	String date = datePicker.getValue().format(DateTimeFormatter.BASIC_ISO_DATE);
    	ScheduleVO scheduleVO = scheduleSelected;
    	
    	CalendarVO calendarVO = new CalendarVO();
    	calendarVO.setCal_tm_id(scheduleVO.getTm_id());
    	calendarVO.setCal_trv_id(scheduleVO.getTrv_id());
    	calendarVO.setCal_trv_nm(scheduleVO.getTrv_nm());
    	calendarVO.setCal_app_dt(date);
    	
    	try {
			boolean flag = service.insertCalendar(calendarVO);
			if (flag) {
				// 팀별로 일정리스트를 가진 리스트 초기화
		    	teamCalendarListSetting();
		    	
		    	// 달력 초기화
		    	calendarBasicSetting();
				result(year, month);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    }


    // 해당 행의 여행지를 여행지즐겨찾기에서 제거함
    @FXML
    void btnRemoveAction(ActionEvent event) {
    	if (scheduleSelected == null) {
			return;
		}
    	
    	int cnt = 0;
		try {
			cnt = serviceTravel.getTravelOnSchedule(teamId, scheduleSelected.getTrv_id());
			System.out.println("cnt : " + cnt);
			System.out.println("teamId : " + teamId);
			System.out.println("getTrv_id : " + scheduleSelected.getTrv_id());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		// 일정에 해당 여행지가 올라가있는 경우
		if (cnt > 0) {
			alertConfirm("일정과 여행지즐겨찾기 항목이 지워집니다. 지우시겠습니까?", "일정 연쇄 삭제 경고!");
		} else {
			deleteFromTravelList();
		}
		
		
		// 팀별로 일정리스트를 가진 리스트 초기화
    	teamCalendarListSetting();
    	
    	// 달력 초기화
    	calendarBasicSetting();
		result(year, month);
    }

    // 이전 달
    @FXML
    void btnLeft(ActionEvent event) {
    	month -= 1;
    	if (month == 0) {
    		year -= 1;
    		month = 12;
		}
    	calendar.set(year, month-1, 1);
    	calendarBasicSetting();
    	result(year, month);
    }
    
    // 다음 달
    @FXML
    void btnRight(ActionEvent event) {
    	month += 1;
    	if (month == 13) {
    		year += 1;
    		month = 1;
		}
    	calendar.set(year, month-1, 1);
    	calendarBasicSetting();
    	result(year, month);
    }

    /**
     * 테이블 클릭시
     * 두번 클릭시 해당하는 정보 조회(팀 홈페이지(이동) or 여행지 상세페이지(팝업창))
     * 한번 클릭(항목기억), 두번 클릭(해당 상세내용 팝업창)
     * @param 한번 클릭 또는 두번 클릭
     */
    @FXML
    void tableViewRightClicked(MouseEvent event) {
    	if(event.getClickCount() == 2) {
    		Stage popUpStage = new Stage();
    		if (selectedItem.equals("TeamSchedule")) {// 팀 일정인 경우
    			try {
    				TeamTravelPopup.setSelectedContentId(scheduleSelected.getTrv_id());
    				Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../../view/team/TeamTravelPopup.fxml")));
    				popUpStage.setTitle("여행지 상세 프로필");
    				popUpStage.setScene(scene);
    				popUpStage.show();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
			} else if (selectedItem.equals("MySchedule")) {// 내 일정인 경우
				// 해당 팀의 홈페이지로 이동
		    	MenuPage.setTeamId(scheduleSelected.getTm_id());
		    	MenuPage.setSeleted("TeamHome");
				Node myTeamList;
				try {
					myTeamList = FXMLLoader.load(this.getClass().getResource("../../view/team/Team.fxml"));
					MenuPage.getMenuPane().getChildren().setAll(myTeamList);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	    	
	    } else if(event.getClickCount() == 1) {
	    	scheduleSelected = tableViewRight.getSelectionModel().getSelectedItem();
	    }
    }

    @FXML
    void initialize() {
    	teamId = MenuPage.getTeamId();
    	selectedItem = MenuPage.getSelected();
    	userId = MenuPage.getUserId();
    	
		
    	// service와 연결
        try {
        	Registry reg = LocateRegistry.getRegistry(localhost, 9988);
        	service = (TeamCalendarServiceInf) reg.lookup("TeamCalendar");
        	serviceTravel = (TeamTravelServiceInf) reg.lookup("TeamTravelList");
        	serviceMenu = (TeamMenuServiceInf) reg.lookup("TeamMenu");
        } catch (RemoteException e) {
        	e.printStackTrace();
        } catch (NotBoundException e) {
        	e.printStackTrace();
        }
    	
    	// 페이지 셋팅
    	String pageResult = pageSetting();
    	
    	// 컬러 셋팅
    	colorLableSetting();
    	
    	// 오른쪽 테이블 셋팅
    	rightTableDataSetting(pageResult);
    	rightTableColSetting(pageResult);
    	tableViewRight.getItems().setAll(rightObList);
    	
    	
    	// 날짜 셋팅
    	calendar = Calendar.getInstance(Locale.KOREA);
    	year = calendar.get(Calendar.YEAR);
    	month = calendar.get(Calendar.MONTH) + 1;
    	
    	// 달력 리스트 초기화
    	listSetting();
    	
    	// 팀별로 일정리스트를 가진 리스트 초기화
    	teamCalendarListSetting();
    	
    	// 달력 초기화
    	calendarBasicSetting();
    	result(year, month);
    	
//    	reflesh();
    	
    }
    
    /**
     * 년과 달을 표시하는 Label 초기화
     */
    private void calendarBasicSetting() {
    	labelMonth.setText(String.valueOf(year) + "년 "+ String.valueOf(month) + "월");
    }
    
    /**
     * teamCalendarList를 초기화하는 메서드
     */
    private void teamCalendarListSetting() {
    	teamCalendarList = new ArrayList<>();
    	// 팀 목록을 반복문을 돌림
    	for (TeamVO teamVO : teamList) {
    		String tm_id = teamVO.getTm_id();

    		// 서비스에서 팀아이디를 이용하여 해당 팀의 일정을 가져옴
    		List<CalendarVO> teamCalendar = null;
			try {
				teamCalendar = service.getCalendarBy_TM_ID(tm_id);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			// 팀 일정 리스트에 추가
    		teamCalendarList.add(teamCalendar);
		}
    }
    
    
    /**
     * 해당 달에 해당하는 일정을 가져와 해당 일에 저장
     * teamCalendarList(팀별 일정리스트)을 사용
     * @param month 해당하는 달
     * @param day 해당하는 일
     * @param vbox 해당일에 주어진 vbox
     */
    private void calendarDataSetting(int month, int day, VBox vbox) {
    	for (List<CalendarVO> listCalendarVO : teamCalendarList) {
    		for (CalendarVO calendarVO : listCalendarVO) {
				String cal_app_dt = calendarVO.getCal_app_dt();// 일정 지정 날짜
				int cal_year = Integer.valueOf(cal_app_dt.substring(0, 4));
				int cal_month = Integer.valueOf(cal_app_dt.substring(5, 7));
				int cal_day = Integer.valueOf(cal_app_dt.substring(8, 10));
				
				// 일정 지정 날짜가 해당 달의 해당 일인 경우 vbox에 추가함
				if (year == cal_year && month == cal_month && day == cal_day) {
					String cal_tm_id= calendarVO.getCal_tm_id();// 일정의 팀 아이디
					Background backColor = null;
					// 팀별 색깔리스트의 index 정해주는 메서드
					for (ScheduleVO scheduleVO : rigthList) {
						String sche_tm_id = scheduleVO.getTm_id();
						if (sche_tm_id.equals(cal_tm_id)) {
							backColor = scheduleVO.getColor().getBackground();
							break;
						}
					}
					
					String cal_trv_id= calendarVO.getCal_trv_id();// 일정의 여행지 아이디
					String cal_trv_nm= calendarVO.getCal_trv_nm();// 일정의 여행지 이름
					// 라벨 셋팅
					Label label  = new Label(cal_trv_nm);
	    			label.setBackground(backColor);// 컬러 셋팅
	    			label.setOnMouseClicked(new EventHandler<Event>() {
	    				@Override
	    				public void handle(Event event) {
	    					Stage popUpStage = new Stage();
	    					try {
	    						TeamTravelPopup.setSelectedContentId(cal_trv_id);
	    						Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../../view/team/TeamTravelPopup.fxml")));
	    						popUpStage.setTitle("여행지 상세 프로필");
	    						popUpStage.setScene(scene);
	    						popUpStage.show();
	    					} catch (IOException e) {
	    						e.printStackTrace();
	    					}
	    				}
	    			});
	    			
					vbox.getChildren().add(label);
				}
				
			}
		}
    }
    
    
    /**
     * 내 일정 or 팀 일정관리 구분
     * 팀 일정에서 팀장인 경우와 아닌경우 구분
     */
    private String pageSetting() {
    	selectedItem = MenuPage.getSelected();
    	
    	if (selectedItem.equals("MySchedule")) {// 내 일정인 경우
    		labelTravelList.setText("팀 목록");
    		colTitleRight.setVisible(false);
    		btnAdd.setVisible(false);
    		btnRm.setVisible(false);
    		datePicker.setVisible(false);
    		return "MySchedule";
        } else if (selectedItem.equals("TeamSchedule")) {// 팀페이지내에서 일정 관리인 경우
        	// 팀장이 아닌 경우 여행지 즐겨찾기 안보이게 하기(해당 팀의 팀장 가져오기)
        	String teamLeader = null;// 서비스에서 받기
        	try {
        		teamLeader = serviceMenu.getTeamLeaderId(teamId);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
        	
        	if (!userId.equals(teamLeader)) {// 팀장이 아닌 경우
        		datePicker.setVisible(false);
    			labelTravelList.setVisible(false);
    			btnAdd.setVisible(false);
    			btnRm.setVisible(false);
    			tableViewRight.setVisible(false);
    			return "TeamUser";
    		} else {// 팀장인 경우
    			colTeamNM.setVisible(false);
    			colColor.setVisible(false);
    			return "TeamLeader";
    		}
		}
		return null;
    }
    
    /**
     * 페이지에 따른 오른쪽 테이블 데이터 불러오기<br>
     * 내일정 : MySchedule<br>
     * 팀리더 : TeamLeader<br>
     * 팀페이지 : TeamUser <br>
     * @param pageSetting 메서드의 결과값
     */
    private void rightTableDataSetting(String pageResult) {
    	if (pageResult.equals("MySchedule")) {
			// 내가 속한 팀아이디 목록 불러옴
    		List<String> teamIdList = null;
    		try {
    			teamIdList = service.getJoinTeamIdList(userId);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
    		
    		// 내 아이디를 보내고 서비스에서 내가 가입된 팀만 있는 팀목록;
    		List<TeamVO> localTeamList = new ArrayList<>();
    		try {
    			for (String teamId : teamIdList) {
    				localTeamList.add(service.getTeamVO(teamId));
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
    		
    		// 오른쪽 테이블에 들어갈 데이터 리스트
    		List<ScheduleVO> localRightList = new ArrayList<>();
    		
    		int i = 0;
    		for (TeamVO teamVO : localTeamList) {
    			ScheduleVO vo = new ScheduleVO();
    			vo.setTm_id(teamVO.getTm_id());
    			vo.setTm_nm(teamVO.getTm_nm());
    			Label label = new Label("                        ");
    			label.setBackground(backgroundList.get(i));
    			vo.setColor(label);
    			localRightList.add(vo);
    			i++;
			}
    		teamList = localTeamList;
    		rigthList = localRightList;
    		rightObList.setAll(rigthList);
    		
		} else {
			reflesh();
		}
    }
    
    /**
     * 오른쪽 테이블 컬럼 설정 및 데이터 초기화
     * @param pageSetting 메서드의 결과값
     */
    private void rightTableColSetting(String pageResult) {
    	if (pageResult.equals("MySchedule")) {
    		colTeamNM.setCellValueFactory(new PropertyValueFactory<>("tm_nm"));
            colColor.setCellValueFactory(new PropertyValueFactory<>("color"));
            
		} else if (pageResult.equals("TeamLeader")) {
			colTitleRight.setCellValueFactory(new PropertyValueFactory<>("trv_nm"));
		}
    }
    
    /**
     * 라벨에 넣을 백그라운드 색상을 설정
     * backgroundList에 담음
     */
    private void colorLableSetting() {
    	for (int i = 0; i < 50; i++) {
    		int red = (int)(Math.random() * 254) + 1;
    		int green = (int)(Math.random() * 254) + 1;
    		int blue = (int)(Math.random() * 254) + 1;
    		Background back = new Background(new BackgroundFill(Color.rgb(red, green, blue), CornerRadii.EMPTY, Insets.EMPTY));
    		backgroundList.add(back);
		}
    }
    
    /**
     * Vbox리스트 만들고 초기화하는 메서드
     */
    private void listSetting() {
    	vbox_1 = new ArrayList<>();
    	vbox_2 = new ArrayList<>();
    	vbox_3 = new ArrayList<>();
    	vbox_4 = new ArrayList<>();
    	vbox_5 = new ArrayList<>();
    	vbox_6 = new ArrayList<>();
    	vbox_1.add(v_1_1);
    	vbox_1.add(v_1_2);
    	vbox_1.add(v_1_3);
    	vbox_1.add(v_1_4);
    	vbox_1.add(v_1_5);
    	vbox_1.add(v_1_6);
    	vbox_1.add(v_1_7);
    	
    	vbox_2.add(v_2_1);
    	vbox_2.add(v_2_2);
    	vbox_2.add(v_2_3);
    	vbox_2.add(v_2_4);
    	vbox_2.add(v_2_5);
    	vbox_2.add(v_2_6);
    	vbox_2.add(v_2_7);
    	vbox_3.add(v_3_1);
    	vbox_3.add(v_3_2);
    	vbox_3.add(v_3_3);
    	vbox_3.add(v_3_4);
    	vbox_3.add(v_3_5);
    	vbox_3.add(v_3_6);
    	vbox_3.add(v_3_7);
    	vbox_4.add(v_4_1);
    	vbox_4.add(v_4_2);
    	vbox_4.add(v_4_3);
    	vbox_4.add(v_4_4);
    	vbox_4.add(v_4_5);
    	vbox_4.add(v_4_6);
    	vbox_4.add(v_4_7);
    	vbox_5.add(v_5_1);
    	vbox_5.add(v_5_2);
    	vbox_5.add(v_5_3);
    	vbox_5.add(v_5_4);
    	vbox_5.add(v_5_5);
    	vbox_5.add(v_5_6);
    	vbox_5.add(v_5_7);
    	vbox_6.add(v_6_1);
    	vbox_6.add(v_6_2);
    	vbox_6.add(v_6_3);
    	vbox_6.add(v_6_4);
    	vbox_6.add(v_6_5);
    	vbox_6.add(v_6_6);
    	vbox_6.add(v_6_7);
    }
    
 	/**
 	 * 달력 만들어 주는 메서드
 	 * @param 연도
 	 * @param 달
 	 * @param 1주
 	 *            Vbox 리스트
 	 * @param 2주
 	 *            Vbox 리스트
 	 * @param 3주
 	 *            Vbox 리스트
 	 * @param 4주
 	 *            Vbox 리스트
 	 * @param 5주
 	 *            Vbox 리스트
 	 * @param 6주
 	 *            Vbox 리스트
 	 * @return 파라미터 리스트를 초기화 후 리스트에 담아서 반환
 	 */
 	private List<List<VBox>> result(int nYear, int mth) {
 		int i, j; // 카운트를 위한 변수입니다.
 		int d = 0;
 		int year = 0;
 		int month;
 		int sum = 0;

 		List<List<VBox>> resultList = new ArrayList<>();// 결과 리스트 선언

 		int dy = count_leap(base_year - nYear); // dy는 기준연도부터 현재연도까지 낀 윤년의 갯수입니다.
 		convert_to_day(nYear); // ★ 우선 기준연도부터 현재 연도까지 년 단위로 총일수를 구합니다. ★

 		int day; // 이변수는 요일을 결정하기위해 존재합니다.예를들어 기준일부터 현재일까지 차이가 7이고
 		// 기준일이 월요일이면 7로 나눠서 나머지가 0이되니까 월요일임을 알수 있듯이
 		// day는 숫자로서 요일을 결정할수있습니다.
 		if (nYear >= base_year) {

 			if (is_leap_year(nYear) == 1) // 윤달이 낀날의 2월은 하루 증가
 				month_table[1] = 29;

 			for (i = 0; i < (mth - base_month); i++)
 				total_sum += month_table[i];
 			// 위에 ★ 에서 기준연도부터 현재연도까지의 일수를 구했습니다.
 			// 이 for루프를 통해 나머지 기준월부터 현재월까지의 총일수를 구합니다.
 			// 즉 이루프를 통해 기준 연도와 월부터 현재 연도와 월까지의 총일수를 구함.

 			day = (total_sum + 2) % 7;
 			// 현재까지의 총일수를 7의 나머지로 연산해줍니다. 2를 더해준 이유는 1980년도 1월 1일 = 화요일

 			month = total_to_month(total_sum); // 입력받은 해당 날짜의 정확한 달을 구해서 저장합니다.

 			// 결과 리스트 초기화
 			resultList.add(vbox_1);
 			resultList.add(vbox_2);
 			resultList.add(vbox_3);
 			resultList.add(vbox_4);
 			resultList.add(vbox_5);
 			resultList.add(vbox_6);
 			for (List<VBox> list : resultList) {
 				for (VBox vBox : list) {
 					vBox.getChildren().clear();
 				}
 			}

 			int gab = day;// 해당 달의 첫날을 받기
 			int line = 0;// 주를 의미함
 			for (j = 1; j <= month_table[month - 1]; j++)
 			// month변수에서 1을 빼준이유는 배열은 0부터 시작하기때문입니다.
 			{
 				VBox vbox = resultList.get(line).get(gab);
 				vbox.getChildren().add(0, new Label(j + ""));
 				
 				// 해당 날짜에 맞는 여행지 넣기
 				calendarDataSetting(month, j, vbox);
 				gab++;
 				
 				// 그리고 처음 요일을 출력하기위한 공백만큼 계산해서 출력
 				if (((j + day) % 7) == 0) {
 					line++;
 					gab = 0;
 				}
 			}
 			month_table[1] = 28; // 윤년이었다면 다시 평년으로 바꾸어줍니다.
 		}
 		return resultList;
 	}

 	
 	// 미리 기준 연도와 , 기준월을 정해서 출력하는 방법을 선택함.
 	private int base_year = 1980; // 기준 연도
 	private int base_month = 1; // 기준 월 입니다. 실제로는 1980년 1월 1을 기준으로 계산
 	private int total_sum = 0; // 기준 년과 월에서 입력받은 날짜까지 총 일수를 여기에 저장

 	private int[] month_table = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 }; // 각 월마다 총 일수

 	private int is_leap_year(int n) { // 윤년이 있는 년도를 조사하는 함수입니다. 윤년인경우는 1

 		if (n % 4 == 0) { // 4의 배수는 윤년

 			if (n % 100 == 0) // 그런데 100의 배수는 윤년이 아님
 			{
 				if (n % 400 == 0) // 그중에서 400의 배수는 윤년
 					return 1;

 				return 0;
 			}

 			return 1;
 		}

 		else
 			return 0;

 	}

 	private int total_to_month(int total) // 기준 날짜부터 입력받은 날짜까지 총 일수를 구해서 반환,
 	{
 		boolean CHK = false; // 무한 루프를 돌리기위한 불리안 값입니다.
 		int i = 0; // i변수는 월입니다.
 		int cnt = 0; // 총 일수를 월로 바꾸어서 cnt 저장시킵니다.
 		int chk_leap_year = base_year; // 총 일수를 월로 바꾸려면 윤년이 있는 날을 고려해야합니다.
 		// 기준연도부터 시작해서 윤년을 조사합니다.

 		while (CHK != true) {
 			if (is_leap_year(chk_leap_year) == 1) {// 만약 지금 연도가 윤년이라면
 				month_table[1] = 29; // 2월달은 하루가 더 늘어납니다.
 			}
 			if (total >= month_table[i]) // 총 일수가 month_table[]배열의 총일수 보다 작다면,
 			{
 				total -= month_table[i++]; // 총일수를 month_table 배열의 현재 월의 일수로 빼줍니다.
 				cnt++; // 그리고 월이 증가합니다.
 				if (i == 12) // 만약 12월이된다면 계절이 변해 다시 제자리로 오는것처럼
 				{
 					i -= 12; // 다시 12를빼서 0으로 만들어줍니다.
 					chk_leap_year++; // 그리고 12개월이 지났으니 연도도 증가시켜줘야함
 				}
 				month_table[1] = 28; // 윤년을 평년의 해로 바꾸어주어야합니다.
 			}
 			else {
 				break;
 			}
 		}
 		cnt %= 12; // 위의 무한루프를 통해 총일수를 총월수로 계산됨, 이제 12 나머지 연산을 해주면
 		// 몇년도 몇월이라는 값으로 바꿀수있습니다.
 		return (cnt + 1); // 그리고 바꾼 월을 반환
 	}

 	private int count_leap(int n) // 기준 연도부터 시작해서 입력받은 연도까지의 윤년이 있는날을 셉니다.
 	{
 		int i; // 기준연도를 저장합니다.
 		int cnt = 0; // 윤년의 개수입니다.

 		for (i = base_year; i < (base_year - n); i++)
 		// i(기준연도) 부터 입력받은 연도까지 i를 증가시키며 윤년이낀 갯수를 구함.
 		{

 			if (is_leap_year(i) == 1) // 위에서 구현한 윤년인지를 판단하는 함수를 사용
 			{
 				cnt++; // 윤년이라면 총 윤년의 개수를 증가시킵니다.
 			}

 		}

 		return cnt; // 카운트한 윤년의 갯수를 리턴합니다.

 	}

 	private void convert_to_day(int nYear) { // 기준 연도부터 입력받은 연도까지 총일수를 구해서 리턴합니다.
 		total_sum = ((nYear - base_year) * 365) + count_leap((base_year - nYear));
 	}

 	
 	/**
 	 * 확인창 띄우기 - 여행지 삭제시 사용
 	 * @param msg
 	 * @param title
 	 */
 	public void alertConfirm(String msg, String title) {
    	Alert alertConfirm = new Alert(AlertType.CONFIRMATION);
    	alertConfirm.setTitle(title);
    	alertConfirm.setContentText(msg);
    	alertConfirm.showAndWait();
    	if (alertConfirm.getResult() == ButtonType.OK) {
    		deleteFromTravelList();
    	}
    }
 	
 	/**
 	 * 일정에서 해당 날짜의 해당 여행지 아이디가 일치하는 일정을 제거
 	 */
 	private void deleteFromTravelList() {
    	try {
    		// DB에서 해당 값을 제거
			serviceTravel.deleteTravel(scheduleSelected.getTrv_id());
			reflesh();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    }
 	
 	/**
     * 팀페이지 내의 일정관리
     * 오른쪽 테이블 새로고침
     */
    private void reflesh() {
    	teamList = new ArrayList<>();
    	// 스케쥴리스트 반환
    	List<ScheduleVO> localRightList = getSchedule(teamId);
		
		// 오른쪽 테이블 새로고침
		rigthList = localRightList;
		rightObList.setAll(rigthList);
		tableViewRight.getItems().setAll(rightObList);
		
		// 해당팀을 teamList에 넣음
		TeamVO teamVO = new TeamVO();
		teamVO.setTm_id(teamId);
		teamList.add(teamVO);
    }
    
    /**
     * 팀페이지 내의 일정관리
     * 해당 팀의 여행지즐겨찾기 목록을 스케쥴목록으로 반환
     * @param 팀아이디
     * @return 스케쥴리스트
     */
    private List<ScheduleVO> getSchedule(String tm_id) {
    	List<TravelListVO> localTravelList = null;
    	try {
			// 해당 팀의 여행지 즐겨찾기 목록 불러옴
			localTravelList = serviceTravel.getTravelList(tm_id);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		List<ScheduleVO> localRightList = new ArrayList<>();
		int i = 0;
		for (TravelListVO travelVO : localTravelList) {
			ScheduleVO vo = new ScheduleVO();
			vo.setTrv_id(travelVO.getTrv_id());
			vo.setTrv_nm(travelVO.getTrv_nm());
			vo.setTm_id(travelVO.getTrv_tm_id());
			Label label = new Label("                        ");
			label.setBackground(backgroundList.get(i));
			vo.setColor(label);
			localRightList.add(vo);
			i++;
		}
		// 여행지 목록 초기화
		travelList = localTravelList;
		
		return localRightList;
    }
    
    private void alert(String msg, String title) {
    	Alert alertWarning = new Alert(AlertType.WARNING);
    	alertWarning.setTitle(title);
    	alertWarning.setContentText(msg);
    	alertWarning.showAndWait();
    }
 	
}