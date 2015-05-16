package org.hxy.pl.dao.game;

import org.hxy.pl.dao.BaseDao;
import org.hxy.pl.vo.game.ResultVO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 15-5-3.
 */
@Repository
public class ResultDaoImpl extends BaseDao<ResultVO> implements ResultDao {
    @Override
    public int saveGameResult(ResultVO resultVO) {
        return insert(resultVO);
    }

    @Override
    public List<ResultVO> getResults(ResultVO resultVO, int pageNo, int pageSize) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("leagueId",resultVO.getLeagueId());
        map.put("seasonId",resultVO.getSeasonId());
        map.put("round",resultVO.getRound());
        map.put("homeTeam",resultVO.getHomeTeam());
        map.put("customTeam",resultVO.getCustomTeam());
        map.put("n", (pageNo-1)*pageSize);
        map.put("m", pageSize);
        return findList(generateStatement("findResultByPageList"),map);
    }

    @Override
    public int getResultCount(ResultVO resultVO) {
        return (Integer)selectOne("getResultCount",resultVO);
    }
}
