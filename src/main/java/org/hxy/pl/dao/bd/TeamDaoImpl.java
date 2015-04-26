package org.hxy.pl.dao.bd;

import org.hxy.pl.dao.BaseDao;
import org.hxy.pl.vo.bd.TeamVO;
import org.springframework.stereotype.Repository;

import java.util.List;

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

}
