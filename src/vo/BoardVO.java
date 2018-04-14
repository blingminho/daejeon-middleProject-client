package vo;

import java.io.Serializable;

/**
 * BOARD 테이블에 해당하는 VO
 * @author 전준혁
 *
 */
public class BoardVO implements Serializable{

	private static final long serialVersionUID = -1265794131881830157L;
	
	private String bd_id;
	private String bd_us_id;
	private String bd_cgr;
	private String bd_dt;
	private String bd_content;
	private String bd_tm_id;
	private String bd_nm;
	
	public String getBd_nm() {
		return bd_nm;
	}
	public void setBd_nm(String bd_nm) {
		this.bd_nm = bd_nm;
	}
	
	public String getBd_id() {
		return bd_id;
	}
	public void setBd_id(String bd_id) {
		this.bd_id = bd_id;
	}
	public String getBd_us_id() {
		return bd_us_id;
	}
	public void setBd_us_id(String bd_us_id) {
		this.bd_us_id = bd_us_id;
	}
	public String getBd_cgr() {
		return bd_cgr;
	}
	public void setBd_cgr(String bd_cgr) {
		this.bd_cgr = bd_cgr;
	}
	public String getBd_dt() {
		return bd_dt;
	}
	public void setBd_dt(String bd_dt) {
		this.bd_dt = bd_dt;
	}
	public String getBd_content() {
		return bd_content;
	}
	public void setBd_content(String bd_content) {
		this.bd_content = bd_content;
	}
	public String getBd_tm_id() {
		return bd_tm_id;
	}
	public void setBd_tm_id(String bd_tm_id) {
		this.bd_tm_id = bd_tm_id;
	}
	
}
