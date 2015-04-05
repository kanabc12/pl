package org.hxy.pl.dao.personal;

import java.util.Date;

/**
 * Created by Administrator on 15-4-4.
 */
public interface CalendarDao {
    Long countRecentlyCalendar(Long userId, Date nowDate, Date nowTime, Integer interval);
}
