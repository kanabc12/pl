package org.hxy.pl.controller.bd;

import java.util.List;

import org.hxy.pl.common.web.upload.FileUploadUtils;
import org.hxy.pl.service.bd.CountryService;
import org.hxy.pl.service.bd.TeamService;
import org.hxy.pl.vo.bd.CountryVO;
import org.hxy.pl.vo.bd.TeamVO;
import org.hxy.pl.vo.common.JqGridData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/bd/team")
public class TeamController {
    @Autowired
    private CountryService countryService;
    @Autowired
    private TeamService teamService;

    @RequestMapping("/showAddTeam")
    public String showAddTeamPage(Model model) {
        showAllContry(model);
        return "bd/addTeam";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/saveTeam")
    public String saveTeam(@ModelAttribute("teamVO") TeamVO teamVO, Model model, HttpServletRequest request, @RequestParam(value = "logo1", required = false) MultipartFile file, BindingResult bindingResult) {
        String name = FileUploadUtils.upload(request,file,bindingResult);
        teamVO.setLogo(name);
        int result = teamService.saveTeam(teamVO);
        if (result == 1) {
            model.addAttribute("result", "1");
        } else {
            model.addAttribute("result", "0");
        }
        showAllContry(model);
        return "bd/addTeam";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/showEdit")
    public String showEditPage(Model model) {
        showAllContry(model);
        return "bd/eddTeam";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/editTeam")
    @ResponseBody
    public String editTeam(@ModelAttribute("teamVO")TeamVO teamVO,@RequestParam(value = "oper") String oper) {
        if("edit".equals(oper)){
            int result = teamService.updateTeam(teamVO);
            if(result == 1)
                return  "success";
        }else if("del".equals(oper)){
            int result = teamService.deleteTeamById(teamVO);
            if(result == 1)
                return  "success";
        }else {

        }
        return "error";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/showEdit")
    @ResponseBody
    public String showTeamPage(@ModelAttribute("teamVO") TeamVO teamVO,@RequestParam(value = "rows") int rows ,@RequestParam(value = "page") int page){
        JqGridData<TeamVO> teamVOJqGridData = teamService.findTeamPageList(teamVO,page,rows);
        teamVOJqGridData.setDateFormat("yyyy-MM-dd");
    return teamVOJqGridData.getJsonString();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getCountry")
    @ResponseBody
    public CountryVO getCountry(Long id){
        CountryVO countryVO = countryService.getCountryById(id);
        return countryVO;
    }

    private void showAllContry(Model model) {
        List<CountryVO> countryList = countryService.showAllContry();
        StringBuffer returnStr  =  new StringBuffer();
        for(CountryVO countryVO:countryList){
            returnStr.append(countryVO.getId()+":"+countryVO.getName()+";");
        }
        model.addAttribute("countryList", countryList);
        model.addAttribute("countryStr",returnStr.toString().substring(0,returnStr.length()-1));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/ShowCountry")
    @ResponseBody
    public String showCountry(){
        List<CountryVO> countryList = countryService.showAllContry();
        StringBuffer returnStr  =  new StringBuffer();
        returnStr.append("<select>");
        for(CountryVO countryVO:countryList){
            returnStr.append("<option value=\""+countryVO.getId()+"\""+">"+countryVO.getName()+"</option>");
        }
        returnStr.append("</select>");
        return returnStr.toString();
    }
}
