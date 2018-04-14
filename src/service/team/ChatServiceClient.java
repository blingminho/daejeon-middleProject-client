package service.team;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextArea;
import vo.ChattingVO;

public class ChatServiceClient extends UnicastRemoteObject implements ChatServiceClientInf {

	private TextArea textArea;
	
	public ChatServiceClient() throws RemoteException {
	}
	
	public void setTextArea(TextArea textArea) {
		this.textArea = textArea;
	}
	
	@Override
	public void setMessage(String msg) throws RemoteException {
		System.out.println("clinet setMessage : " + msg);
		if(this.textArea != null) {	
//			textArea.setScrollTop(Double.MAX_VALUE);
			textArea.setText(textArea.getText()+"\n"+msg);
			
			textArea.selectPositionCaret(textArea.getLength());
			textArea.deselect(); //removes the highlighting
		}

	}
	
	
	public void unexport() {
		try {
			UnicastRemoteObject.unexportObject(this, true);
		} catch (NoSuchObjectException e) {
			e.printStackTrace();
		}
	}


}
