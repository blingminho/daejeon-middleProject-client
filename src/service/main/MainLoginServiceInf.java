package service.main;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import vo.JoinVO;
import vo.UserVO;

public interface MainLoginServiceInf extends Remote{

	/**
	 * id값이 있으면 1을 반환 없으면 0을 반환
	 * @param string
	 * @return
	 */
	public int getLoginId(String userId) throws RemoteException;
	
	/**
	 * password값이 있으면 1을 반환 없으면 0을 반환
	 * @param string
	 * @return
	 */
	public int getLoginPwd(String userPwd) throws RemoteException;
	
	/**
	 * 로그인을 하기전 해당 id가 있는지 확인
	 * @param userId
	 * @return
	 * @throws RemoteException
	 */
	public List<UserVO> getLoginUser(String userId) throws RemoteException;
	
	/**
	 * 로그인을 하면 팀아이디를 넘겨주기 위해 사용
	 * @param userId
	 * @return
	 * @throws RemoteException
	 */
	public List<JoinVO> getJoinInfo(String userId) throws RemoteException;
	
}
