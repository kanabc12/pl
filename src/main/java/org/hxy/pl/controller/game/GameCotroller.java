package org.hxy.pl.controller.game;

import org.hxy.pl.common.Constants;
import org.hxy.pl.service.bd.LeagueService;
import org.hxy.pl.service.bd.SeasonService;
import org.hxy.pl.service.bd.TeamService;
import org.hxy.pl.service.game.GameService;
import org.hxy.pl.vo.bd.LeagueVO;
import org.hxy.pl.vo.bd.SeasonVO;
import org.hxy.pl.vo.bd.TeamVO;
import org.hxy.pl.vo.game.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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

    @Autowired
    private GameService gameService;

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

    @RequestMapping(value = "/saveGame",method = RequestMethod.POST)
    public String saveGameResult(@ModelAttribute("resultVO")ResultVO resultVO,Model model){
        int result;
        String resultStr;
        resultStr = resultVO.getHomeGoals()+":" + resultVO.getCustomGoals();
        resultVO.setResultStr(resultStr);
        if(resultVO.getHomeGoals()>resultVO.getCustomGoals()){
            resultVO.setResultType(1);
        }else if(resultVO.getHomeGoals()<resultVO.getCustomGoals()){
            resultVO.setResultType(-1);
        }else{
            resultVO.setResultType(0);
        }
        Date  currentDate = new Date();
        if(resultVO.getActualTime() != null){
            if(currentDate.getTime()>resultVO.getActualTime().getTime()){
                resultVO.setGameStatus(1);//已赛
            }else{
                resultVO.setGameStatus(0);//未赛
            }
        }
        resultVO.setHomeSecGoals(resultVO.getHomeGoals()-resultVO.getHomeHalfGoals());
        resultVO.setCustomSecGoals(resultVO.getCustomGoals()-resultVO.getCustomHalfGoals());
        result = gameService.saveGameResult(resultVO);
        if(result == 0){
            model.addAttribute(Constants.ERROR, "保存失败，请重新填写");
        }else{
            model.addAttribute(Constants.MESSAGE,"保存成功");
        }
        return "game/addGame";
    }

    @RequestMapping(value = "/getResultById",method = RequestMethod.POST)
    @ResponseBody
    public ResultVO getResultById(@RequestParam("gameId")Integer gameId){
        ResultVO resultVO = gameService.getResultById(gameId);
        return  resultVO;
    }

}
