package org.hxy.pl.dao.bd;

import org.hxy.pl.dao.BaseDao;
import org.hxy.pl.vo.bd.SeasonVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 15-5-9.
 */
@Repository
public class SeasonDaoImpl extends BaseDao<SeasonVO> implements SeasonDao {
    @Override
    public List<SeasonVO> showAllSeason() {
        return findList(generateStatement("showAllSeason"));
    }
}
