package vo;

import java.io.Serializable;

/**
 * US 테이블에 해당하는 VO
 * @author Jun
 *
 */
public class UserVO implements Serializable{
	
	private static final long serialVersionUID = 6868194802493901884L;

	private String us_id;
	private String us_pw;
	private String us_mail;
	private String us_gn;
	private String us_ph;
	private String us_nm;
	private String us_bir;
	private String us_pf;
	private int us_rt_cn;
	private String us_scs;
	
	public String getUs_id() {
		return us_id;
	}
	public void setUs_id(String us_id) {
		this.us_id = us_id;
	}
	public String getUs_pw() {
		return us_pw;
	}
	public void setUs_pw(String us_pw) {
		this.us_pw = us_pw;
	}
	public String getUs_mail() {
		return us_mail;
	}
	public void setUs_mail(String us_mail) {
		this.us_mail = us_mail;
	}
	public String getUs_gn() {
		return us_gn;
	}
	public void setUs_gn(String us_gn) {
		this.us_gn = us_gn;
	}
	public String getUs_ph() {
		return us_ph;
	}
	public void setUs_ph(String us_ph) {
		this.us_ph = us_ph;
	}
	public String getUs_nm() {
		return us_nm;
	}
	public void setUs_nm(String us_nm) {
		this.us_nm = us_nm;
	}
	public String getUs_bir() {
		return us_bir;
	}
	public void setUs_bir(String us_bir) {
		this.us_bir = us_bir;
	}
	public String getUs_pf() {
		return us_pf;
	}
	public void setUs_pf(String us_pf) {
		this.us_pf = us_pf;
	}
	public int getUs_rt_cn() {
		return us_rt_cn;
	}
	public void setUs_rt_cn(int us_rt_cn) {
		this.us_rt_cn = us_rt_cn;
	}
	public String getUs_scs() {
		return us_scs;
	}
	public void setUs_scs(String us_scs) {
		this.us_scs = us_scs;
	}
	
}
