package vo;

import java.io.Serializable;

/**
 * TEAM 테이블에 해당하는 VO
 * @author 전준혁
 *
 */
public class TeamVO implements Serializable{

	private static final long serialVersionUID = 6603367337735239093L;
	
	private String tm_id;
	private int tm_pn;
	private String tm_rec_st_dt;
	private String tm_rec_ed_dt;
	private String tm_op;
	private String tm_nm;
	private String tm_ld_id;
	private String tm_pf;
	
	public String getTm_id() {
		return tm_id;
	}
	public void setTm_id(String tm_id) {
		this.tm_id = tm_id;
	}
	public int getTm_pn() {
		return tm_pn;
	}
	public void setTm_pn(int tm_pn) {
		this.tm_pn = tm_pn;
	}
	public String getTm_rec_st_dt() {
		return tm_rec_st_dt;
	}
	public void setTm_rec_st_dt(String tm_rec_st_dt) {
		this.tm_rec_st_dt = tm_rec_st_dt;
	}
	public String getTm_rec_ed_dt() {
		return tm_rec_ed_dt;
	}
	public void setTm_rec_ed_dt(String tm_rec_ed_dt) {
		this.tm_rec_ed_dt = tm_rec_ed_dt;
	}
	public String getTm_op() {
		return tm_op;
	}
	public void setTm_op(String tm_op) {
		this.tm_op = tm_op;
	}
	public String getTm_nm() {
		return tm_nm;
	}
	public void setTm_nm(String tm_nm) {
		this.tm_nm = tm_nm;
	}
	public String getTm_ld_id() {
		return tm_ld_id;
	}
	public void setTm_ld_id(String tm_ld_id) {
		this.tm_ld_id = tm_ld_id;
	}
	public String getTm_pf() {
		return tm_pf;
	}
	public void setTm_pf(String tm_pf) {
		this.tm_pf = tm_pf;
	}

}
