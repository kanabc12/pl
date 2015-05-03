package org.hxy.pl.dao.game;

import org.hxy.pl.dao.BaseDao;
import org.hxy.pl.vo.game.ResultVO;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 15-5-3.
 */
@Repository
public class ResultDaoImpl extends BaseDao<ResultVO> implements ResultDao {
    @Override
    public int saveGameResult(ResultVO resultVO) {
        return insert(resultVO);
    }
}
