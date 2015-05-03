package org.hxy.pl.dao.game;

import org.hxy.pl.dao.BaseDao;
import org.hxy.pl.vo.game.OddVO;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 15-5-3.
 */
@Repository
public class OddDaoImpl extends BaseDao<OddVO> implements OddDao {
    @Override
    public int saveGameOdd(OddVO oddVO) {
        return insert(oddVO);
    }
}
