package service.team;

import java.rmi.Remote;
import java.rmi.RemoteException;

import vo.FileInfoVO;
import vo.FileVO;
import vo.PhbVO;

/**
 * server 사진첩 upload, reUpload 서비스 인터페이스
 * @author sk.Back
 *
 */
public interface TeamPHBUploadServiceInf extends Remote {
	
	/**
	 * 사진첩정보를 db에 insert
	 * @param PhbVO
	 * @return 성공시 1이상, 실패시 0
	 * @throws RemoteException
	 * @author 백선경
	 */
	public int phbInsert(PhbVO phbVo) throws RemoteException;

	
	/**
	 * user가 올리는 이미지 정보를 DB에 insert
	 * @param FileVO
	 * @return 성공시 1이상, 실패시 0
	 * @throws RemoteException
	 * @author 백선경
	 */
	public int phbFileInsert(FileVO fileVo) throws RemoteException;
	
	/**
	 * user가 수정한 이미지 정보를 DB에 update
	 * ReamPTBReUpLoad 버튼 액션버튼에 사용
	 * @param FileVO
	 * @return 성공시 1이상, 실패시 0
	 * @throws RemoteException
	 * @author 백선경
	 */
	public int phbFileupdate(FileVO fileVo) throws RemoteException;
	
	/**
	 * 사진첩에 올린 사진첩 게시물 삭제(delete)
	 * @param 삭제할 사진첩 게시물의 id
	 * @return 성공시 1이상, 실패시 0
	 * @throws RemoteException
	 * @author 백선경
	 */
	public int phbDel(String phb_id) throws RemoteException;
	
	/**
	 * 가장 최근에 저장된 사진첩에 저장된 사진첩id를 가져옴(select)
	 * @return 사진첩ID
	 * @throws RemoteException
	 * @author 백선경
	 */
	public String getPhbID() throws RemoteException;
	
	/**
	 * 가장 최근에 저장된 사진첩 정보를 가져옴(select)
	 * @return 사진첩 정보(팀아이디로 저장 경로지정)
	 * @param 방금 저장한 사진첩 ID
	 * @throws RemoteException
	 * @author 백선경
	 */
	public PhbVO selectPhbVo(String phb_id) throws RemoteException;
	
	/**
	 * 서버에 file이름과 팀아이디를 이용하여 파일을 만듬
	 * @param FileInfoVO
	 * @param 팀아이디
	 * @throws RemoteException
	 */
	public FileInfoVO setFiles(FileInfoVO info, String tm_id) throws RemoteException;

}
