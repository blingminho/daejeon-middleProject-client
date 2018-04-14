package vo;

import java.io.Serializable;

/**
 * FI 테이블에 해당하는 VO
 * @author 전준혁
 *
 */
public class FileVO implements Serializable{

	private static final long serialVersionUID = 6357118858218745198L;
	
	private String fi_id;
	private String fi_path;
	private String fi_phb_id;
	private String fi_ct_id;
	private String fi_bd_id;
	
	public String getFi_id() {
		return fi_id;
	}
	public void setFi_id(String fi_id) {
		this.fi_id = fi_id;
	}
	public String getFi_path() {
		return fi_path;
	}
	public void setFi_path(String fi_path) {
		this.fi_path = fi_path;
	}
	public String getFi_phb_id() {
		return fi_phb_id;
	}
	public void setFi_phb_id(String fi_phb_id) {
		this.fi_phb_id = fi_phb_id;
	}
	public String getFi_ct_id() {
		return fi_ct_id;
	}
	public void setFi_ct_id(String fi_ct_id) {
		this.fi_ct_id = fi_ct_id;
	}
	public String getFi_bd_id() {
		return fi_bd_id;
	}
	public void setFi_bd_id(String fi_bd_id) {
		this.fi_bd_id = fi_bd_id;
	}
	
}
