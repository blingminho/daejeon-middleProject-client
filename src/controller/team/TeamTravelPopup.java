package controller.team;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.ResourceBundle;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import vo.ParameterVO;

/**
 * 여행지 즐겨찾기 목록이나 검색결과 목록을 클릭시 나오는 팝업창
 * @author Jun
 *
 */
public class TeamTravelPopup {

	private static String selectedContentId;
	/**
	 * 선택한 여행지의 contentId를 받아옴
	 * @param 선택한 여행지의 contentId
	 */
	public static void setSelectedContentId(String contentId) {
		selectedContentId = contentId;
	}
	
	// api 설정
	// 공공데이터포털에서 받은 인증키
	private static final String ServiceKey = "EtSF91PcyVZu75%2Fiy%2BCYg19wTdh2k0dPenZOTEQOmzLs7j5qm84mUVav2qJY5cM8dLMQWg2%2BSRXpCFD9DENPbw%3D%3D";
	// 파라미터 vo
	private ParameterVO apiParameterVO;
	
	// 시군구코드
	private Map<String, String[]> sigunguCode_Array;
	
	
	
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Label labelContentTypeId;
    @FXML
    private Label labelTitle;
    @FXML
    private Label labelHomepage;
    @FXML
    private Label labelTel;
    @FXML
    private Label labelAreaCode;
    @FXML
    private Label labelSigunguCode;
    @FXML
    private Label labelAddr1;
    @FXML
    private ImageView imageViewFirst;
    @FXML
    private WebView webViewMap;
    @FXML
    private WebView webViewOverview;
    @FXML
    private ScrollPane scrollOverview;
    
    @FXML
    void initialize() throws IOException {
    	// list 초기화
    	apiParameterVO = new ParameterVO();
    	
    	String[] contentTypeId_code_Array = {"12", "14", "15", "32"};
    	String[] contentTypeId_name_Array = {"관광지", "문화시설", "축제/공연/행사","숙박"};
    	
    	String[] areaCodeArray = apiParameterVO.getAreaCode_code_Array();
    	String[] areaNameArray = apiParameterVO.getAreaCode_name_Array();
    	sigunguCode_Array = apiParameterVO.getSigunguCode_Array();
    	
    	
    	
        //============================================================================
		//==URL을 이용한 연결 및 결과를 String으로 저장===============================
		//============================================================================
		// 요청 파라미터 및 파라미터에 대한 값 설정
		// 공통정보조회
		String defaultYN = "Y";// 기본정보 조회여부
		String firstImageYN = "Y";// 원본, 썸네일 대표이미지 조회여부
		String areacodeYN = "Y";// 지역코드, 시군구코드 조회여부
		String catcodeYN = "N";// 서비스분류코드(대,중,소 코드) 조회여부
		String addrinfoYN = "Y";// 주소, 상세주소 조회여부
		String mapinfoYN = "Y";// 좌표 X,Y 조회여부
		String overviewYN = "Y";// 콘텐츠 개요 조회여부
		
		// URL 만듬
		StringBuilder urlBuilder = new StringBuilder("http://api.visitkorea.or.kr/openapi/service/rest/KorService/"); /*URL*/
		urlBuilder.append(URLEncoder.encode("detailCommon", "UTF-8")); /*요청할 데이터*/
		urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + ServiceKey); /*Service Key*/
		urlBuilder.append("&" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + URLEncoder.encode("SERVICE_KEY", "UTF-8")); /*공공데이터포털에서 받은 인증키*/
		urlBuilder.append("&" + URLEncoder.encode("MobileOS","UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8")); /*IOS(아이폰), AND(안드로이드), WIN(원도우폰), ETC*/
		urlBuilder.append("&" + URLEncoder.encode("MobileApp","UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8")); /*서비스명=어플명*/
		urlBuilder.append("&" + "_type=json"); /*반환타입 json*/
		urlBuilder.append("&" + URLEncoder.encode("defaultYN","UTF-8") + "=" + URLEncoder.encode(defaultYN, "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("firstImageYN","UTF-8") + "=" + URLEncoder.encode(firstImageYN, "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("areacodeYN","UTF-8") + "=" + URLEncoder.encode(areacodeYN, "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("catcodeYN","UTF-8") + "=" + URLEncoder.encode(catcodeYN, "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("addrinfoYN","UTF-8") + "=" + URLEncoder.encode(addrinfoYN, "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("mapinfoYN","UTF-8") + "=" + URLEncoder.encode(mapinfoYN, "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("overviewYN","UTF-8") + "=" + URLEncoder.encode(overviewYN, "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("contentId","UTF-8") + "=" + URLEncoder.encode(selectedContentId, "UTF-8"));
		
		
		// StringBuilder를 이용한 URL생성
		URL url = new URL(urlBuilder.toString());
//		System.out.println(url.toString());
		
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
//		System.out.println(resultJson.toString());
		
		
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
//		System.out.println("response_header_resultCode : " + response_header_resultCode);
//		System.out.println("response_header_resultMsg : " + response_header_resultMsg);
		
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
		
//		System.out.println("response_body_numOfRows : " + response_body_numOfRows);
//		System.out.println("response_body_pageNo : " + response_body_pageNo);
//		System.out.println("response_body_totalCount : " + totalCount);
		
		if (totalCount == 0) {
			return;
		}
		
		JsonElement response_body_items = response_body.getAsJsonObject().get("items");
		JsonElement response_body_items_item = response_body_items.getAsJsonObject().get("item");
//		System.out.println("response_body_items : " + response_body_items);
//		System.out.println("response_body_items_item : " + response_body_items_item);
		
		
		JsonObject result = response_body_items_item.getAsJsonObject();
		
		
		// 무조건 응답하는 파라미터
		String contenttypeid = result.get("contenttypeid").getAsString();
		for (int i = 0; i < contentTypeId_code_Array.length; i++) {
			if (contenttypeid.equals(contentTypeId_code_Array[i])) {
				contenttypeid = contentTypeId_name_Array[i];
			}
		}
		
		String title = result.get("title").getAsString();
		
		// 응답이 없을 수 있는 파라미터 (응답이 없는 경우 : 없음)
		String addr1 = "없음";
		if (result.get("addr1") != null) {
			addr1 = result.get("addr1").getAsString();
		}
		String homepage = "없음";
		if (result.get("homepage") != null) {
			homepage = result.get("homepage").getAsString();
			int indexLeft = homepage.indexOf(">http:") + 1;
			int indexRight = homepage.lastIndexOf("</a>");
			if (indexLeft != -1 && indexRight != -1) {
				homepage = homepage.substring(indexLeft, indexRight);
			}
		}
		String tel = "없음";
		if (result.get("tel") != null) {
			tel = result.get("tel").getAsString();
		}
		String firstimage = "없음";
		if (result.get("firstimage") != null) {
			firstimage = result.get("firstimage").getAsString();
		};
		String areacode = "없음";
		int areacodeInt = 0;
		if (result.get("areacode") != null) {
			areacodeInt = result.get("areacode").getAsInt();
			areacode = result.get("areacode").getAsString();
			for (int i = 0; i < areaCodeArray.length; i++) {
				if (areacode.equals(areaCodeArray[i])) {
					areacode = areaNameArray[i];
				}
			}
		};
		String sigungucode = "없음";
		if (result.get("sigungucode") != null) {
			int sigungucodeInt = result.get("sigungucode").getAsInt() - 1;
			String[] sigunguArray = sigunguCode_Array.get(areacodeInt + "");
//			System.out.println("sigungucodeInt : " + sigungucodeInt);
//			System.out.println("sigunguArray : " + sigunguArray);
			sigungucode = sigunguArray[sigungucodeInt];
		};
		String overview = "없음";
		if (result.get("overview") != null) {
			overview = result.get("overview").getAsString();
		};
		String mapx = "없음";
		if (result.get("mapx") != null) {
			mapx = result.get("mapx").getAsString();
		};
		String mapy = "없음";
		if (result.get("mapy") != null) {
			mapy = result.get("mapy").getAsString();
		};
		String mlevel = "없음";
		if (result.get("mlevel") != null) {
			mlevel = result.get("mlevel").getAsString();
		};
		
		
		// 읽어온 자료를 보여주게끔 셋팅
		labelAddr1.setText(addr1);
		labelHomepage.setText(homepage);
		labelTel.setText(tel);
		labelTitle.setText(title);
		labelContentTypeId.setText(contenttypeid);
		labelAreaCode.setText(areacode);
		labelSigunguCode.setText(sigungucode);
		WebEngine webEngineOverview = webViewOverview.getEngine();
		webEngineOverview.loadContent(overview);
		scrollOverview.setContent(webViewOverview);
		
		// 구글 지도 설정
		WebEngine webEngineMap = webViewMap.getEngine();
        URL urlGoogleMaps = getClass().getResource("googleMap.html");
        
        // 주소가 있는 경우
        if (!mapx.equals("없음") && !mapy.equals("없음") && !mlevel.equals("없음")) {
        	Double dmapx = Double.parseDouble(mapx);
        	Double dmapy = Double.parseDouble(mapy);
        	Double dmlevel = Double.parseDouble("15");
        	
        	webEngineMap.load(urlGoogleMaps.toExternalForm());
        	webEngineMap.getLoadWorker().stateProperty().addListener(new ChangeListener<State>() {
        		@Override
        		public void changed(ObservableValue observable, State oldValue, State newValue) {
        			if (newValue == State.SUCCEEDED) {
        				webEngineMap.executeScript("" +
        						"window.mapx1 = " + dmapx + ";" +
        						"window.mapy1 = " + dmapy + ";" +
        						"window.mlevel1 = " + dmlevel + ";" +
        						"document.settingLocation(window.mapx1, window.mapy1, window.mlevel1);"
        						);
        			}
        		}
        	});
			
		} else {
			
		}
        
		// 이미지 셋팅
        // 이미지가 있는 경우
        if (!firstimage.equals("없음")) {
        	imageViewFirst.setImage(new Image(firstimage));
		} else {
			imageViewFirst.setImage(new Image("controller/team/noImage.png"));
		}
		
		
		
    }
}