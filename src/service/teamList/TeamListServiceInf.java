package service.teamList;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import vo.TeamVO;
/**
 * 공개된 팀 목록과 관련된 서비스 인터페이스
 * @author Jun
 *
 */
public interface TeamListServiceInf extends Remote{

	/**
	 * 팀아이디를 제공하여 가입 가능한 공개된 팀 목록을 가져옴
	 * @return 팀VO 리스트
	 */
	public List<TeamVO> getTeamList() throws RemoteException;
	
	/**
	 * 가입테이블의 팀아이디와 해당 팀의 가입한 인원수를 보내줌
	 * @return jo_tm_id 팀아이디, jo_pn 팀가입수
	 */
	public List<Map<String, String>> getTeamPN() throws RemoteException;
}
