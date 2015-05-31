package org.hxy.pl.service.bd;

import com.github.abel533.echarts.Legend;
import com.github.abel533.echarts.Option;
import com.github.abel533.echarts.Title;
import com.github.abel533.echarts.Tooltip;
import com.github.abel533.echarts.code.Orient;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.code.X;
import com.github.abel533.echarts.data.PieData;
import com.github.abel533.echarts.series.Pie;
import com.google.common.collect.Lists;
import org.hxy.pl.dao.bd.TeamDao;
import org.hxy.pl.vo.bd.TeamVO;
import org.hxy.pl.vo.common.JqGridData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamService {

	@Autowired
	private TeamDao teamDao;
	
	public int saveTeam(TeamVO team){
		return teamDao.saveTeam(team);
	}

    public List<TeamVO> findTeams(TeamVO teamVO){
        return  teamDao.findTeamsByConditions(teamVO);
    }

    public JqGridData<TeamVO> findTeamPageList(TeamVO teamVO,int pageNo,int pageSize){
        List<TeamVO> teamVOs = teamDao.findTeamPageListBySize(teamVO,pageNo,pageSize);
        int teamCount =  teamDao.findTeamCount(teamVO);
        int totalPage = (teamCount%pageSize)==0?teamCount/pageSize:teamCount/pageSize+1;
        JqGridData jqGridData = new JqGridData(totalPage,pageNo,teamCount,teamVOs);
        return  jqGridData;
    }

    public int deleteTeamById (TeamVO teamVO){
        return teamDao.deleteTeamById(teamVO);
    }

    public  int updateTeam(TeamVO teamVO){
        return teamDao.updateTeam(teamVO);
    }

    public List<TeamVO> findTeamsByCountryId(Long countryId){
        return  teamDao.findTeamByCountry(countryId);
    }

    public Option countTeamWDF(Long seasonId,String teamName){
        TeamVO teamVO =  teamDao.selectTeamByName(teamName);
        TeamVO countTeam =  teamDao.countWDFBySeason(teamVO,seasonId);
        Option option = new Option();
        Title title = new Title();
        Legend legend = new Legend();
        legend.setOrient(Orient.vertical);
        legend.setX(X.left);
        String[] legendTest = new String[]{"主场胜","主场平","主场负","客场胜","客场平","客场负"};
        legend.data(legendTest);
        option.legend(legend);
        Tooltip tooltip =  new Tooltip();
        tooltip.setTrigger(Trigger.item);
        tooltip.formatter("{a} <br/>{b} : {c} ({d}%)");
        option.setTooltip(tooltip);
        if(countTeam !=null){
            title.setText(countTeam.getName());
            title.setX(X.center);
            option.setTitle(title);
            Pie pie = new Pie("场次");
            pie.data(new PieData("主场胜",countTeam.getHomeWin()));
            pie.data(new PieData("主场平",countTeam.getHomeDraw()));
            pie.data(new PieData("主场负",countTeam.getHomeLose()));
            pie.data(new PieData("客场胜",countTeam.getCustomWin()));
            pie.data(new PieData("客场平",countTeam.getCustomDraw()));
            pie.data(new PieData("客场负",countTeam.getCustomLose()));
            option.series(pie);
        }
        return  option;
    }

}
