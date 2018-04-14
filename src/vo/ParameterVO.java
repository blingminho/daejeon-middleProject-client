package vo;

import java.util.HashMap;
import java.util.Map;

/**
 * API 콤보박스를 만들기 위한 설정VO<br>
 * getter만 존재<br>
 * 목록 : 지역코드, 시군구코드, 관광타입, 대분류, 중분류, 정렬<br>
 * @author Jun
 *
 */
public class ParameterVO {
	private String[] category = {
			"지역기반 검색", "키워드 검색", "행사정보 검색", "숙박정보 검색"
	};
	
	public String[] getCategory() {
		return category;
	}
	
	public ParameterVO() {
		
		// 시군구코드 초기화
		sigunguCode_Array.put("1", sigunguCode_name_Array_1);
		sigunguCode_Array.put("2", sigunguCode_name_Array_2);
		sigunguCode_Array.put("3", sigunguCode_name_Array_3);
		sigunguCode_Array.put("4", sigunguCode_name_Array_4);
		sigunguCode_Array.put("5", sigunguCode_name_Array_5);
		sigunguCode_Array.put("6", sigunguCode_name_Array_6);
		sigunguCode_Array.put("7", sigunguCode_name_Array_7);
		sigunguCode_Array.put("8", sigunguCode_name_Array_8);
		sigunguCode_Array.put("31", sigunguCode_name_Array_31);
		sigunguCode_Array.put("32", sigunguCode_name_Array_32);
		sigunguCode_Array.put("33", sigunguCode_name_Array_33);
		sigunguCode_Array.put("34", sigunguCode_name_Array_34);
		sigunguCode_Array.put("35", sigunguCode_name_Array_35);
		sigunguCode_Array.put("36", sigunguCode_name_Array_36);
		sigunguCode_Array.put("37", sigunguCode_name_Array_37);
		sigunguCode_Array.put("38", sigunguCode_name_Array_38);
		sigunguCode_Array.put("39", sigunguCode_name_Array_39);
		
		
		// contentTypeId가 관광지인경우 중분류 초기화
		contentTypeId_12_cat2_Array.put("A01", contentTypeId_12_cat2_A01_Array);
		contentTypeId_12_cat2_Array.put("A02", contentTypeId_12_cat2_A02_Array);
		
		
		
	}
	
	//=================================================================================
	// 지역코드
	private String[] areaCode_name_Array = {
			"서울", "인천", "대전", "대구", "광주", "부산", "울산",
			"세종특별자치시", "경기도", "강원도", "충청북도", "충청남도",
			"경상북도", "경상남도","전라북도", "전라남도", "제주도"
	};
	private String[] areaCode_code_Array = {
			"1", "2", "3", "4", "5", "6", "7", "8",
			"31", "32", "33", "34",	"35", "36",	"37", "38",	"39"
	};
	
	//=================================================================================
	// 시군구코드
	private Map<String, String[]> sigunguCode_Array = new HashMap<>();
	
	// 시군구코드_서울
	private String[] sigunguCode_name_Array_1 = {
			"강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구",
			"금천구", "노원구", "도봉구", "동대문구", "동작구", "마포구", "서대문구",
			"서초구", "성동구", "성북구", "송파구", "양천구", "영등포구", "용산구",
			"은평구", "종로구", "중구", "중랑구"
	};
	// 시군구코드_인천
	private String[] sigunguCode_name_Array_2 = {
			"강화군", "계양구", "남구", "남동구", "동구", "부평구", "서구",
			"연수구", "옹진군", "중구"
	};
	// 시군구코드_대전
	private String[] sigunguCode_name_Array_3 = {
			"대덕구", "동구", "서구", "유성구", "중구"
	};
	// 시군구코드_대구
	private String[] sigunguCode_name_Array_4 = {
			"남구", "달서구", "달성군", "동구", "북구", "서구", "수성구",
			"중구"
	};
	// 시군구코드_광주
	private String[] sigunguCode_name_Array_5 = {
			"광산구", "남구", "동구", "북구", "서구"
	};
	// 시군구코드_부산
	private String[] sigunguCode_name_Array_6 = {
			"강서구", "금정구", "기장군", "남구", "동구", "동래구", "부산진구",
			"북구", "사상구", "사하구", "서구", "수영구", "연제구", "영도구",
			"중구", "해운대구"
	};
	// 시군구코드_울산
	private String[] sigunguCode_name_Array_7 = {
			"중구", "남구", "동구", "북구", "울주군"
	};
	// 시군구코드_세종특별자치시
	private String[] sigunguCode_name_Array_8 = {
			"세종특별자치시"
	};
	// 시군구코드_경기도
	private String[] sigunguCode_name_Array_31 = {
			"가평군", "고양시", "과천시", "광명시", "광주시", "구리시", "군포시",
			"김포시", "남양주시", "동두천시", "부천시", "성남시", "수원시", "시흥시",
			"안산시", "안성시", "안양시", "양주시", "양평군", "여주시", "연천군",
			"오산시", "용인시", "의왕시", "의정부시", "이천시", "파주시", "평택시",
			"포천시", "하남시", "화성시"
	};
	// 시군구코드_강원도
	private String[] sigunguCode_name_Array_32 = {
			"강릉시", "고성군", "동해시", "삼척시", "속초시", "양구군", "양양군",
			"영월군", "원주시", "인제군", "정선군", "철원군", "춘천시", "태백시",
			"평창군", "홍천군", "화천군", "횡성군"
	};
	// 시군구코드_충청북도
	private String[] sigunguCode_name_Array_33 = {
			"괴산군", "단양군", "보은군", "영동군", "옥천군", "음성군", "제천시",
			"진천군", "청원군", "청주시", "충주시", "증평군"
	};
	// 시군구코드_충청남도
	private String[] sigunguCode_name_Array_34 = {
			"공주시", "금산군", "논산시", "당진시", "보령시", "부여군", "서산시",
			"서천군", "아산시", "예산군", "천안시", "청양군", "태안군", "홍성군",
			"계룡시"
	};
	// 시군구코드_경상북도
	private String[] sigunguCode_name_Array_35 = {
			"경산시", "경주시", "고령군", "구미시", "군위군", "김천시", "문경시",
			"봉화군", "상주시", "성주군", "안동시", "영덕군", "영양군", "영주시",
			"영천시", "예천군", "울릉군", "울진군", "의성군", "청도군", "청송군",
			"칠곡군", "포항시"
	};
	// 시군구코드_경상남도
	private String[] sigunguCode_name_Array_36 = {
			"거제시", "거창군", "고성군", "김해시", "남해군", "마산시", "밀양시",
			"사천시", "산청군", "양산시", "의령군", "진주시", "진해시", "창녕군",
			"창원시", "통영시", "하동군", "함안군", "함양군", "합천군"
	};
	// 시군구코드_전라북도
	private String[] sigunguCode_name_Array_37 = {
			"고창군", "군산시", "김제시", "남원시", "무주군", "부안군", "순창군",
			"완주군", "익산시", "임실군", "장수군", "전주시", "정읍시", "진안군"
	};
	// 시군구코드_전라남도
	private String[] sigunguCode_name_Array_38 = {
			"강진군", "고흥군", "곡성군", "광양시", "구례군", "나주시", "담양군",
			"목포시", "무안군", "보성군", "순천시", "신안군", "여수시", "영광군",
			"영암군", "완도군", "장성군", "장흥군", "진도군", "함평군", "해남군",
			"화순군"
	};
	// 시군구코드_제주도
	private String[] sigunguCode_name_Array_39 = {
			"남제주군", "북제주군", "서귀포시", "제주시"
	};
	
	//=================================================================================
	// 관광타입(관광지, 숙박등) ID
	private String[] contentTypeId_name_Array = {
			"관광지", "문화시설", "숙박"
	};
	private String[] contentTypeId_code_Array = {
			"12", "14", "32"
	};
	
	// 관광지 & 대분류
	private String[] contentTypeId_12_cat1_name_Array = {
			"자연", "인문(문화/예술/역사)"
	};
	private String[] contentTypeId_12_cat1_code_Array = {
			"A01", "A02"
	};

	// 관광지 & 중분류	
	private Map<String, String[]> contentTypeId_12_cat2_Array = new HashMap<>();
	// 관광지 & 대분류가 A01(자연)인 경우
	private String[] contentTypeId_12_cat2_A01_Array = {
			"자연관광지", "관광자원"
	};
	// 관광지 & 대분류가 A02(인문(문화/예술/역사)) 경우
	private String[] contentTypeId_12_cat2_A02_Array = {
			"역사관광지", "휴양관광지", "체험관광지", "산업관광지", "건축/조형물"
	};
	
	// 문화시설, 숙박은 cat1, cat2 고정
	// 문화시설(14) - A02 - A0206
	// 숙박(32) - B02 - B0201
	private String contentTypeId_14_cat1 = "A02";
	private String contentTypeId_14_cat2 = "A0206";
	private String contentTypeId_32_cat1 = "B02";
	private String contentTypeId_32_cat2 = "B0201";
	
	//=================================================================================
	// 정렬(검색결과)
	private String[] arrange_code = {
			"O", "P"
	};
	private String[] arrange_name = {
			"제목순", "조회순"
	};
	
	
	//=================================================================================
	// getter와 setter
	public String[] getAreaCode_name_Array() {
		return areaCode_name_Array;
	}
	public String[] getAreaCode_code_Array() {
		return areaCode_code_Array;
	}
	public Map<String, String[]> getSigunguCode_Array() {
		return sigunguCode_Array;
	}
	public String[] getContentTypeId_name_Array() {
		return contentTypeId_name_Array;
	}
	public String[] getContentTypeId_code_Array() {
		return contentTypeId_code_Array;
	}
	public String[] getContentTypeId_12_cat1_name_Array() {
		return contentTypeId_12_cat1_name_Array;
	}
	public String[] getContentTypeId_12_cat1_code_Array() {
		return contentTypeId_12_cat1_code_Array;
	}
	public Map<String, String[]> getContentTypeId_12_cat2_Array() {
		return contentTypeId_12_cat2_Array;
	}
	public String getContentTypeId_14_cat1() {
		return contentTypeId_14_cat1;
	}
	public String getContentTypeId_14_cat2() {
		return contentTypeId_14_cat2;
	}
	public String getContentTypeId_32_cat1() {
		return contentTypeId_32_cat1;
	}
	public String getContentTypeId_32_cat2() {
		return contentTypeId_32_cat2;
	}
	public String[] getArrange_code() {
		return arrange_code;
	}
	public String[] getArrange_name() {
		return arrange_name;
	}
	
}
