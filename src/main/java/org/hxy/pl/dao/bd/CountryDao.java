package org.hxy.pl.dao.bd;

import java.util.List;

import org.hxy.pl.vo.bd.CountryVO;

public interface CountryDao {
	List<CountryVO> showAllCountry();
    CountryVO getCountryById(Long id);
}
