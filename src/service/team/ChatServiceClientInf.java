package service.team;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import vo.ChattingVO;

public interface ChatServiceClientInf extends Remote{
	
	public void setMessage(String msg) throws RemoteException;
		
}
