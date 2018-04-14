package service.team;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import vo.UserVO;
/**
 * 팀 프로필에 관련된 서비스 인터페이스
 * @author Jun
 *
 */
public interface TeamProfileServiceInf extends Remote{

	/**
	 * 팀아이디를 제공하여 해당 팀의 모든 멤버를 가져옴
	 * @param 팀 아이디
	 * @return 유저vo
	 */
	public List<UserVO> getTeamMemberList(String tm_id) throws RemoteException;
	
	/**
	 * 유저아이디를 제공하여 해당 유저의 상세정보를 가져옴
	 * @param 유저아이디
	 * @return 해당 팀의 인원수
	 */
	public UserVO getTeamMemberProfile(String us_id) throws RemoteException;
}
