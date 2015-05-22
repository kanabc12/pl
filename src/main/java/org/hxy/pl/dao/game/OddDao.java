package org.hxy.pl.dao.game;

import org.hxy.pl.vo.game.OddVO;

import java.util.List;

/**
 * Created by Administrator on 15-5-3.
 */
public interface OddDao {
    public int saveGameOdd(OddVO oddVO);
    public List<OddVO> getOddsByGameAndCompany(Integer gameId,Integer companyId);
}
