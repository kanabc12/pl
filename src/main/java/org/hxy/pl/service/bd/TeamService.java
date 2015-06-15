package org.hxy.pl.service.bd;

import com.github.abel533.echarts.*;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.*;
import com.github.abel533.echarts.data.PieData;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Pie;
import org.hxy.pl.dao.bd.TeamDao;
import org.hxy.pl.vo.bd.SeasonVO;
import org.hxy.pl.vo.bd.TeamVO;
import org.hxy.pl.vo.common.JqGridData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TeamService {

	@Autowired
	private TeamDao teamDao;

    @Autowired
    private SeasonService seasonService;
	
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
        option.calculable(true);
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
        option.toolbox().x(X.right).y(Y.center).orient(Orient.vertical).show(true).feature(Tool.mark,
                Tool.dataView,
                new MagicType(Magic.line, Magic.bar, Magic.stack, Magic.tiled),
                Tool.restore,
                Tool.saveAsImage).padding(10);
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
//            pie.radius(new String[]{"50%", "70%"});
//            ItemStyle itemStyle = new ItemStyle();
//            itemStyle.normal().label(new Label().show(false)).labelLine().show(false);
//            itemStyle.emphasis().label().show(true).position("center").textStyle(new TextStyle().fontSize(30).fontFamily("bold"));
//            pie.itemStyle(itemStyle);
            option.series(pie);

        }
        return  option;
    }

    public GsonOption countWDFBySeasonAndYM(Long seasonId,String teamName){
        ArrayList<String> ym = new ArrayList<>();
        ArrayList<Integer> homeWin = new ArrayList<>();
        ArrayList<Integer> homeDraw = new ArrayList<>();
        ArrayList<Integer> homeLose = new ArrayList<>();
        ArrayList<Integer> customWin = new ArrayList<>();
        ArrayList<Integer> customDraw = new ArrayList<>();
        ArrayList<Integer> customLose = new ArrayList<>();
        TeamVO teamVO = teamDao.selectTeamByName(teamName);
        SeasonVO seasonVO =seasonService.findSeasonById(seasonId);
        GsonOption  option = new GsonOption ();
        Title title = new Title();
        title.setText(teamName);
        title.setSubtext(seasonVO.getName());
        title.setX(X.center);
        option.setTitle(title);
        option.tooltip().trigger(Trigger.axis);
        Legend legend = new Legend();
        legend.setOrient(Orient.vertical);
        legend.setX(X.left);
        String[] legendTest = new String[]{"主场胜","主场平","主场负","客场胜","客场平","客场负"};
        legend.data(legendTest);
        option.legend(legend);
        option.toolbox().x(X.right).y(Y.center).orient(Orient.vertical).show(true).feature(Tool.mark,
                Tool.dataView,
                new MagicType(Magic.line, Magic.bar, Magic.stack, Magic.tiled),
                Tool.restore,
                Tool.saveAsImage).padding(10);
        Bar homeWinBar = new Bar("主场胜");
        Bar homeDrawBar = new Bar("主场平");
        Bar homeLoseBar = new Bar("主场负");
        Bar customWinBar = new Bar("客场胜");
        Bar customDrawBar = new Bar("客场平");
        Bar customLoseBar = new Bar("客场负");

        if(teamVO!= null){
            List<TeamVO>  teamVOs = teamDao.countWDFBySeasonAndYM(teamVO,seasonId);
            for(TeamVO teamVO1:teamVOs){
                homeWin.add(teamVO1.getHomeWin());
                homeDraw.add(teamVO1.getHomeDraw());
                homeLose.add(teamVO1.getHomeLose());
                customWin.add(teamVO1.getCustomWin());
                customDraw.add(teamVO1.getCustomDraw());
                customLose.add(teamVO1.getCustomLose());
                ym.add(teamVO1.getYm());
            }
        }
        homeWinBar.data(homeWin.toArray());
        homeDrawBar.data(homeDraw.toArray());
        homeLoseBar.data(homeLose.toArray());
        customWinBar.data(customWin.toArray());
        customDrawBar.data(customDraw.toArray());
        customLoseBar.data(customLose.toArray());
        option.xAxis(new CategoryAxis().boundaryGap(false).data(ym.toArray()));
        option.yAxis(new ValueAxis());
        option.series(homeWinBar,homeDrawBar,homeLoseBar,customWinBar,customDrawBar,customLoseBar);
        return  option;
    }

}
