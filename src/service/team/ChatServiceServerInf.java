package service.team;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import vo.ChattingVO;
import vo.ReportVO;

public interface ChatServiceServerInf extends Remote{
	
	public void insertChat(ChattingVO vo) throws RemoteException;
	
	public String chatTeamId(String tm_id) throws RemoteException;
	
	public List<ChattingVO> getAllChat() throws RemoteException;
	
	public List<ChattingVO> getTeamChat(String tm_id) throws RemoteException;
	
	public void setMessage(String tm_id, String msg) throws RemoteException;
	
	public List<String> memList(String tm_id) throws RemoteException;
	
	public boolean userReport(ReportVO vo) throws RemoteException;
	
	public void exitChat(String us_id) throws RemoteException;
	
	public boolean makeChat(String ct_tm_id) throws RemoteException;
	
	public void joinChat(String ct_tm_id, ChatServiceClientInf client) throws RemoteException;

	public void excel(String tm_id,List<ChattingVO> list) throws RemoteException;
	
}
