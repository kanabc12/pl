package org.hxy.pl.controller.game;

import org.hxy.pl.service.bd.LeagueService;
import org.hxy.pl.service.bd.SeasonService;
import org.hxy.pl.service.bd.TeamService;
import org.hxy.pl.vo.bd.LeagueVO;
import org.hxy.pl.vo.bd.SeasonVO;
import org.hxy.pl.vo.bd.TeamVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by Administrator on 15-5-12.
 */
@Controller
@RequestMapping(value = "/odd")
public class OddController {

    @Autowired
    private SeasonService seasonService;
    @Autowired
    private LeagueService leagueService;
    @Autowired
    private TeamService teamService;

    @RequestMapping("/addOdd")
    public String showAddOdd(Model model){
        List<SeasonVO> seasonVOs =  seasonService.findAllSeason();
        List<LeagueVO> leagueVOs = leagueService.findAllLeague();
        List<TeamVO>  teamVOList = teamService.findTeamsByCountryId(1L);//英超球队为初始化球队
        model.addAttribute("teamList",teamVOList);
        model.addAttribute("seasonList",seasonVOs);
        model.addAttribute("leagueList",leagueVOs);
        return "game/addOdd";
    }
}
