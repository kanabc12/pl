package org.hxy.pl.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.hxy.pl.util.Assert;
import org.hxy.pl.vo.common.PageModel;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Administrator on 15-3-22.
 */
public class BaseDao<E> extends SqlSessionDaoSupport {
    public static final String MAPPER_INSERT = "insert";
    public static final String MAPPER_DELETE = "delete";
    @Resource
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){
        super.setSqlSessionFactory(sqlSessionFactory);
    }
    private Class<E> entityClass;
    public BaseDao(){
        Type type = getClass().getGenericSuperclass();
        Type trueType = ((ParameterizedType) type).getActualTypeArguments()[0];
        this.entityClass = (Class<E>) trueType;
    }
    /**
     *
     * 功能描述：查询list
     *
     *
     * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
     */
    public List<E> findList(String statement, Object parameter) {
        Assert.notEmpty(statement, "Property statement is required");
        return (List<E>)this.getSqlSession().selectList(statement, parameter);
    }

    /**
     *
     * 功能描述：查询list
     *
     *
     * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
     */
    public List<E> findList(String statement) {
        Assert.notEmpty(statement, "Property statement is required");
        return (List<E>)this.getSqlSession().selectList(statement);
    }

    /**
     *
     * 功能描述：selectOne
     *
     *
     * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
     */
    public Object selectOne(String statement, Object parameter){
        Assert.notEmpty(statement, "Property statement is required");
        return this.getSqlSession().selectOne(statement, parameter);
    }
    /**
     *
     * 功能描述：批量删除
     *
     * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
     */
    public int deleteBatch(String statement, List<?> list) {
        Assert.notEmpty(statement, "Property statement is required");
        if(list == null || list.isEmpty()){
            return 0;
        }
        SqlSession sqlSession = this.getSqlSession();
        for (int i = 0; i < list.size(); i++) {
            sqlSession.delete(statement, list.get(i));
        }
        return list.size();
    }

    /**
     *
     * 功能描述：删除数据
     *
     * @param statement
     * @param parameter
     * @return
     *
     * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
     */
    public int delete(String statement,Object  parameter)
    {
        Assert.notEmpty(statement, "Property statement is required");
        return this.getSqlSession().delete(statement, parameter);
    }
    /**
     *
     * 功能描述：insert
     *
     * @param statement
     * @param parameter
     * @return
     *
     * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
     */
    public int insert(String statement, Object parameter) {
        Assert.notEmpty(statement, "Property statement is required");
        return this.getSqlSession().insert(statement, parameter);
    }
    /**
     *
     * 功能描述： insert
     *
     * @param parameter
     * @return
     *
     * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
     */
    public int insert(Object parameter) {
        return this.getSqlSession().insert(generateStatement(MAPPER_INSERT), parameter);
    }
    /**
     *
     * 功能描述：批量插入
     *
     * @param statement
     * @param list
     * @return
     *
     * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
     */
    public int insertBatch(String statement, List<?> list) {
        Assert.notEmpty(statement, "Property statement is required");
        if(list == null || list.isEmpty()){
            return 0;
        }
        SqlSession sqlSession = this.getSqlSession();
        for (int i = 0; i < list.size(); i++) {
            sqlSession.insert(statement, list.get(i));
        }
        return list.size();
    }
    /**
     *
     * 功能描述：update
     *
     * @param statement
     * @param parameter
     * @return
     *
     * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
     */
    public int update(String statement, Object parameter) {
        Assert.notEmpty(statement, "Property statement is required");
        return this.getSqlSession().update(statement, parameter);

    }
    public int update(Object parameter) {
        return this.getSqlSession().update(generateStatement("update"), parameter);

    }
    /**
     *
     * 功能描述：批量修改
     *
     * @param statement
     * @param list
     * @return
     *
     * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
     */
    public int updateBatch(String statement, List<?> list) {
        Assert.notEmpty(statement, "Property statement is required");
        if(list == null || list.isEmpty()){
            return 0;
        }
        SqlSession sqlSession = this.getSqlSession();
        for (int i = 0; i < list.size(); i++) {
            sqlSession.update(statement, list.get(i));
        }
        return list.size();
    }
    /**
     *
     * 功能描述：查询分页
     *
     * @param statement
     * @param countStatement
     * @param parameter
     * @return
     *
     * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
     */
    public PageModel findPagedList(String statement,String countStatement,PageModel parameter) {
        Assert.notEmpty(statement, "Property statement is required");
        Assert.notEmpty(statement, "Property countStatement is required");

        if(parameter == null){
            return new PageModel();
        }
        //首次查询时返回总记录数，以后查询时传入totalItem，则不再查询。
        if(parameter.getTotalItem() <= 0){
            // 得到数据记录总数
            Integer totalItem = (Integer) this.getSqlSession().selectOne(countStatement, parameter);
            if(totalItem != null){
                parameter.setTotalItem(totalItem.intValue());
            }else{
                return new PageModel();
            }
        }
        List<E> list = null;
        list = (List<E>)this.getSqlSession().selectList(statement, parameter);
        parameter.setList(list);
        return parameter;
    }
    /**
     *
     * 功能描述： mapperId:sqlmap配置文件sql语句id
     *
     * @param mapperId
     * @return
     *
     * <p>修改历史 ：(修改人，修改时间，修改原因/内容)</p>
     */
    public String generateStatement(String mapperId){
        return entityClass.getName() + "." + mapperId;
    }
}
