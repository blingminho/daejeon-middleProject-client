package service.team;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import vo.TravelListVO;
/**
 * 팀 여행지 즐겨찾기와 관련된 서비스 인터페이스
 * @author Jun
 *
 */
public interface TeamTravelServiceInf extends Remote{

	/**
	 * 팀 아이디를 제공하여 해당 팀의 여행지 즐겨찾기 목록을 가져옴
	 * @param 팀 아이디
	 * @return 여행지 즐겨찾기 리스트
	 */
	public List<TravelListVO> getTravelList(String trv_tm_id) throws RemoteException;
	
	/**
	 * 여행지 즐겨찾기 아이디를 제공하여 일정 테이블에 있는 여행지와 일치하는 경우에 행의 수를 반환한다
	 * @param 여행지 즐겨찾기 아이디
	 * @param 팀 아이디
	 * @return 일정테이블에 있는 여행지의 수를 가져옴
	 */
	public int getTravelOnSchedule(String cal_tm_id, String cal_trv_id) throws RemoteException;
	
	/**
	 * 여행지아이디를 제공하여 여행지 및 일정 테이블에서 제거
	 * @param trv_id 여행지아이디
	 * @return 성공시 true
	 */
	public boolean deleteTravel(String trv_id) throws RemoteException;
	
	/**
	 * 여행지를 제공하여 여행지 즐겨찾기 테이블에 추가
	 * @param travelVO 여행지(여행지 아이디를 제외한)
	 * @return 성공시 true
	 */
	public boolean insertTravel(TravelListVO travelVO) throws RemoteException;
}
