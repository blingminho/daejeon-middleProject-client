package vo;

import java.io.Serializable;

public class TeamStatusVO implements Serializable{

	private static final long serialVersionUID = -6696268430038859071L;
	private String tm_id;
	private String tm_nm;
	private String jo_id;
	
	public TeamStatusVO() {
		
	}
	
	public TeamStatusVO(String tm_id, String tm_nm, String jo_id) {
		super();
		this.tm_id = tm_id;
		this.tm_nm = tm_nm;
		this.jo_id = jo_id;
	}

	public String getTm_id() {
		return tm_id;
	}

	public void setTm_id(String tm_id) {
		this.tm_id = tm_id;
	}

	public String getTm_nm() {
		return tm_nm;
	}

	public void setTm_nm(String tm_nm) {
		this.tm_nm = tm_nm;
	}

	public String getJo_id() {
		return jo_id;
	}

	public void setNo(String jo_id) {
		this.jo_id= jo_id;
	}

	@Override
	public String toString() {
		return "TeamStatus [tm_id=" + tm_id + ", tm_nm=" + tm_nm + ", jo_id=" + jo_id + "]";
	}
	
	
	
}
