package org.hxy.pl.service.game;

import org.hxy.pl.dao.game.OddDao;
import org.hxy.pl.vo.game.OddVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
