package org.hxy.pl.controller.personal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Administrator on 15-4-4.
 */
@Controller
@RequestMapping(value = "/personal/calendar")
public class CalendarController {
    private static final long oneDayMillis = 24L * 60 * 60 * 1000;
    private static final String dataFormat = "yyyy-MM-dd HH:mm:ss";
}
