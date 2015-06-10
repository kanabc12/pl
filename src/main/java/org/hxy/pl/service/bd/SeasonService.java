package org.hxy.pl.service.bd;

import org.hxy.pl.dao.bd.SeasonDao;
import org.hxy.pl.vo.bd.SeasonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 15-5-9.
 */
@Service
public class SeasonService {
    @Autowired
    private SeasonDao seasonDao;
    public List<SeasonVO>  findAllSeason(){
        return seasonDao.showAllSeason();
    }
    public SeasonVO findSeasonById(Long seasonId){
        return  seasonDao.findSeasonById(seasonId);
    }
}
