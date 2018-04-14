package service.board;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import vo.BoardVO;
import vo.FileInfoVO;

public interface BoardHomeServiceInf extends Remote{

	/**
	 * Board테이블의 모든 정보를 불러온다.
	 * @return
	 * @throws RemoteException
	 */
	public List<BoardVO> getAllBoard() throws RemoteException;
	
	/**
	 * 검색어에 입력한 제목내용과 관련된 정보를 뽑아옴
	 * @param search
	 * @return
	 * @throws RemoteException
	 */
	public List<BoardVO> getBoardTitle(String search) throws RemoteException;
	
	/**
	 * 검색어에 입력한 내용과 관련된 정보를 뽑아옴
	 * @param search
	 * @return
	 * @throws RemoteException
	 */
	public List<BoardVO> getBoardContent(String search) throws RemoteException;
	
	/**
	 * 검색어에 입력한 id와 관련된 정보를 뽑아옴
	 * @param search
	 * @return
	 * @throws RemoteException
	 */
	public List<BoardVO> getBoardUserId(String search) throws RemoteException;
	
	
	public FileInfoVO[] setFiles(FileInfoVO[] info) throws RemoteException;
	public FileInfoVO[] getFiles() throws RemoteException;
}
