package vo;

import java.io.Serializable;

public class FileInfoVO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String fileName;	//파일명
	private byte[] fileData;	//파일크기
	
	
	public byte[] getFileData() {
		return fileData;
	}

	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
}
