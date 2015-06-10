package org.hxy.pl.controller.analyse;

import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.json.GsonOption;
import com.google.gson.Gson;
import org.hxy.pl.service.bd.CountryService;
import org.hxy.pl.service.bd.SeasonService;
import org.hxy.pl.service.bd.TeamService;
import org.hxy.pl.service.game.GameService;
import org.hxy.pl.vo.bd.CountryVO;
import org.hxy.pl.vo.bd.SeasonVO;
import org.hxy.pl.vo.bd.TeamVO;
import org.hxy.pl.vo.game.ResultVO;
import org.hxy.pl.vo.tree.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 15-5-28.
 */
@Controller
@RequestMapping("/analyse/team")
public class TeamAnalyseController {
    @Autowired
    private TeamService teamService;
    @Autowired
    private CountryService countryService;

    @Autowired
    private GameService gameService;

    @Autowired
    private SeasonService seasonService;

    @RequestMapping("/show")
    public String showTeamAnalysePage(){
        return "analyse/teamAnalyse";
    }

    @ModelAttribute("seasonList")
    public List<SeasonVO> getAllSeason(){
        return seasonService.findAllSeason();
    }

    @RequestMapping(value = "/getTreeData",method = RequestMethod.POST)
    @ResponseBody
    public TreeRespVO getTreeData(@RequestParam("id") Long countryId){
        TreeRespVO vo = new TreeRespVO();
        if(countryId ==0){
            List<CountryVO> countryVOs = countryService.showAllContry();
            List<Item> voItemList = new ArrayList<Item>();
            for(CountryVO countryVO : countryVOs){
                Item item = new Item();
                List<TeamVO> teamVOs = teamService.findTeamsByCountryId(countryVO.getId());
                int childCount = teamVOs.size();
                item.setText(countryVO.getName());
                item.setState(new State());
                ArrayList<String> tags = new ArrayList<String>();
                tags.add(""+childCount);
                item.setTags(tags);
                if(childCount>0){
                    List<Item> nodes = new ArrayList<Item>();
                    for(TeamVO teamVO:teamVOs){
                        Item teamItem = new Item();
                        teamItem.setText(teamVO.getName());
                        nodes.add(teamItem);
                    }
                    item.setNodes(nodes);
                }else{
                }
                voItemList .add(item );
            }
            vo.setData( voItemList );
        }else{
            List<TeamVO> teamVOs = teamService.findTeamsByCountryId(countryId);
        }
        return  vo;
    }

    @RequestMapping(value = "/getTeamWDF",method = RequestMethod.POST)
    @ResponseBody
    public Option getTeamWDF(@RequestParam(value = "seasonId")Long seasonId,@RequestParam(value = "teamName") String teamName){
        Option option = teamService.countTeamWDF(seasonId,teamName);
        return  option;
    }

    @RequestMapping(value = "/getTeamWDFAndYM",method = RequestMethod.POST)
    @ResponseBody
    public String getTeamWDFAndYM(@RequestParam(value = "seasonId")Long seasonId,@RequestParam(value = "teamName") String teamName){
        GsonOption barOption = teamService.countWDFBySeasonAndYM(seasonId,teamName);
        return  barOption.toString();
    }
    @RequestMapping(value = "/getTeamWDFDetail",method = RequestMethod.POST)
    @ResponseBody
    public List<ResultVO> getResultsByTeam(@RequestParam(value = "resultType")Integer resultType,@RequestParam(value = "seasonId")Integer seasonId,@RequestParam(value = "teamName") String teamName){
        return  gameService.getResultsByTeam(teamName,seasonId,resultType);
    }
    @RequestMapping(value = "/getTeamWDFDetailByMonth",method = RequestMethod.POST)
    @ResponseBody
    public List<ResultVO> getResultsByTeamAndYM(@RequestParam(value = "resultType")Integer resultType,@RequestParam(value = "seasonId")Integer seasonId,@RequestParam(value = "teamName") String teamName,@RequestParam(value = "ym") String ym){
        return  gameService.getResultsByTeamAndYM(teamName,seasonId,resultType,ym);
    }


}
