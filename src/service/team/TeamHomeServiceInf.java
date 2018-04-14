package service.team;

import java.rmi.Remote;
import java.rmi.RemoteException;

import vo.TeamVO;
/**
 * TeamHome 페이지와 연결된 서비스 인터페이스
 * @author Jun
 *
 */
public interface TeamHomeServiceInf extends Remote{

	/**
	 * 팀 아이디를 제공하여 해당 팀의 정보를 가져옴
	 * @param 팀 아이디
	 * @return 팀vo
	 */
	public TeamVO getTeamInfo(String tm_id) throws RemoteException;
	
	/**
	 * 팀아이디를 제공하여 해당 팀의 인원수를 가져옴
	 * @param 팀 아이디
	 * @return 해당 팀의 인원수
	 */
	public int getTeamMemberTotalCount(String jo_tm_id) throws RemoteException;
	
	/**
	 * 팀아이디와 유저아이디를 제공하여 해당 팀의 가입 또는 초대 여부를 가져옴
	 * @param jo_tm_id 팀아이디
	 * @param jo_us_id 유저아이디
	 * @return jo_stt (가입 상태)
	 */
	public String getJoinMemberState(String jo_tm_id, String jo_us_id) throws RemoteException;
	
	/**
	 * 팀아이디와 유저아이디를 제공하여 가입 테이블에서 제거
	 * @param jo_tm_id 팀아이디
	 * @param jo_us_id 유저아이디
	 * @return 성공시 true
	 */
	public boolean deleteJoinMember(String jo_tm_id, String jo_us_id) throws RemoteException;
	
	/**
	 * 팀아이디와 유저아이디를 제공하여 가입 테이블에서 제거
	 * @param jo_tm_id 팀아이디
	 * @param jo_us_id 유저아이디
	 * @return 성공시 true
	 */
	public boolean insertJoinMember(String jo_tm_id, String jo_us_id) throws RemoteException;
	
	/**
	 * 초대 승낙시 
	 * 팀아이디와 유저아이디를 제공하여 가입 테이블에서 가입 상태를 Y로 변경
	 * @param jo_tm_id 팀아이디
	 * @param jo_us_id 유저아이디
	 * @return 성공시 true
	 */
	public boolean updateJoinMember(String jo_tm_id, String jo_us_id) throws RemoteException;
}
