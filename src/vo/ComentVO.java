package vo;

import java.io.Serializable;

/**
 * COMENT 테이블에 해당하는 VO
 * @author 전준혁
 *
 */
public class ComentVO implements Serializable{

	private static final long serialVersionUID = -812001073505249417L;
	
	private String cm_id;
	private String cm_dt;
	private String cm_content;
	private String cm_us_nm;
	private String cm_bd_id;
	private String cm_us_id;
	
	public String getCm_id() {
		return cm_id;
	}
	public void setCm_id(String cm_id) {
		this.cm_id = cm_id;
	}
	public String getCm_dt() {
		return cm_dt;
	}
	public void setCm_dt(String cm_dt) {
		this.cm_dt = cm_dt;
	}
	public String getCm_content() {
		return cm_content;
	}
	public void setCm_content(String cm_content) {
		this.cm_content = cm_content;
	}
	public String getCm_us_nm() {
		return cm_us_nm;
	}
	public void setCm_us_nm(String cm_us_nm) {
		this.cm_us_nm = cm_us_nm;
	}
	public String getCm_bd_id() {
		return cm_bd_id;
	}
	public void setCm_bd_id(String cm_bd_id) {
		this.cm_bd_id = cm_bd_id;
	}
	public String getCm_us_id() {
		return cm_us_id;
	}
	public void setCm_us_id(String cm_us_id) {
		this.cm_us_id = cm_us_id;
	}
	
}
