package org.hxy.pl.controller.personal;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.hxy.pl.service.personal.CalendarService;
import org.hxy.pl.vo.personal.CalendarVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String showNewForm(
            @RequestParam(value = "start", required = false) @DateTimeFormat(pattern = dataFormat) Date start,
            @RequestParam(value = "end", required = false) @DateTimeFormat(pattern = dataFormat) Date end,
            Model model) {

        setColorList(model);

        CalendarVO calendar = new CalendarVO();
        calendar.setLength(1);
        if(start != null) {
            calendar.setStartDate(start);
            calendar.setLength((int)Math.ceil(1.0 * (end.getTime() - start.getTime()) / oneDayMillis));
            if(DateUtils.isSameDay(start, end)) {
                calendar.setLength(1);
            }
            if(!"00:00:00".equals(DateFormatUtils.format(start, "HH:mm:ss"))) {
                calendar.setStartTime(start);
            }
            if(!"00:00:00".equals(DateFormatUtils.format(end, "HH:mm:ss"))) {
                calendar.setEndTime(end);
            }

        }
        model.addAttribute("model", calendar);
        return "index/newForm";
    }
    
    private void setColorList(Model model) {
        List<String> backgroundColorList = Lists.newArrayList();
        backgroundColorList.add("#A0A0A0");
        backgroundColorList.add("#82AF6F");
        backgroundColorList.add("#D15B47");
        backgroundColorList.add("#9585BF");
        backgroundColorList.add("#FEE188");
        backgroundColorList.add("#D6487E");
        backgroundColorList.add("#3A87AD");
        model.addAttribute("backgroundColorList", backgroundColorList);
    }
    
}
