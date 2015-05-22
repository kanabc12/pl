package org.hxy.pl.dao.game;

import org.hxy.pl.dao.BaseDao;
import org.hxy.pl.vo.game.OddVO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 15-5-3.
 */
@Repository
public class OddDaoImpl extends BaseDao<OddVO> implements OddDao {
    @Override
    public int saveGameOdd(OddVO oddVO) {
        return insert(oddVO);
    }

    @Override
    public List<OddVO> getOddsByGameAndCompany(Integer gameId, Integer companyId) {
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("gameId",gameId);
        paramMap.put("companyId",companyId);
        List<OddVO>  odds = findList(generateStatement("getOddsByGameAndCompany"),paramMap);
        return odds;
    }
}
