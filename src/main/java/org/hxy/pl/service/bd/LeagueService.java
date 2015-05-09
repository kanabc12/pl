package org.hxy.pl.service.bd;

import org.hxy.pl.dao.bd.LeagueDao;
import org.hxy.pl.vo.bd.LeagueVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 15-5-8.
 */
@Service
public class LeagueService {
    @Autowired
    private LeagueDao leagueDao;
    public List<LeagueVO> findAllLeague(){
        return leagueDao.getAllLeagues();
    }
}
