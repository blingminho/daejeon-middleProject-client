package vo;

import java.io.Serializable;

/**
 * JOIN 테이블에 해당하는 VO
 * @author 전준혁
 *
 */
public class JoinVO implements Serializable{
	
	private static final long serialVersionUID = -6578840925531992688L;
	
	private String jo_us_id;
	private String jo_id;
	private String jo_ct_id;
	private String jo_stt;
	private String jo_tm_id;
	
	public String getJo_us_id() {
		return jo_us_id;
	}
	public void setJo_us_id(String jo_us_id) {
		this.jo_us_id = jo_us_id;
	}
	public String getJo_id() {
		return jo_id;
	}
	public void setJo_id(String jo_id) {
		this.jo_id = jo_id;
	}
	public String getJo_ct_id() {
		return jo_ct_id;
	}
	public void setJo_ct_id(String jo_ct_id) {
		this.jo_ct_id = jo_ct_id;
	}
	public String getJo_stt() {
		return jo_stt;
	}
	public void setJo_stt(String jo_stt) {
		this.jo_stt = jo_stt;
	}
	public String getJo_tm_id() {
		return jo_tm_id;
	}
	public void setJo_tm_id(String jo_tm_id) {
		this.jo_tm_id = jo_tm_id;
	}
	
}
