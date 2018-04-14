package vo;

import java.io.Serializable;

/**
 * PHB 테이블에 해당하는 VO
 * @author 전준혁
 *
 */
public class PhbVO implements Serializable{

	private static final long serialVersionUID = 7303600308304462180L;
	
	private String phb_id;
	private String phb_us_id;
	private String phb_dt;
	private int phb_gd;
	private String phb_tm_id;
	private String phb_nm;
	
	public String getPhb_id() {
		return phb_id;
	}
	public void setPhb_id(String phb_id) {
		this.phb_id = phb_id;
	}
	public String getPhb_us_id() {
		return phb_us_id;
	}
	public void setPhb_us_id(String phb_us_id) {
		this.phb_us_id = phb_us_id;
	}
	public String getPhb_dt() {
		return phb_dt;
	}
	public void setPhb_dt(String phb_dt) {
		this.phb_dt = phb_dt;
	}
	public int getPhb_gd() {
		return phb_gd;
	}
	public void setPhb_gd(int phb_gd) {
		this.phb_gd = phb_gd;
	}
	public String getPhb_tm_id() {
		return phb_tm_id;
	}
	public void setPhb_tm_id(String phb_tm_id) {
		this.phb_tm_id = phb_tm_id;
	}
	public String getPhb_nm() {
		return phb_nm;
	}
	public void setPhb_nm(String phb_nm) {
		this.phb_nm = phb_nm;
	}
	
}
