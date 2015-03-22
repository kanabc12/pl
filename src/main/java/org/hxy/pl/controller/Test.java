package org.hxy.pl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 15-3-22.
 */
@Controller
public class Test {

    @RequestMapping(value = "/test")
    public String test(){
        System.out.println("Hello!");
        return "test/test";
    }
}
