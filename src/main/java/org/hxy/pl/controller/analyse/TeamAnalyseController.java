package org.hxy.pl.controller.analyse;

import org.hxy.pl.service.bd.CountryService;
import org.hxy.pl.service.bd.TeamService;
import org.hxy.pl.vo.bd.CountryVO;
import org.hxy.pl.vo.bd.TeamVO;
import org.hxy.pl.vo.tree.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @RequestMapping("/show")
    public String showTeamAnalysePage(){
        return "analyse/teamAnalyse";
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

}
