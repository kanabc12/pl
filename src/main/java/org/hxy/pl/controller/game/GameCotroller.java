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
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 15-5-7.
 */
@Controller
@RequestMapping(value = "/game")
public class GameCotroller {

    @Autowired
    private LeagueService leagueService;

    @Autowired
    private SeasonService seasonService;

    @Autowired
    private TeamService teamService;

    @ModelAttribute("leagueList")
    public List<LeagueVO> getAllLeague(){
        return  leagueService.findAllLeague();
    }
    @ModelAttribute("seasonList")
    public List<SeasonVO> getAllSeason(){
        return seasonService.findAllSeason();
    }

    @ModelAttribute("teamList")
    public List<TeamVO> getTeamByCountry(){
        return teamService.findTeamsByCountryId(1L);//英超球队为初始化球队
    }

    @RequestMapping("/gameInputInit")
    public String showGameAddPage(Model model){
        return "game/addGame";
    }

    @ResponseBody
    @RequestMapping(value = "/findTeamsByCountry",method = RequestMethod.POST)
    public List<TeamVO>findTeamsByCountry(@RequestParam(value = "countryId") Long countryId){
        List<TeamVO> teamVOs = teamService.findTeamsByCountryId(countryId);
        return  teamVOs;
    }

}
