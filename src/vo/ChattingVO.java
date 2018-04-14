package vo;

import java.io.Serializable;

/**
 * CHATTING 테이블에 해당하는 VO
 * @author 전준혁
 *
 */
public class ChattingVO implements Serializable{
	
	private static final long serialVersionUID = -363301011118756812L;
	
	private String ct_tm_id;
	private String ct_id;
	private String ct_us_id;
	private String ct_content;
	private String ct_dt;
	
	public String getCt_tm_id() {
		return ct_tm_id;
	}
	public void setCt_tm_id(String ct_tm_id) {
		this.ct_tm_id = ct_tm_id;
	}
	public String getCt_id() {
		return ct_id;
	}
	public void setCt_id(String ct_id) {
		this.ct_id = ct_id;
	}
	public String getCt_us_id() {
		return ct_us_id;
	}
	public void setCt_us_id(String ct_us_id) {
		this.ct_us_id = ct_us_id;
	}
	public String getCt_content() {
		return ct_content;
	}
	public void setCt_content(String ct_content) {
		this.ct_content = ct_content;
	}
	public String getCt_dt() {
		return ct_dt;
	}
	public void setCt_dt(String ct_dt) {
		this.ct_dt = ct_dt;
	}

}
