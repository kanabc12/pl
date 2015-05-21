package org.hxy.pl.dao.game;

import org.hxy.pl.vo.game.ResultVO;

import java.util.List;

/**
 * Created by Administrator on 15-5-3.
 */
public interface ResultDao {
    public int saveGameResult(ResultVO resultVO);
    public List<ResultVO>  getResults(ResultVO resultVO,int pageNo,int pageSize);
    public int getResultCount(ResultVO resultVO);
    public ResultVO getResultById(Integer gameId);
}
