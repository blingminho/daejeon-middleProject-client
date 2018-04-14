package vo;

import java.io.Serializable;

/**
 * REPORT 테이블에 해당하는 VO
 * @author 전준혁
 *
 */
public class ReportVO implements Serializable{

	private static final long serialVersionUID = -4798621405433936523L;
	
	private String re_id;
	private String re_cs;
	private String re_tm_id;
	private String re_elm;
	private String re_us_id;
	
	public String getRe_id() {
		return re_id;
	}
	public void setRe_id(String re_id) {
		this.re_id = re_id;
	}
	public String getRe_cs() {
		return re_cs;
	}
	public void setRe_cs(String re_cs) {
		this.re_cs = re_cs;
	}
	public String getRe_tm_id() {
		return re_tm_id;
	}
	public void setRe_tm_id(String re_tm_id) {
		this.re_tm_id = re_tm_id;
	}
	public String getRe_elm() {
		return re_elm;
	}
	public void setRe_elm(String re_elm) {
		this.re_elm = re_elm;
	}
	public String getRe_us_id() {
		return re_us_id;
	}
	public void setRe_us_id(String re_us_id) {
		this.re_us_id = re_us_id;
	}
	
}
