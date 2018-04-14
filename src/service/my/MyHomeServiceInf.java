package service.my;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import vo.TeamVO;
import vo.UserVO;
/**
 * server MyHome 페이지와 연결된 서비스 인터페이스
 * @author sun
 *
 */
public interface MyHomeServiceInf extends Remote{

	/**
	 * 유저아이디를 매개변수로 가입된 유저의 정보를 가져옴
	 * @param us_id 유저아이디
	 * @return 해당 아이디에 대한 유저의가입 정보 UserVO 
	 */
	public UserVO getUserInfo(String us_id) throws RemoteException;
	
	/**
	 *  유저아디를 매개변수로 가입된 유저의 정보를 변경함
	 * @param us_id
	 * @return 변경된 유저의 정보를 유저아디로 return
	 * @throws RemoteException 
	 */
	public  int updateUser(UserVO vo) throws RemoteException;

	/**
	 * 유저아이디를 매개변수로 가입된 유저의 탈퇴 컬럼을 'Y'로 수정
	 * @param us_id
	 * @return 성공시 true
 	 * @throws RemoteException
	 */
	public boolean deleteUser(String us_id) throws RemoteException;
	
}
