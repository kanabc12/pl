package org.hxy.pl.controller.front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 15-3-23.
 */
@Controller
public class IndexController {
    @RequestMapping(value = "index")
    public String index(){
        return "index/index";
    }
}
