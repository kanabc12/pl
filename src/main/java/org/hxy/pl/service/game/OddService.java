package org.hxy.pl.service.game;

import org.hxy.pl.dao.game.OddDao;
import org.hxy.pl.vo.game.OddVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 15-5-21.
 */
@Service
public class OddService {
    @Autowired
    private OddDao oddDao;

    public int saveOdd(OddVO oddVO){
        return  oddDao.saveGameOdd(oddVO);
    }

    public Map getOddsByGameId(Integer  gameId){
        Map<String ,List<OddVO>> oddMap = new HashMap<String ,List<OddVO>>();
        List<OddVO> wOdds = oddDao.getOddsByGameAndCompany(gameId,new Integer(1));//威廉赔率
        List<OddVO> lOdds = oddDao.getOddsByGameAndCompany(gameId,new Integer(2));//立博赔率
        oddMap.put("wOdds",wOdds);
        oddMap.put("lOdds",lOdds);
        return  oddMap;
    }
}
