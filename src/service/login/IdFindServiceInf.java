package service.login;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import vo.UserVO;

public interface IdFindServiceInf extends Remote{
	
	/**
	 * 입력한 회원정보가 디비에 있는지 확인
	 * @param userNm
	 * @return
	 */
	public List<UserVO> getUserInfo(String userNm) throws RemoteException;
}
