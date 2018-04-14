package vo;

import java.io.Serializable;

import javafx.scene.image.ImageView;

/**
 * TRAVELLIST 테이블에 해당하는 VO
 * @author 전준혁
 *
 */
public class TravelListVO implements Serializable{

	private static final long serialVersionUID = -5426032145178917167L;
	
	private String trv_id;
	private String trv_rep_img;
	private String trv_add;
	private String trv_nm;
	private String trv_ctt_tp;
	private String trv_ph;
	private String trv_tm_id;
	private transient ImageView imageView;
	
	public String getTrv_id() {
		return trv_id;
	}
	public void setTrv_id(String trv_id) {
		this.trv_id = trv_id;
	}
	public String getTrv_rep_img() {
		return trv_rep_img;
	}
	public void setTrv_rep_img(String trv_rep_img) {
		this.trv_rep_img = trv_rep_img;
	}
	public String getTrv_add() {
		return trv_add;
	}
	public void setTrv_add(String trv_add) {
		this.trv_add = trv_add;
	}
	public String getTrv_nm() {
		return trv_nm;
	}
	public void setTrv_nm(String trv_nm) {
		this.trv_nm = trv_nm;
	}
	public String getTrv_ctt_tp() {
		return trv_ctt_tp;
	}
	public void setTrv_ctt_tp(String trv_ctt_tp) {
		this.trv_ctt_tp = trv_ctt_tp;
	}
	public String getTrv_ph() {
		return trv_ph;
	}
	public void setTrv_ph(String trv_ph) {
		this.trv_ph = trv_ph;
	}
	public String getTrv_tm_id() {
		return trv_tm_id;
	}
	public void setTrv_tm_id(String trv_tm_id) {
		this.trv_tm_id = trv_tm_id;
	}
	public ImageView getImageView() {
		return imageView;
	}
	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}
	
	
}
