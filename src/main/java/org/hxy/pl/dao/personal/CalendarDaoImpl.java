package org.hxy.pl.dao.personal;

import org.hxy.pl.dao.BaseDao;
import org.hxy.pl.vo.personal.CalendarVO;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 15-4-4.
 */
@Repository
public class CalendarDaoImpl extends BaseDao<CalendarVO> implements CalendarDao{

	@Override
    public Long countRecentlyCalendar(Long userId, Date nowDate, Date nowTime, Integer interval) {
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("userId",userId);
        paramMap.put("startDate",nowDate);
        paramMap.put("endTime",nowTime);
        paramMap.put("interval",interval);
        return (Long)super.selectOne(generateStatement("countRecentlyCalendar"),paramMap);
    }
	@Override
    public void delete(CalendarVO  calendar){
    	Map<String,Object> map = new HashMap<>();
    	map.put("id",calendar.getId());
    	super.delete(generateStatement("deleteCalendar"), map);
    }
	@Override
	public CalendarVO addCalendar(CalendarVO calendar) {
		super.insert(generateStatement("saveCalendar"), calendar);
		return calendar;
	}
	@Override
	public List<CalendarVO> findAllCalendar(Long userId) {
		return super.findList(generateStatement("findAllCalender"), userId);
	}
}
