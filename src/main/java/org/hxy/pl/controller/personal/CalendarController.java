package org.hxy.pl.controller.personal;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.hxy.pl.service.personal.CalendarService;
import org.hxy.pl.vo.personal.CalendarVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;


import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 15-4-4.
 */
@Controller
@RequestMapping(value = "/personal/calendar")
public class CalendarController {
    private static final long oneDayMillis = 24L * 60 * 60 * 1000;
    private static final String dataFormat = "yyyy-MM-dd HH:mm:ss";
    
    @Autowired
    private CalendarService calendarService;
    
    @RequestMapping("/load")
    @ResponseBody
    public Collection<Map> ajaxLoad(){
    	Long userId = 10000L;
    	List<CalendarVO> calendarList =  calendarService.showAllCalendar(userId);
    	 return Lists.<CalendarVO, Map>transform(calendarList, new Function<CalendarVO, Map>() {
             @Override
             public Map apply(CalendarVO c) {
                 Map<String, Object> m = Maps.newHashMap();

                 Date startDate = new Date(c.getStartDate().getTime());
                 Date endDate = DateUtils.addDays(startDate, c.getLength() - 1);
                 boolean allDays = c.getStartTime() == null && c.getEndTime() == null;

                 if(!allDays) {
                     startDate.setHours(c.getStartTime().getHours());
                     startDate.setMinutes(c.getStartTime().getMinutes());
                     startDate.setSeconds(c.getStartTime().getSeconds());
                     endDate.setHours(c.getEndTime().getHours());
                     endDate.setMinutes(c.getEndTime().getMinutes());
                     endDate.setSeconds(c.getEndTime().getSeconds());
                 }

                 m.put("id", c.getId());
                 m.put("start", DateFormatUtils.format(startDate, "yyyy-MM-dd HH:mm:ss"));
                 m.put("end", DateFormatUtils.format(endDate, "yyyy-MM-dd HH:mm:ss"));
                 m.put("allDay", allDays);
                 m.put("title", c.getTitle());
                 m.put("details", c.getDetails());
                 if(StringUtils.isNotEmpty(c.getBackgroundColor())) {
                     m.put("backgroundColor", c.getBackgroundColor());
                     m.put("borderColor", c.getBackgroundColor());
                 }
                 if(StringUtils.isNotEmpty(c.getTextColor())) {
                     m.put("textColor", c.getTextColor());
                 }
                 return m;
             }
         });
    }
    
    
}
