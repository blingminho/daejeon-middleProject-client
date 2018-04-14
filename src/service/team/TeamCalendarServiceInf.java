package service.team;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import vo.CalendarVO;
import vo.TeamVO;
/**
 * 팀페이지의 일정관리 및
 * 마이페이지의 내 일정에 관련된 DAO 인터페이스
 * @author Jun
 */
public interface TeamCalendarServiceInf extends Remote{
	
	/**
	 * 유저아이디를 제공하여 해당 아이디의 모든 가입된 팀아이디 목록을 가져옴
	 * @param 유저아이디
	 * @return 팀아이디 목록
	 */
	public List<String> getJoinTeamIdList(String jo_us_id) throws RemoteException;

	/**
	 * 팀아이디를 제공하여 해당 팀의 모든 정보를 가져옴
	 * @param 팀아이디
	 * @return 팀정보
	 */
	public TeamVO getTeamVO(String tm_id) throws RemoteException;
	
	/**
	 * 팀아이디를 제공하여 해당 팀아이디의 모든 일정 목록을 가져옴
	 * @param 팀아이디
	 * @return 해당 팀 일정 목록
	 */
	public List<CalendarVO> getCalendarBy_TM_ID(String cal_tm_id) throws RemoteException;
	
	/**
	 * 캘린더vo를 제공하여 해당 캘린더vo를 캘린더 테이블에 insert함
	 * @param 캘린더vo
	 * @return 성공시 true
	 */
	public boolean insertCalendar(CalendarVO calendarVO) throws RemoteException;
	
	/**
	 * 캘린더vo를 제공하여 해당 캘린더vo에 부합하는 캘린더 행의 수를 반환함
	 * @param 캘린더vo
	 * @return 파라미터와 부합하는 행의 수
	 */
	public int getCalendar(CalendarVO calendarVO) throws RemoteException;
	
	/**
	 * 캘린더vo를 제공하여 해당 캘린더vo를 캘린더 테이블에 delete함
	 * @param 캘린더vo
	 * @return 성공시 true
	 */
	public boolean deleteCalendar(CalendarVO calendarVO) throws RemoteException;
	
}
