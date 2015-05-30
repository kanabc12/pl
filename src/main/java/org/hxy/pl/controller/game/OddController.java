package org.hxy.pl.controller.game;

import org.hxy.pl.service.bd.LeagueService;
import org.hxy.pl.service.bd.SeasonService;
import org.hxy.pl.service.bd.TeamService;
import org.hxy.pl.service.game.GameService;
import org.hxy.pl.service.game.OddService;
import org.hxy.pl.vo.bd.LeagueVO;
import org.hxy.pl.vo.bd.SeasonVO;
import org.hxy.pl.vo.bd.TeamVO;
import org.hxy.pl.vo.common.PageModel;
import org.hxy.pl.vo.game.OddVO;
import org.hxy.pl.vo.game.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    @Autowired
    private GameService gameService;
    @Autowired
    private OddService oddService;

    @RequestMapping("/addOdd")
    public String showAddOdd(Model model) {
        List<SeasonVO> seasonVOs = seasonService.findAllSeason();
        List<LeagueVO> leagueVOs = leagueService.findAllLeague();
        List<TeamVO> teamVOList = teamService.findTeamsByCountryId(1L);//英超球队为初始化球队
        model.addAttribute("teamList", teamVOList);
        model.addAttribute("seasonList", seasonVOs);
        model.addAttribute("leagueList", leagueVOs);
        return "game/addOdd";
    }

    @RequestMapping(value = "/queryResult", method = RequestMethod.POST)
    @ResponseBody
    public PageModel queryResult(@ModelAttribute("resultVO") ResultVO resultVO, @RequestParam(value = "page", defaultValue = "1") int pageNO, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        PageModel pageModel = gameService.findResultsPageList(resultVO, pageNO, pageSize);
        return pageModel;
    }
    @RequestMapping(value = "/saveOdd",method = RequestMethod.POST)
    @ResponseBody
    public int saveOdd(@ModelAttribute("oddVO")OddVO oddVO){
        return oddService.saveOdd(oddVO);
    }

    @RequestMapping(value = "/getOddsByGameId", method = RequestMethod.POST)
    @ResponseBody
    public Map getOddsByGameId(@RequestParam(value = "gameId") Integer gameId){
        Map<String,List<OddVO>> resultMap = oddService.getOddsByGameId(gameId);
        return resultMap;
    }
    @RequestMapping(value = "/showOdd", method = RequestMethod.GET)
    public String showOdd(){
        return "game/showOdd";
    }

}
