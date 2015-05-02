package org.hxy.pl.dao.bd;

import java.util.List;

import org.hxy.pl.dao.BaseDao;
import org.hxy.pl.vo.bd.CountryVO;
import org.springframework.stereotype.Repository;
@Repository
public class CountryDaoImpl extends BaseDao<CountryVO> implements CountryDao {

	@Override
	public List<CountryVO> showAllCountry() {
		 return super.findList(generateStatement("findAllCountry"));
	}

    @Override
    public CountryVO getCountryById(Long id) {
        return  (CountryVO)selectOne(generateStatement("getCountryById"),id);
    }

}
