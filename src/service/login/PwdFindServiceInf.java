package service.login;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import vo.UserVO;

public interface PwdFindServiceInf extends Remote{

	public List<UserVO> getUserInfo(String userId) throws RemoteException;
	
	public int updatePwd(UserVO userPwd) throws RemoteException;
}
