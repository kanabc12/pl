package org.hxy.pl.dao.bd;

import org.hxy.pl.vo.bd.TeamVO;

import java.util.List;

public interface TeamDao {
	public  int saveTeam(TeamVO team);
	public  TeamVO findTeamById(Long id);
    public List<TeamVO> findTeamsByConditions(TeamVO teamVO);
    public List<TeamVO>findTeamPageListBySize(TeamVO teamVO,int pageNo,int pageSize);
    public Integer findTeamCount(TeamVO  teamVO);
    public int  deleteTeamById(TeamVO teamVO);
    public int updateTeam(TeamVO teamVO);
    public  List<TeamVO> findTeamByCountry(Long  countryId);


}
