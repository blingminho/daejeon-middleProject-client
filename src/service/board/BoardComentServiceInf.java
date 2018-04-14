package service.board;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import vo.BoardVO;
import vo.ComentVO;
import vo.FileVO;
import vo.UserVO;

public interface BoardComentServiceInf extends Remote{
	
	/**
	 * 유저 아이디를 받아서 게시판 정보를 얻어옴
	 * @param userId
	 * @return
	 * @throws RemoteException
	 */
	public String getBoardInfo(String userId) throws RemoteException;
	
	/**
	 * 입력한 댓글정보를 vo에 담아서 db에 넣기
	 * @param cvo
	 * @return 1이면 성공 0이면 실패
	 * @throws RemoteException
	 */
	public int setComent(ComentVO cvo) throws RemoteException;
	
	/**
	 * 유저id로 유저테이블의 정보를 읽어온다.
	 * @param us_id
	 * @return
	 * @throws RemoteException
	 */
	public List<UserVO> getUserInfo(String us_id) throws RemoteException;
	
	/**
	 * 현재 게시판등록id와 동일한 댓글들을 뽑아온다
	 * @param bd_id
	 * @return
	 * @throws RemoteException
	 */
	public List<ComentVO> getComentInfo(String bd_id) throws RemoteException;
	
	/**
	 * 게시글을 작성한 유저아이디를 받아서 정보를 삭제
	 * @param us_id
	 * @return
	 * @throws RemoteException
	 */
	public int deleteBoard(String bd_id) throws RemoteException;
	
	/**
	 * 게시글 내용을 수정하고 게시판id를 읽어 동일하면 바꾼다.
	 * @param bd_content
	 * @return
	 * @throws RemoteException
	 */
	public int updateBoard(BoardVO bvo) throws RemoteException;
	
	public List<FileVO> getFile(String id) throws RemoteException;

}