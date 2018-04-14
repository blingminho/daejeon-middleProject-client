package vo;

/**
 * 공개 팀 목록 보기에 쓰임
 * TeamList 에 넣을 vo
 * @author Jun
 *
 */
public class TeamListVO {
	private String tm_id;
	private int tm_no;
	private String tm_nm;
	private String tm_rec_st_dt;
	private String tm_rec_ed_dt;
	private String tm_pf;
	private String tm_pn;
	
	public int getTm_no() {
		return tm_no;
	}
	public void setTm_no(int tm_no) {
		this.tm_no = tm_no;
	}
	public String getTm_nm() {
		return tm_nm;
	}
	public void setTm_nm(String tm_nm) {
		this.tm_nm = tm_nm;
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
	public String getTm_pf() {
		return tm_pf;
	}
	public void setTm_pf(String tm_pf) {
		this.tm_pf = tm_pf;
	}
	public String getTm_pn() {
		return tm_pn;
	}
	public void setTm_pn(String tm_pn) {
		this.tm_pn = tm_pn;
	}
	public String getTm_id() {
		return tm_id;
	}
	public void setTm_id(String tm_id) {
		this.tm_id = tm_id;
	}
	
}
