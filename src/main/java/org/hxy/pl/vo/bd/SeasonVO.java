package org.hxy.pl.vo.bd;

import org.hxy.pl.vo.BaseVO;

import java.util.Date;

/**
 * Created by Administrator on 15-5-9.
 */
public class SeasonVO extends BaseVO {
    private Integer id;
    private String name;
    private Date beginDate;
    private Date endDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
