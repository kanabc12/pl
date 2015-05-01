package org.hxy.pl.service.bd;

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
}
