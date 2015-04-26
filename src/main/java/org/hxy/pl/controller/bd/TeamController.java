package org.hxy.pl.controller.bd;

import java.util.List;

import org.hxy.pl.service.bd.CountryService;
import org.hxy.pl.service.bd.TeamService;
import org.hxy.pl.vo.bd.CountryVO;
import org.hxy.pl.vo.bd.TeamVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/bd/team")
public class TeamController {
	@Autowired
	private CountryService countryService;
	@Autowired
	private TeamService teamService;
	@RequestMapping("/showAddTeam")
	public String showAddTeamPage(Model model ){
		showAllContry(model);
		return "bd/addTeam";
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/saveTeam")
	public  String saveTeam(@ModelAttribute("teamVO") TeamVO teamVO,Model model){
		int result = teamService.saveTeam(teamVO);
		if(result ==1){
			model.addAttribute("result","1");
		}else{
			model.addAttribute("result","0");
		}
		showAllContry(model);
		return "bd/addTeam";
	}
	
	@RequestMapping(method = RequestMethod.GET ,value="/showEdit")
	public String showEditPage(Model model){
		showAllContry(model);
		return "bd/eddTeam";
	}
    @RequestMapping(method = RequestMethod.POST,value = "/showEdit")
	@ResponseBody
    public List<TeamVO> showTeams(@ModelAttribute("teamVO")TeamVO teamVO){
        List<TeamVO> teamVOs = teamService.findTeams(teamVO);
        return teamVOs;
    }
	private void showAllContry(Model model ){
		List<CountryVO> countryList = countryService.showAllContry();
		model.addAttribute("countryList", countryList);
	}
	
}
