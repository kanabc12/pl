package org.hxy.pl.service.game;

import org.hxy.pl.dao.game.ResultDao;
import org.hxy.pl.vo.common.PageModel;
import org.hxy.pl.vo.game.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public PageModel findResultsPageList(ResultVO resultVO,int pageNO,int pageSize){
        List<ResultVO> resultVOs = resultDao.getResults(resultVO,pageNO,pageSize);
        int totalItems = resultDao.getResultCount(resultVO);
        PageModel pageModel =  new PageModel();
        pageModel.setList(resultVOs);
        pageModel.setTotalItem(totalItems);
        return  pageModel;
    }

    public PageModel findLeagueResultsPageList(ResultVO resultVO,int pageNO,int pageSize){
        List<ResultVO> resultVOs = resultDao.getLeagueResults(resultVO,pageNO,pageSize);
        int totalItems = resultDao.getResultCount(resultVO);
        PageModel pageModel =  new PageModel();
        pageModel.setList(resultVOs);
        pageModel.setTotalItem(totalItems);
        return  pageModel;
    }

    public List<ResultVO> findLeagueResults(ResultVO resultVO){
        List<ResultVO> resultVOs = resultDao.getLeagueResultsWithoutPage(resultVO);
        return  resultVOs;
    }

    public  ResultVO getResultById(Integer gameId){
        return resultDao.getResultById(gameId);
    }

    public List<ResultVO> getResultsByTeam(String teamName,Integer seasonId,Integer resultType){
        return resultDao.getResultsByTeam(teamName,seasonId,resultType);
    }

    public List<ResultVO>getResultsByTeamAndYM(String teamName,Integer seasonId,Integer resultType,String ym){
        return  resultDao.getResultsByTeamAndYM(teamName,seasonId,resultType,ym);
    }

    public ResultVO updateGameResult (ResultVO resultVO){
        if(resultVO.getHomeGoals() != null && resultVO.getCustomGoals() != null){
            if(resultVO.getHomeGoals().intValue() >resultVO.getCustomGoals().intValue()){
                resultVO.setResultType(1);
            }else if(resultVO.getHomeGoals().intValue() == resultVO.getCustomGoals().intValue()){
                resultVO.setResultType(0);
            }else{
                resultVO.setResultType(-1);
            }
            if(resultVO.getHomeHalfGoals() != null){
                resultVO.setHomeSecGoals(resultVO.getHomeGoals()-resultVO.getHomeHalfGoals());
            }
            if(resultVO.getCustomHalfGoals() != null){
                resultVO.setCustomSecGoals(resultVO.getCustomGoals()-resultVO.getCustomHalfGoals());
            }
            resultVO.setResultStr(resultVO.getHomeGoals()+":"+resultVO.getCustomGoals()+"("+resultVO.getHomeHalfGoals()+":"+resultVO.getCustomHalfGoals()+")");
        }
        resultVO.setGameStatus(1);
        resultDao.updateGameResult(resultVO);
        return  resultVO;
    }

}
