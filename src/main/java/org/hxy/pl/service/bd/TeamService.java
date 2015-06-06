package org.hxy.pl.service.bd;

import com.github.abel533.echarts.*;
import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.*;
import com.github.abel533.echarts.data.PieData;
import com.github.abel533.echarts.data.PointData;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.json.FsonOption;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.series.Pie;
import org.hxy.pl.dao.bd.TeamDao;
import org.hxy.pl.vo.bd.TeamVO;
import org.hxy.pl.vo.common.JqGridData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
//            pie.radius(new String[]{"50%", "70%"});
//            ItemStyle itemStyle = new ItemStyle();
//            itemStyle.normal().label(new Label().show(false)).labelLine().show(false);
//            itemStyle.emphasis().label().show(true).position("center").textStyle(new TextStyle().fontSize(30).fontFamily("bold"));
//            pie.itemStyle(itemStyle);
            option.series(pie);

        }
        return  option;
    }

    public Option countWDFBySeasonAndYM(Long seasonId,String teamName){
//        TeamVO teamVO =  teamDao.selectTeamByName(teamName);
//        List<TeamVO> countTeam =  teamDao.countWDFBySeasonAndYM(teamVO,seasonId);
//        Option option = new Option();
//        Title title = new Title();
//        Legend legend = new Legend();
//        legend.setOrient(Orient.vertical);
//        legend.setX(X.left);
//        String[] legendTest = new String[]{"主场胜","主场平","主场负","客场胜","客场平","客场负"};
//        legend.data(legendTest);
//        option.legend(legend);
//        Tooltip tooltip =  new Tooltip();
//        tooltip.setTrigger(Trigger.axis);
//        tooltip.axisPointer().type(PointerType.shadow);
//        option.setTooltip(tooltip);
//        //创建类目轴
//        CategoryAxis category = new CategoryAxis();
//        if(countTeam.size()>0){
//            title.setText(countTeam.get(0).getName());
//            title.setX(X.center);
//            option.setTitle(title);
//            Bar bar = new Bar("主场胜");
//            for(TeamVO team :countTeam){
//                //设置类目
//                category.data(team.getYm());
//                bar.data(team.getHomeWin());
//            }
//            option.xAxis(category);
//            option.yAxis(new ValueAxis());
//            option.series(bar);
//        }
        Option option = new Option();
        option.title("某楼盘销售情况", "纯属虚构");
        option.tooltip().trigger(Trigger.axis);
        option.legend("意向", "预购", "成交");
        option.toolbox().show(true).feature(Tool.mark,
                Tool.dataView,
                new MagicType(Magic.line, Magic.bar, Magic.stack, Magic.tiled),
                Tool.restore,
                Tool.saveAsImage).padding(20);
        option.calculable(true);
        option.xAxis(new CategoryAxis().boundaryGap(false).data("周一", "周二", "周三", "周四", "周五", "周六", "周日"));
        option.yAxis(new ValueAxis());

        Line l1 = new Line("成交");
        l1.smooth(true).itemStyle().normal().areaStyle().typeDefault();
        l1.data(10, 12, 21, 54, 260, 830, 710);

        Line l2 = new Line("预购");
        l2.smooth(true).itemStyle().normal().areaStyle().typeDefault();
        l2.data(30, 182, 434, 791, 390, 30, 10);

        Line l3 = new Line("意向");
        l3.smooth(true).itemStyle().normal().areaStyle().typeDefault();
        l3.data(1320, 1132, 601, 234, 120, 90, 20);

        option.series(l1, l2, l3);
        return  option;
    }

}
