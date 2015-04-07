package org.hxy.pl.service.personal;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import org.hxy.pl.dao.personal.CalendarDao;
import org.hxy.pl.vo.personal.CalendarVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by Administrator on 15-4-5.
 */
@Service
public class CalendarService {
    private final Logger logger = LoggerFactory.getLogger(CalendarService.class);

    @Autowired
    private CalendarDao calendarDao;
    public void copyAndRemove(CalendarVO calendar) {
    	calendarDao.delete(calendar);
    	CalendarVO copyCalendar = new CalendarVO();
    	BeanUtils.copyProperties(calendar, copyCalendar);
    	copyCalendar.setId(null);
    	calendarDao.addCalendar(copyCalendar);
    }
    public Long countRecentlyCalendar(Long userId, Integer interval) {
    	Date nowDate = new Date();
        Date nowTime = new Time(nowDate.getHours(), nowDate.getMinutes(), nowDate.getSeconds());
        nowDate.setHours(0);
        nowDate.setMinutes(0);
        nowDate.setSeconds(0);
        return calendarDao.countRecentlyCalendar(userId, nowDate, nowTime, interval);
    }
    
    public List<CalendarVO> showAllCalendar(Long userId){
    	return calendarDao.findAllCalendar(userId);
    }

}
