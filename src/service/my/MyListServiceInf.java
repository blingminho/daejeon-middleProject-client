package service.my;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


/**
 * client_serivce
 * MyList의  화면에 관련된 서비스 인터페이스
 * @author sun
 * @param <TeamStatusVO>
 *
 */
public interface MyListServiceInf<TeamStatusVO> extends Remote{

	/**
	 * 팀 아이디를 제공하여 가입 신청한 팀의 정보를 가져옴
	 * @param 팀 아이디
	 * @return 팀 vo
	 */
	public List<TeamStatusVO> tabeSignedUpTeam(String jo_us_id) throws RemoteException;
	
	/**
	 * 팀아이디를 제공하여 초대받은 팀의 정보를 가져옴
	 * @param 팀 아이디
	 * @return 팀 vo
	 */
	public List<TeamStatusVO> tableInvitedTeam(String jo_us_id) throws RemoteException;
	
	/**
	 * 팀아이디를 제공하여 가입한  팀의 정보를 가져옴
	 * @param  팀아이디
	 * @return 팀 vo
	 */
	public List<TeamStatusVO> tableEnrolledTeam(String jo_us_id) throws RemoteException;
	
	}
	
