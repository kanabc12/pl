package org.hxy.pl.dao.test;

import org.hxy.pl.dao.BaseDao;
import org.hxy.pl.vo.test.TestVo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 15-3-24.
 */
public class TestDaoImpl extends BaseDao<TestVo> implements TestDao {
    public TestVo findTestVoById(Integer id) {
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("id",id);
        return (TestVo)selectOne(generateStatement("findTestVoById"),map);
    }
}
