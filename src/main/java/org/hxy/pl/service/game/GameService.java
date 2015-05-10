package org.hxy.pl.service.game;

import org.hxy.pl.dao.game.ResultDao;
import org.hxy.pl.vo.game.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 15-5-10.
 */
@Service
public class GameService {
    @Autowired
    private ResultDao resultDao;

    public int saveGameResult(ResultVO resultVO){
        int result = 0;
        result = resultDao.saveGameResult(resultVO);
        return  result;
    }
}
