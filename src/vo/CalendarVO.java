package vo;

import java.io.Serializable;

/**
 * CALENDAR 테이블에 해당하는 VO
 * @author 전준혁
 *
 */
public class CalendarVO implements Serializable{

	private static final long serialVersionUID = 670271821605585546L;
	
	private String cal_id;
	private String cal_tm_id;
	private String cal_app_dt;
	private String cal_trv_id;
	private String cal_trv_nm;
	
	public String getCal_id() {
		return cal_id;
	}
	public void setCal_id(String cal_id) {
		this.cal_id = cal_id;
	}
	public String getCal_app_dt() {
		return cal_app_dt;
	}
	public void setCal_app_dt(String cal_app_dt) {
		this.cal_app_dt = cal_app_dt;
	}
	public String getCal_trv_id() {
		return cal_trv_id;
	}
	public void setCal_trv_id(String cal_trv_id) {
		this.cal_trv_id = cal_trv_id;
	}
	public String getCal_tm_id() {
		return cal_tm_id;
	}
	public void setCal_tm_id(String cal_tm_id) {
		this.cal_tm_id = cal_tm_id;
	}
	public String getCal_trv_nm() {
		return cal_trv_nm;
	}
	public void setCal_trv_nm(String cal_trv_nm) {
		this.cal_trv_nm = cal_trv_nm;
	}
	
}
