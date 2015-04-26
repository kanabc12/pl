package org.hxy.pl.dao.bd;

import org.hxy.pl.vo.bd.TeamVO;

public interface TeamDao {
	public  int saveTeam(TeamVO team);
	public  TeamVO findTeamById(Long id);
}
