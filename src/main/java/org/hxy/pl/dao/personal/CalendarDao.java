package org.hxy.pl.dao.personal;

import java.util.Date;
import java.util.List;

import org.hxy.pl.vo.personal.CalendarVO;

/**
 * Created by Administrator on 15-4-4.
 */
public interface CalendarDao {
    Long countRecentlyCalendar(Long userId, Date nowDate, Date nowTime, Integer interval);
    void delete(CalendarVO  calendar);
    CalendarVO  addCalendar(CalendarVO calendar);
    List<CalendarVO> findAllCalendar(Long userId);
    
    
}
