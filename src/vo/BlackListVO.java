package vo;

import java.io.Serializable;

/**
 * BLACKLIST 테이블에 해당하는 VO
 * @author 전준혁
 *
 */
public class BlackListVO  implements Serializable{
	
	private static final long serialVersionUID = -3229353896098140917L;
	
	private String blist_id;
	private String blist_elm_dt;
	private String blist_us_id;
	
	public String getBlist_id() {
		return blist_id;
	}
	public void setBlist_id(String blist_id) {
		this.blist_id = blist_id;
	}
	public String getBlist_elm_dt() {
		return blist_elm_dt;
	}
	public void setBlist_elm_dt(String blist_elm_dt) {
		this.blist_elm_dt = blist_elm_dt;
	}
	public String getBlist_us_id() {
		return blist_us_id;
	}
	public void setBlist_us_id(String blist_us_id) {
		this.blist_us_id = blist_us_id;
	}
	
}
