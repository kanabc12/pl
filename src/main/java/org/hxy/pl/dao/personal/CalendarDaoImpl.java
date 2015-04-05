package org.hxy.pl.dao.personal;

import org.hxy.pl.dao.BaseDao;
import org.hxy.pl.vo.personal.CalendarVO;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
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
}
