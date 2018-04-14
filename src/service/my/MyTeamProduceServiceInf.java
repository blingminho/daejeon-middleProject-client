package service.my;

import java.rmi.Remote;
import java.rmi.RemoteException;

import vo.TeamVO;
/**
 * client_service
 * Team 생성과 연결된 서비스 인터페이스
 * @author sun
 *
 */
public interface MyTeamProduceServiceInf extends Remote{

	
	/**
	 * 팀을 생성하여 팀 테이블에 넣음
	 * @param vo  
	 * @return
	 */
	public  int insertTeam(TeamVO vo) throws RemoteException;
}
