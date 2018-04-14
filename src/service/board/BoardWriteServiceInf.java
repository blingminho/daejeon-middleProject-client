package service.board;

import java.rmi.server.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import vo.BoardVO;
import vo.FileInfoVO;
import vo.FileVO;

public interface BoardWriteServiceInf extends Remote{

	/**
	 * BoardVO를 파라미터로 넘겨줘서 DB에 저장
	 * @param bvo
	 * @return
	 * @throws RemoteException
	 */
	public int setBoardWrite(BoardVO bvo) throws RemoteException;
	
	/**
	 * 파일시퀀스번호를 얻어와서 이미지저장에 사용
	 * @return
	 * @throws RemoteException
	 */
	public String getFileSeqId() throws RemoteException;
	
	/**
	 * 게시판시퀀스번호를 얻어와서 이미지저장에 사용
	 * @return
	 * @throws RemoteException
	 */
	public String getBoardSeqId() throws RemoteException;
	
	
	/**
	 * 서버pc에 이미지 저장
	 * 게시글을 읽을 때 이미지를 불러오기 위해
	 * @param info
	 * @throws RemoteException
	 */
	public void setFiles(FileInfoVO[] info) throws RemoteException;
	
	/**
	 * FileVO를 파라미터값으로 넘겨줘서 db에 저장
	 * @param fvo
	 * @return
	 * @throws RemoteException
	 */
	public void setBoardWriteFile(List<FileVO> fileList) throws RemoteException;
}
