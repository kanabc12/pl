package org.hxy.pl.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 15-6-19.
 */
@Component(value = "timerTaskTest")
public class TimerTaskTest {
//    @Scheduled(cron = "0/5 * * * * ?")
    public void test(){
        System.out.println("aaaa");
    }
}
