package vo;

import javafx.scene.control.Label;
/**
 * 스케쥴에 담을 VO
 * @author Jun
 *
 */
public class ScheduleVO {
	private String trv_id;
	private String trv_nm;
	private String tm_id;
	private String tm_nm;
	private Label color;
	private String app_dt;
	public String getTrv_id() {
		return trv_id;
	}
	public void setTrv_id(String trv_id) {
		this.trv_id = trv_id;
	}
	public String getTm_id() {
		return tm_id;
	}
	public void setTm_id(String tm_id) {
		this.tm_id = tm_id;
	}
	public String getTrv_nm() {
		return trv_nm;
	}
	public void setTrv_nm(String trv_nm) {
		this.trv_nm = trv_nm;
	}
	public String getTm_nm() {
		return tm_nm;
	}
	public void setTm_nm(String tm_nm) {
		this.tm_nm = tm_nm;
	}
	public Label getColor() {
		return color;
	}
	public void setColor(Label color) {
		this.color = color;
	}
	public String getApp_dt() {
		return app_dt;
	}
	public void setApp_dt(String app_dt) {
		this.app_dt = app_dt;
	}
	
}
