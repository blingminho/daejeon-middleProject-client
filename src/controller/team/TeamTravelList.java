package controller.team;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import controller.main.LoginPage;
import controller.main.MenuPage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import service.team.TeamMenuServiceInf;
import service.team.TeamTravelServiceInf;
import vo.ParameterVO;
import vo.TravelListVO;

/**
 * 여행지 즐겨찾기 controller
 * TeamTravelList.fxml과 연결된 controller
 * @author Jun
 *
 */
public class TeamTravelList {
	String localhost = LoginPage.getLocalhost();
	// 서비스 설정
	private TeamTravelServiceInf service;
	// 팀 아이디
	private String teamId;
	// 팀 리더 아이디
	private String leaderId;
	// 유저 아이디
	private String userId;
	
	// api 설정
	// 공공데이터포털에서 받은 인증키
	private static final String ServiceKey = "EtSF91PcyVZu75%2Fiy%2BCYg19wTdh2k0dPenZOTEQOmzLs7j5qm84mUVav2qJY5cM8dLMQWg2%2BSRXpCFD9DENPbw%3D%3D";
	// 한 페이지 결과수
	private static final String numOfRows = "100";	
	// 파라미터 vo
	private ParameterVO apiParameterVO;
	
	// 파라미터 리스트
	private List<String> categoryList;
	private List<String> arrangeNameList;
	private List<String> arrangeCodeList;
	private List<String> contentTypeIdNameList;
	private List<String> contentTypeIdCodeList;
    private List<String> cat1NameList;// contentType이 관광지인 경우의 대분류 이름
    private List<String> cat1CodeList;// contentType이 관광지인 경우의 대분류 코드
    private List<String> cat2NameList;// contentType이 관광지인 경우의 중분류 이름
    private List<String> areaNameList;
    private List<String> areaCodeList;
	private List<String> sigunguNameList;
	private List<String> soogsoNameList;
	private List<String> soogsoCodeList;
	// 시군구코드
	private Map<String, String[]> sigunguCode_Array;
	// contentType이 관광지인 경우의 중분류 이름
	private Map<String, String[]> contentTypeId_12_cat2_Map;
	
	// contentType에 따른 대분류 및 중분류 code
	private String contentTypeId_14_cat1Code;
	private String contentTypeId_14_cat2Code;
	private String contentTypeId_32_cat1Code;
	private String contentTypeId_32_cat2Code;
	
	
	// 검색결과 리스트
	private List<TravelListVO> searchResultList;
	// 여행지 즐겨찾기 리스트
	private List<TravelListVO> travelList;
	// 테이블에 넣을 데이터 리스트
	ObservableList<TravelListVO> obListLeft = FXCollections.observableArrayList();
    ObservableList<TravelListVO> obListRight = FXCollections.observableArrayList();
	
	// 검색결과 항목을 저장할 vo
	private TravelListVO searchSelected;
	// 여행지 즐겨찾기 리스트 항목을 저장할 vo
	private TravelListVO travelSelected;
	
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private ComboBox<String> comboArrange;
    @FXML
    private ComboBox<String> comboContentTypeId;
    @FXML
    private ComboBox<String> comboCat1;
    @FXML
    private ComboBox<String> comboCat2;
    @FXML
    private ComboBox<String> comboAreaCode;
    @FXML
    private ComboBox<String> comboSigunguCode;
    @FXML
    private ComboBox<String> comboCategory;
    @FXML
    private ComboBox<String> comboSoogso;
    @FXML
    private TextField tfKeyword;
    @FXML
    private DatePicker DateEventStartDate;
    @FXML
    private DatePicker DateEventEndDate;
    
    @FXML
    private TableView<TravelListVO> tableViewLeft;
    @FXML
    private TableColumn<?, ?> colTypeLeft;
    @FXML
    private TableColumn<?, ?> colTitleLeft;
    @FXML
    private TableColumn<?, ?> colImageLeft;
    @FXML
    private TableView<TravelListVO> tableViewRight;
    @FXML
    private TableColumn<?, ?> colTypeRight;
    @FXML
    private TableColumn<?, ?> colTitleRight;
    @FXML
    private TableColumn<?, ?> colImageRight;
    @FXML
    private TableColumn<?, ?> colAddr1Right;
    @FXML
    private TableColumn<?, ?> colTelRight;
    @FXML
    private Button btnRm;
    @FXML
    private Label labelSearchResult;
    @FXML
    private Label labelTravelList;
    @FXML
    private Button btnSearchAdd;
    
    /**
     * 시도 콤보박스 선택에 맞는 시군구 콤보박스 데이터 셋팅
     * @param 시도 콤보박스 선택
     */
    @FXML
    void comboAreaCodeAction(ActionEvent event) {
    	int selectedIndex = comboAreaCode.getSelectionModel().getSelectedIndex();
    	String key = areaCodeList.get(selectedIndex);
    	
    	String[] sigungu = sigunguCode_Array.get(key);
    	List<String> sigunguList = new ArrayList<>();
    	for (String sigunguName : sigungu) {
    		sigunguList.add(sigunguName);
		}
    	
    	comboSigunguCode.getItems().setAll(sigunguList);
    	comboSigunguCode.getSelectionModel().selectFirst();
    }
    
    /**
     * contentType이 관광지인 경우 대분류 콤보박스 선택에 맞는 중분류 콤보박스 데이터 셋팅
     * @param 대분류 콤보박스 선택
     */
    @FXML
    void comboCat1Action(ActionEvent event) {
    	int selectedIndex = comboCat1.getSelectionModel().getSelectedIndex();
    	String key = cat1CodeList.get(selectedIndex);
    	
    	String[] cat2 = contentTypeId_12_cat2_Map.get(key);
    	List<String> cat2List = new ArrayList<>();
    	for (String cat2Name : cat2) {
    		cat2List.add(cat2Name);
		}
    	
    	comboCat2.getItems().setAll(cat2List);
    	comboCat2.getSelectionModel().selectFirst();
    }
    
    /**
     * 콘텐츠 타입에 따른 대분류, 중분류 콤보박스 disable 설정
     * @param 콘텐츠 타입 콤보박스 선택
     */
    @FXML
    void comboContentTypeIdAction(ActionEvent event) {
    	int selectedIndex = comboContentTypeId.getSelectionModel().getSelectedIndex();
    	String key = contentTypeIdCodeList.get(selectedIndex);
    	
    	switch (key) {
		case "12":// 관광지(12)
			comboCat1.setDisable(false);
			comboCat2.setDisable(false);
			break;
		case "14":// 문화시설(14)
			comboCat1.setDisable(true);
			comboCat2.setDisable(true);
			break;
		case "32":// 숙박(32)
			comboCat1.setDisable(true);
			comboCat2.setDisable(true);
			break;
		}
    }

    /**
     * 카테고리에 따른 콤보박스 disable 설정
     * @param 카테고리 콤보박스 선택
     */
    @FXML
    void comboCategoryAction(ActionEvent event) {
    	int key = comboCategory.getSelectionModel().getSelectedIndex();
    	switch (key) {
    	case 0:// 지역기반 검색
    		comboContentTypeId.setDisable(false);
    		comboCat1.setDisable(false);
    		comboCat2.setDisable(false);
    		comboSoogso.setDisable(true);
    		tfKeyword.setDisable(true);
    		DateEventStartDate.setDisable(true);
    		DateEventEndDate.setDisable(true);
    		comboContentTypeIdAction(null);
    		break;
    	case 1:// 키워드 검색
    		comboContentTypeId.setDisable(false);
    		comboCat1.setDisable(false);
    		comboCat2.setDisable(false);
    		comboSoogso.setDisable(true);
    		tfKeyword.setDisable(false);
    		DateEventStartDate.setDisable(true);
    		DateEventEndDate.setDisable(true);
    		comboContentTypeIdAction(null);
    		break;
    	case 2:// 행사정보 검색
    		comboContentTypeId.setDisable(true);
    		comboCat1.setDisable(true);
    		comboCat2.setDisable(true);
    		comboSoogso.setDisable(true);
    		tfKeyword.setDisable(true);
    		DateEventStartDate.setDisable(false);
    		DateEventEndDate.setDisable(false);
    		break;
    	case 3:// 숙박정보 검색
    		comboContentTypeId.setDisable(true);
    		comboCat1.setDisable(true);
    		comboCat2.setDisable(true);
    		comboSoogso.setDisable(false);
    		tfKeyword.setDisable(true);
    		DateEventStartDate.setDisable(true);
    		DateEventEndDate.setDisable(true);
    		break;
    	}
    }
    /**
     * 검색하기
     * @param 검색 버튼 클릭
     * @throws IOException
     */
    @FXML
    void btnSearchAction(ActionEvent event) throws IOException {
    	// 무조건 선택받는 콤보박스의 index값 가져옴
    	int selectedCategory = comboCategory.getSelectionModel().getSelectedIndex();
    	int selectedArrange = comboArrange.getSelectionModel().getSelectedIndex();
    	int selectedAreaCode = comboAreaCode.getSelectionModel().getSelectedIndex();
    	int selectedSigunguCode = comboSigunguCode.getSelectionModel().getSelectedIndex();
    	
    	// index값을 이용하여 api에 넣을 값 만듬
    	String service = null;
    	switch (selectedCategory) {
    	case 0:// 지역기반 검색
    		service = "areaBasedList";
    		break;
    	case 1:// 키워드 검색 조회
    		service = "searchKeyword";
    		break;
    	case 2:// 행사정보 조회
    		service = "searchFestival";
    		break;
    	case 3:// 숙박정보 조회
    		service = "searchStay";
    		break;
    	}
    	String arrange = arrangeCodeList.get(selectedArrange);
    	String areaCode = areaCodeList.get(selectedAreaCode);
    	String sigunguCode = (selectedSigunguCode + 1) + "";
    	
    	
    	//============================================================================
		//==URL을 이용한 연결 및 결과를 String으로 저장===============================
		//============================================================================
		// 요청 파라미터 및 파라미터에 대한 값 설정
    	String pageNo = "1";/*현재 페이지 번호*/
    	
    	
    	// URL 만듬
    	StringBuilder urlBuilder = new StringBuilder("http://api.visitkorea.or.kr/openapi/service/rest/KorService/"); /*URL*/
        urlBuilder.append(URLEncoder.encode(service, "UTF-8")); /*요청할 데이터*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + ServiceKey); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + URLEncoder.encode("SERVICE_KEY", "UTF-8")); /*공공데이터포털에서 받은 인증키*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode(numOfRows, "UTF-8")); /*한 페이지 결과수*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(pageNo, "UTF-8")); /*현재 페이지 번호*/
        urlBuilder.append("&" + URLEncoder.encode("MobileOS","UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8")); /*IOS(아이폰), AND(안드로이드), WIN(원도우폰), ETC*/
        urlBuilder.append("&" + URLEncoder.encode("MobileApp","UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8")); /*서비스명=어플명*/
        urlBuilder.append("&" + URLEncoder.encode("areaCode","UTF-8") + "=" + URLEncoder.encode(areaCode, "UTF-8")); /*지역코드(areaCode)*/
        urlBuilder.append("&" + URLEncoder.encode("sigunguCode","UTF-8") + "=" + URLEncoder.encode(sigunguCode, "UTF-8")); /*시군구코드*/
        urlBuilder.append("&" + URLEncoder.encode("arrange","UTF-8") + "=" + URLEncoder.encode(arrange, "UTF-8")); /*정렬 구분*/
        urlBuilder.append("&" + URLEncoder.encode("listYN","UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /*목록 구분*/
    	urlBuilder.append("&" + "_type=json"); /*반환타입 json*/
    	
    	if (selectedCategory == 0 || selectedCategory == 1) {// 지역기반 검색 or 키워드 검색 조회
    		int selectedContentTypeId = comboContentTypeId.getSelectionModel().getSelectedIndex();
    		int selectedCat1 = 0;
    		int selectedCat2 = 0;
    		String cat1 = "";
    		String cat2 = "";
    		String contentTypeId = contentTypeIdCodeList.get(selectedContentTypeId);
    		
    		if (contentTypeId.equals("14")) {// 문화시설
    			cat1 = contentTypeId_14_cat1Code;
    			cat2 = contentTypeId_14_cat2Code;
			} else if (contentTypeId.equals("32")) {// 숙박
				cat1 = contentTypeId_32_cat1Code;
				cat2 = contentTypeId_32_cat2Code;
			} else {
				selectedCat1 = comboCat1.getSelectionModel().getSelectedIndex();
				selectedCat2 = comboCat2.getSelectionModel().getSelectedIndex();
				cat1 = cat1CodeList.get(selectedCat1);
	    		cat2 = cat1 + "0" + (selectedCat2 + 1);
			}
    		
    		urlBuilder.append("&" + URLEncoder.encode("contentTypeId","UTF-8") + "=" + URLEncoder.encode(contentTypeId, "UTF-8")); /*관광타입ID*/
    		urlBuilder.append("&" + URLEncoder.encode("cat1","UTF-8") + "=" + URLEncoder.encode(cat1, "UTF-8")); /*대분류*/
    		urlBuilder.append("&" + URLEncoder.encode("cat2","UTF-8") + "=" + URLEncoder.encode(cat2, "UTF-8")); /*중분류*/
    		
    		// 키워드 검색 조회인 경우
    		if (selectedCategory == 1) {
    			String keyword = tfKeyword.getText();
    			urlBuilder.append("&" + URLEncoder.encode("keyword","UTF-8") + "=" + URLEncoder.encode(keyword, "UTF-8")); /*키워드*/
			}
    		
    	} else if (selectedCategory == 2) {// 행사정보 조회
    		// 날짜를 하나라도 입력 안한경우
    		if (DateEventStartDate.getValue() == null || DateEventEndDate.getValue() == null) {
    			alert("날짜를 입력하세요", "날짜 오류");
				return;
			}
    		// 종료날짜를 시작날짜보다 이전으로 설정한 경우
    		int flag = DateEventStartDate.getValue().compareTo(DateEventEndDate.getValue());
    		if (flag > 0) {
    			alert("종료날짜를 시작날짜보다 이후로 설정하세요", "날짜 오류");
    			return;
			}
    		
    		String eventStartDate = DateEventStartDate.getValue().format(DateTimeFormatter.BASIC_ISO_DATE);
    		String eventEndDate = DateEventEndDate.getValue().format(DateTimeFormatter.BASIC_ISO_DATE);
//    		System.out.println(flag);
    		urlBuilder.append("&" + URLEncoder.encode("eventStartDate","UTF-8") + "=" + URLEncoder.encode(eventStartDate, "UTF-8")); /*행사 시작일*/
    		urlBuilder.append("&" + URLEncoder.encode("eventEndDate","UTF-8") + "=" + URLEncoder.encode(eventEndDate, "UTF-8")); /*행사 종료일*/

    	} else if (selectedCategory == 3) {// 숙박정보 조회
			int selectedSoogso = comboSoogso.getSelectionModel().getSelectedIndex();
			String soogsoCode = soogsoCodeList.get(selectedSoogso);
			urlBuilder.append("&" + URLEncoder.encode(soogsoCode,"UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*숙소 종류 여부*/
		}

    	
    	// StringBuilder를 이용한 URL생성
    	URL url = new URL(urlBuilder.toString());
//    	System.out.println(url.toString());
    	
    	// URL을 이용한 연결
    	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    	conn.setRequestMethod("GET");
    	conn.setRequestProperty("Content-type", "application/json");
    	BufferedReader readResult;
    	
    	// url 연결의 응답 결과가 200 ~ 300 이내이면 통과
    	// 그 이외인 경우 에러코드 출력 (예 : 404 에러)
    	if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
    		readResult = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    	} else {
    		readResult = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
    	}
    	StringBuilder resultJson = new StringBuilder();
    	String line;
    	while ((line = readResult.readLine()) != null) {
    		resultJson.append(line);
    	}
    	readResult.close();
    	conn.disconnect();
    	
    	
    	// 출력결과를 StringBuilder에 저장
//    	System.out.println(resultJson.toString());
    	
    	
    	//============================================================================
        //==결과(String)를 Json으로 저장===============================
        //============================================================================
        String json = resultJson.toString();
    	JsonParser parser = new JsonParser();
    	JsonElement element = parser.parse(json);
    	JsonElement response = element.getAsJsonObject().get("response");
    	
    	JsonElement response_header = response.getAsJsonObject().get("header");
    	JsonElement response_header_resultCode = response_header.getAsJsonObject().get("resultCode");
    	JsonElement response_header_resultMsg = response_header.getAsJsonObject().get("resultMsg");
//    	System.out.println("response_header_resultCode : " + response_header_resultCode);
//    	System.out.println("response_header_resultMsg : " + response_header_resultMsg);
    	
    	// 결과 코드가 정상(0000) 이 아닌 경우 종료
    	if (!response_header_resultCode.getAsString().equals("0000")) {
			System.out.println("결과값이 정상이 아닙니다");
			System.out.println("response_header_resultMsg : " + response_header_resultMsg);
			System.exit(0);
		}

    	JsonElement response_body = response.getAsJsonObject().get("body");
    	JsonElement response_body_numOfRows = response_body.getAsJsonObject().get("numOfRows");
    	JsonElement response_body_pageNo = response_body.getAsJsonObject().get("pageNo");
    	JsonElement response_body_totalCount = response_body.getAsJsonObject().get("totalCount");
    	int totalCount = Integer.valueOf((response_body_totalCount.toString()));
    	
//    	System.out.println("response_body_numOfRows : " + response_body_numOfRows);
//    	System.out.println("response_body_pageNo : " + response_body_pageNo);
//    	System.out.println("response_body_totalCount : " + totalCount);
    	
    	if (totalCount == 0) {
    		searchResultList = new ArrayList<>();
    		obListLeft.setAll(searchResultList);
        	tableViewLeft.setItems(obListLeft);
			return;
		}
    	
    	JsonElement response_body_items = response_body.getAsJsonObject().get("items");
    	JsonElement response_body_items_item = response_body_items.getAsJsonObject().get("item");
//    	System.out.println("response_body_items : " + response_body_items);
//    	System.out.println("response_body_items_item : " + response_body_items_item);
    	
    	
    	searchResultList = new ArrayList<>();
    	// 결과값이 여러개인 경우
    	if (totalCount >= 2) {
    		for (int i = 0; i < totalCount; i++) {
    			JsonArray response_body_items_item_array = response_body_items_item.getAsJsonArray();
//    			System.out.println("response_body_items_item_array : " + response_body_items_item_array.get(i));
    			
    			JsonObject result = response_body_items_item_array.get(i).getAsJsonObject();
    			TravelListVO resultVO = new TravelListVO();
    			resultVO.setTrv_id(result.get("contentid").getAsString());
    			resultVO.setTrv_ctt_tp(result.get("contenttypeid").getAsString());
    			if (result.get("addr1") != null) {
    				resultVO.setTrv_add(result.get("addr1").getAsString());
    			} else {
    				resultVO.setTrv_add("없음");
    			}
    			if (result.get("firstimage2") != null) {
    				resultVO.setTrv_rep_img(result.get("firstimage2").getAsString());
    				String imgUrl = resultVO.getTrv_rep_img();
    				ImageView newImageView = new ImageView(imgUrl);
    				newImageView.setFitWidth(250);
    				resultVO.setImageView(new ImageView(imgUrl));
    			} else {
    				resultVO.setTrv_rep_img("없음");
    				resultVO.setImageView(new ImageView("controller/team/noImage.png"));
    			}
    			if (result.get("tel") != null) {
    				resultVO.setTrv_ph(result.get("tel").getAsString());
    			} else {
    				resultVO.setTrv_ph("없음");
    			}
    			if (result.get("title") != null) {
    				resultVO.setTrv_nm(result.get("title").getAsString());
    			} else {
    				resultVO.setTrv_nm("없음");
    			}

    			
    			searchResultList.add(resultVO);
			}
		} else {
			
			JsonObject result = response_body_items_item.getAsJsonObject();
			TravelListVO resultVO = new TravelListVO();
			resultVO.setTrv_id(result.get("contentid").getAsString());
			resultVO.setTrv_ctt_tp(result.get("contenttypeid").getAsString());
			if (result.get("addr1") != null) {
				resultVO.setTrv_add(result.get("addr1").getAsString());
			} else {
				resultVO.setTrv_add("없음");
			}
			if (result.get("firstimage2") != null) {
				resultVO.setTrv_rep_img(result.get("firstimage2").getAsString());
				String imgUrl = resultVO.getTrv_rep_img();
				ImageView newImageView = new ImageView(imgUrl);
				newImageView.setFitWidth(250);
				resultVO.setImageView(new ImageView(imgUrl));
			} else {
				resultVO.setTrv_rep_img("없음");
				resultVO.setImageView(new ImageView("controller/team/noImage.png"));
			}
			if (result.get("tel") != null) {
				resultVO.setTrv_ph(result.get("tel").getAsString());
			} else {
				resultVO.setTrv_ph("없음");
			}
			if (result.get("title") != null) {
				resultVO.setTrv_nm(result.get("title").getAsString());
			} else {
				resultVO.setTrv_nm("없음");
			}
			
			searchResultList.add(resultVO);
		}
    	obListLeft.setAll(searchResultList);
    	tableViewLeft.setItems(obListLeft);
    }
    
    
    
    /**
     * 검색결과 테이블 클릭시
     * 한번 클릭(항목기억), 두번 클릭(해당 상세내용 팝업창)
     * @param 한번 클릭 또는 두번 클릭
     */
    @FXML
    void tableViewLeftClicked(MouseEvent event) {
    	if(event.getClickCount() == 2) {
	    	Stage popUpStage = new Stage();
			try {
				TeamTravelPopup.setSelectedContentId(searchSelected.getTrv_id());
				Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../../view/team/TeamTravelPopup.fxml")));
				popUpStage.setTitle("여행지 상세 프로필");
				popUpStage.setScene(scene);
				popUpStage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    } else if(event.getClickCount() == 1) {
	    	searchSelected = tableViewLeft.getSelectionModel().getSelectedItem();
	    }
    }

    /**
     * 여행지리스트 테이블 클릭시
     * 한번 클릭(항목기억), 두번 클릭(해당 상세내용 팝업창)
     * @param 한번 클릭 또는 두번 클릭
     */
    @FXML
    void tableViewRightClicked(MouseEvent event) {
    	if(event.getClickCount() == 2) {
    		Stage popUpStage = new Stage();
			try {
				TeamTravelPopup.setSelectedContentId(travelSelected.getTrv_id());
				Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../../view/team/TeamTravelPopup.fxml")));
				popUpStage.setTitle("여행지 상세 프로필");
				popUpStage.setScene(scene);
				popUpStage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    } else if(event.getClickCount() == 1) {
	    	travelSelected = tableViewRight.getSelectionModel().getSelectedItem();
	    	
	    	// 리더가 아닌 경우
	    	if (!leaderId.equals(userId)) {
	    		int cnt = 0;
	    		try {
	    			cnt = service.getTravelOnSchedule(teamId, travelSelected.getTrv_id());
				} catch (RemoteException e) {
//					e.printStackTrace();
				}
	    		// 일정에 해당 여행지가 올라가있는 경우
	    		if (cnt > 0) {
	    			btnRm.setDisable(true);
				} else {
					btnRm.setDisable(false);
				}
			}
	    }
    }
    
    /**
     * 추가하기 버튼 클릭시
     * 왼쪽 테이블의 값을 오른쪽 테이블에 저장(DB에도 저장)
     * @param 추가하기 버튼 클릭시
     */
    @FXML
    void btnAdd(ActionEvent event) {
    	if (searchSelected == null) {
			return;
		}
    	searchSelected.setTrv_tm_id(teamId);
    	
    	try {
    		// DB에 해당 값을 저장
			service.insertTravel(searchSelected);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    	reflesh();
    }

    /**
     * 제거하기 버튼 클릭시
     * 오른쪽 테이블의 값을 제거(DB에서도 제거)
     * @param 제거하기 버튼 클릭시
     */
    @FXML
    void btnRemove(ActionEvent event) {
    	if (travelSelected == null) {
			return;
		}
    	travelSelected.setTrv_tm_id(teamId);
    	
    	int cnt = 0;
		try {
			cnt = service.getTravelOnSchedule(teamId, travelSelected.getTrv_id());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		// 일정에 해당 여행지가 올라가있는 경우
		if (cnt > 0) {
			alertConfirm("일정도 지워지는데 지우시겠습니까?", "일정 연쇄 삭제 위험!");
		} else {
			deleteFromTravelList();
		}
    }
    
    @FXML
    void initialize() {
    	teamId = MenuPage.getTeamId();
    	userId = MenuPage.getUserId();
    	try {
        	Registry reg = LocateRegistry.getRegistry(localhost, 9988);
        	service = (TeamTravelServiceInf) reg.lookup("TeamTravelList");
        	TeamMenuServiceInf menuService = (TeamMenuServiceInf) reg.lookup("TeamMenu");
        	leaderId = menuService.getTeamLeaderId(teamId);
        } catch (RemoteException e) {
        	e.printStackTrace();
        } catch (NotBoundException e) {
        	e.printStackTrace();
        }
    	
    	
        // Parameter 값들을 가지는 vo 생성
        apiParameterVO = new ParameterVO();
        
        // parameter를 받아줄 List 생성
        categoryList = new ArrayList<>();
        arrangeNameList = new ArrayList<>();
        arrangeCodeList = new ArrayList<>();
        contentTypeIdNameList = new ArrayList<>();
        contentTypeIdCodeList = new ArrayList<>();
        cat1NameList = new ArrayList<>();
        cat1CodeList = new ArrayList<>();
        cat2NameList = new ArrayList<>();
        areaNameList = new ArrayList<>();
    	areaCodeList = new ArrayList<>();
    	sigunguNameList = new ArrayList<>();
        soogsoNameList = new ArrayList<>();
        soogsoCodeList = new ArrayList<>();
        sigunguCode_Array = apiParameterVO.getSigunguCode_Array();
        contentTypeId_12_cat2_Map = apiParameterVO.getContentTypeId_12_cat2_Array();
        
        
        // List 초기화
        String[] category = apiParameterVO.getCategory();
        for (int i = 0; i < category.length; i++) {
        	categoryList.add(category[i]);
		}
        
        String[] arrangeName = apiParameterVO.getArrange_name();
        String[] arrangeCode = apiParameterVO.getArrange_code();
        for (int i = 0; i < arrangeName.length; i++) {
			arrangeNameList.add(arrangeName[i]);
			arrangeCodeList.add(arrangeCode[i]);
		}
        
        String[] contentTypeIdName = apiParameterVO.getContentTypeId_name_Array();
        String[] contentTypeIdCode = apiParameterVO.getContentTypeId_code_Array();
        for (int i = 0; i < contentTypeIdName.length; i++) {
        	contentTypeIdNameList.add(contentTypeIdName[i]);
        	contentTypeIdCodeList.add(contentTypeIdCode[i]);
		}
        
        String[] areaName = apiParameterVO.getAreaCode_name_Array();
        String[] areaCode = apiParameterVO.getAreaCode_code_Array();
        for (int i = 0; i < areaName.length; i++) {
			areaNameList.add(areaName[i]);
			areaCodeList.add(areaCode[i]);
		}
        // 초기값 설정
        String[] sigungu = sigunguCode_Array.get(areaCode[0]);
        for (int i = 0; i < sigungu.length; i++) {
			sigunguNameList.add(sigungu[i]);
		}
        
        String[] cat1Name = apiParameterVO.getContentTypeId_12_cat1_name_Array();
        String[] cat1Code = apiParameterVO.getContentTypeId_12_cat1_code_Array();
        for (int i = 0; i < cat1Name.length; i++) {
        	cat1NameList.add(cat1Name[i]);
        	cat1CodeList.add(cat1Code[i]);
		}
        // 초기값 설정
        String[] cat2Name = contentTypeId_12_cat2_Map.get(cat1Code[0]);
        for (int i = 0; i < cat2Name.length; i++) {
        	cat2NameList.add(cat2Name[i]);
		}
        
        contentTypeId_14_cat1Code = apiParameterVO.getContentTypeId_14_cat1();
    	contentTypeId_14_cat2Code = apiParameterVO.getContentTypeId_14_cat2();
    	contentTypeId_32_cat1Code = apiParameterVO.getContentTypeId_32_cat1();
    	contentTypeId_32_cat2Code = apiParameterVO.getContentTypeId_32_cat2();
        
        
        soogsoNameList.add("한옥");
        soogsoNameList.add("베니키아");
        soogsoNameList.add("굿스테이");
        soogsoCodeList.add("hanOk");
        soogsoCodeList.add("benikia");
        soogsoCodeList.add("goodStay");
        
        
        // 콤보박스 데이터 셋팅
        comboCategory.getItems().setAll(categoryList);
        comboArrange.getItems().setAll(arrangeNameList);
        comboContentTypeId.getItems().setAll(contentTypeIdNameList);
        comboCat1.getItems().setAll(cat1NameList);
        comboCat2.getItems().setAll(cat2NameList);
        comboAreaCode.getItems().setAll(areaNameList);
        comboSigunguCode.getItems().setAll(sigunguNameList);
        comboSoogso.getItems().setAll(soogsoNameList);
        
        
        // 콤보박스의 데이터를 첫번째 데이터로 설정
        comboCategory.getSelectionModel().selectFirst();
        comboArrange.getSelectionModel().selectFirst();
        comboContentTypeId.getSelectionModel().selectFirst();
        comboCat1.getSelectionModel().selectFirst();
        comboCat2.getSelectionModel().selectFirst();
        comboAreaCode.getSelectionModel().selectFirst();
        comboSigunguCode.getSelectionModel().selectFirst();
        comboSoogso.getSelectionModel().selectFirst();
        
        
        // 테이블 초기화
        // 테이블에 넣을 데이터를 가져옴
        travelList = new ArrayList<>();// 서비스에서 가져오기
        
        // 테이블에 넣을 데이터를 ObservableList에 담음
        obListLeft.setAll(travelList);
        
        // 컬럼 설정
        colTitleLeft.setCellValueFactory(new PropertyValueFactory<>("trv_nm"));
        colTypeLeft.setCellValueFactory(new PropertyValueFactory<>("trv_ctt_tp"));
        colImageLeft.setCellValueFactory(new PropertyValueFactory<>("imageView"));
        colTitleRight.setCellValueFactory(new PropertyValueFactory<>("trv_nm"));
        colTypeRight.setCellValueFactory(new PropertyValueFactory<>("trv_ctt_tp"));
        colImageRight.setCellValueFactory(new PropertyValueFactory<>("imageView"));
        colAddr1Right.setCellValueFactory(new PropertyValueFactory<>("trv_add"));
        colTelRight.setCellValueFactory(new PropertyValueFactory<>("trv_ph"));
        
        reflesh();
        
        String selectedItem = MenuPage.getSelected();
        // 페이지 셋팅
        // 검색하기 페이지에 맞는 설정
        if (selectedItem.equals("SearchTravel")) {
        	btnSearchAdd.setVisible(false);
        	labelTravelList.setVisible(false);
        	btnRm.setVisible(false);
        	tableViewRight.setVisible(false);
        	tableViewLeft.setPrefSize(1200, 553);
        }
        
    }
    
    
    private void alert(String msg, String title) {
    	Alert alertWarning = new Alert(AlertType.WARNING);
    	alertWarning.setTitle(title);
    	alertWarning.setContentText(msg);
    	alertWarning.showAndWait();
    }
    
    
    private void alertConfirm(String msg, String title) {
    	Alert alertConfirm = new Alert(AlertType.CONFIRMATION);
    	alertConfirm.setTitle(title);
    	alertConfirm.setContentText(msg);
    	alertConfirm.showAndWait();
    	if (alertConfirm.getResult() == ButtonType.OK) {
    		deleteFromTravelList();
    	}
    }
    
    private void deleteFromTravelList() {
    	try {
    		// DB에서 해당 값을 제거
			service.deleteTravel(travelSelected.getTrv_id());
			
			reflesh();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * 오른쪽 테이블 새로고침
     */
    private void reflesh() {
    	try {
			// DB에서 데이터 받아옴
			travelList = service.getTravelList(teamId);
			for (TravelListVO travelVo : travelList) {
				String img = travelVo.getTrv_rep_img();
				travelVo.setImageView(new ImageView(img));
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
    	
    	// 오른쪽 테이블 새로고침
    	obListRight.setAll(travelList);
    	tableViewRight.setItems(obListRight);
    }
}