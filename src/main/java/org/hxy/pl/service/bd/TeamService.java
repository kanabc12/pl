package org.hxy.pl.service.bd;

import org.hxy.pl.dao.bd.TeamDao;
import org.hxy.pl.vo.bd.TeamVO;
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
}
