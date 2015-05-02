package org.hxy.pl.service.bd;

import java.util.List;

import org.hxy.pl.dao.bd.CountryDao;
import org.hxy.pl.vo.bd.CountryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryService {
    @Autowired
	private CountryDao contryDao;
	public List<CountryVO> showAllContry(){
		return contryDao.showAllCountry();
	}

    public CountryVO getCountryById(Long id){
        return  contryDao.getCountryById(id);
    }
}
