package service.login;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import vo.BoardVO;
import vo.JoinVO;
import vo.UserVO;

public interface JoinServiceInf extends Remote{
	
	/**
	 * 입력한 id값이  디비에 있는지 중복확인
	 * @param userId
	 * @return
	 */
	public int getSearchId(String userId) throws RemoteException;
	
	/**
	 * 입력한 값들을 VO에 담아 디비에 저장
	 * @param usVo
	 * @return
	 */
	public int InsertJoinInfo(UserVO usVo) throws RemoteException;
	
	public List<JoinVO> setJoinTable(JoinVO jvo) throws RemoteException;
	
}
