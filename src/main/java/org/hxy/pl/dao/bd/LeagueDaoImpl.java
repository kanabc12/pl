package org.hxy.pl.dao.bd;

import org.hxy.pl.dao.BaseDao;
import org.hxy.pl.vo.bd.LeagueVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 15-5-8.
 */
@Repository
public class LeagueDaoImpl extends BaseDao<LeagueVO> implements LeagueDao {
    @Override
    public List<LeagueVO> getAllLeagues() {
        return findList(generateStatement("findAllLeague"));
    }
}
