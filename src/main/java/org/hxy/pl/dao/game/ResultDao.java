package org.hxy.pl.dao.game;

import org.hxy.pl.vo.game.ResultVO;

import java.util.List;

/**
 * Created by Administrator on 15-5-3.
 */
public interface ResultDao {
    public int saveGameResult(ResultVO resultVO);
    public List<ResultVO>  getResults(ResultVO resultVO,int pageNo,int pageSize);
    public List<ResultVO>  getLeagueResults(ResultVO resultVO,int pageNo,int pageSize);
    public List<ResultVO>  getLeagueResultsWithoutPage(ResultVO resultVO);
    public int getResultCount(ResultVO resultVO);
    public ResultVO getResultById(Integer gameId);
    public List<ResultVO> getResultsByTeam(String teamName,Integer seasonId,Integer resultType);
    public List<ResultVO>getResultsByTeamAndYM(String teamName,Integer seasonId,Integer resultType,String ym);
    public int updateGameResult(ResultVO resultVO);
}
