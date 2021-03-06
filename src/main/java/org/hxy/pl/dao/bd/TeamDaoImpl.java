package org.hxy.pl.dao.bd;

import org.hxy.pl.dao.BaseDao;
import org.hxy.pl.vo.bd.TeamVO;
import org.hxy.pl.vo.common.JqGridData;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TeamDaoImpl extends BaseDao<TeamVO> implements TeamDao {

	@Override
	public int saveTeam(TeamVO team) {
		return super.insert(team);
	}

	@Override
	public TeamVO findTeamById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public List<TeamVO> findTeamsByConditions(TeamVO teamVO) {
        return super.findList(generateStatement("selectTeamsByCondition"),teamVO);
    }

    @Override
    public List<TeamVO> findTeamPageListBySize(TeamVO teamVO,int pageNo,int pageSize) {
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("name",teamVO.getName());
        map.put("type",teamVO.getType());
        map.put("contry",teamVO.getContry());
        map.put("n", (pageNo-1)*pageSize);
        map.put("m", pageSize);
        return findList(generateStatement("findTeamPageListBySize"),map);
    }

    @Override
    public Integer findTeamCount(TeamVO teamVO) {
        return (Integer)selectOne(generateStatement("selectTeamCount"),teamVO);
    }

    @Override
    public int  deleteTeamById(TeamVO teamVO) {
        return delete(generateStatement("deleteTeamById"),teamVO);
    }

    @Override
    public int updateTeam(TeamVO teamVO) {
        return  super.update(generateStatement("updateTeam"),teamVO);
    }

    @Override
    public List<TeamVO> findTeamByCountry(Long countryId) {
        return super.findList(generateStatement("findTeamByCountryId"),countryId);
    }

    @Override
    public TeamVO countWDFBySeason(TeamVO teamVO, Long seasonId) {
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("teamId",teamVO.getId());
        map.put("seasonId",seasonId);
        return (TeamVO)super.selectOne(generateStatement("countWDFBySeason"),map);
    }

    @Override
    public List<TeamVO> countWDFBySeasonAndYM(TeamVO teamVO, Long seasonId) {
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("teamId",teamVO.getId());
        map.put("seasonId",seasonId);
        return findList(generateStatement("countWDFBySeasonAndYM"),map);
    }

    @Override
    public TeamVO selectTeamByName(String teamName) {
        return (TeamVO)super.selectOne(generateStatement("selectTeamByName"),teamName);
    }

}
