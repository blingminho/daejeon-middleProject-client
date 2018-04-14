package service.team;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import vo.FileInfoVO;
import vo.PhbVO;
/**
 * 사진첩 목록 서비스 인터페이스
 * @author Jun
 *
 */
public interface TeamPHBServiceInf extends Remote{
	/**
	 * 서버로부터 FileInfoVO배열을 받음
	 * @param 팀아이디
	 * @return 초기화된 FileInfoVO배열
	 * @throws RemoteException
	 */
	public FileInfoVO[] getFiles(String tm_id) throws RemoteException;
	
	/**
	 * 서버로부터 받은 FileInfoVO[]과 팀아이디를 이용하여 파일을 만듬
	 * @param FileInfoVO[]
	 * @param 팀아이디
	 * @return 초기화된 FileInfoVO배열
	 * @throws RemoteException
	 */
	public FileInfoVO[] setFiles(FileInfoVO[] info, String tm_id) throws RemoteException;
	
	/**
	 * 관리자가 모든 사진첩의 정보를 가져올때 사용
	 * @return 모든 사진첩 리스트
	 */
	public List<PhbVO> getAllPhb() throws RemoteException;

	/**
	 * 관리자가 모든 사진첩의 정보를 가져올때 사용
	 * @return 모든 사진첩 리스트
	 */
	
	/**
	 * 팀원이 팀아이디를 이용하여 팀의 사진첩 리스트 가져옴
	 * @param 팀아이디를 담은 PhbVO
	 * @return 해당 팀의 모든 사진첩 리스트
	 */
	public List<PhbVO> getPhb(PhbVO phbvo) throws RemoteException;
	
	/**
	 * 글쓴이 또는 관리자가 사진을 지울때 사용
	 * @param 사진첩아이디를 담은 PhbVO
	 * @return 성공시 1 반환
	 */
	public boolean deletePhb(PhbVO phbvo) throws RemoteException;
	
	/**
	 * 좋아요수를 증가시킬때 사용
	 * @param 사진첩아이디를 담은 PhbVO
	 * @return 성공시 1 반환
	 */
	public boolean updateGood(PhbVO phbvo) throws RemoteException;
}
