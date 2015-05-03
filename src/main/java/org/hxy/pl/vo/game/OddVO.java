package org.hxy.pl.vo.game;

import org.hxy.pl.vo.BaseVO;

import java.util.Date;

/**
 * Created by Administrator on 15-5-3.
 */
public class OddVO extends BaseVO {
    private Integer  id;
    private Integer gemeId;
    private Float winOdd;
    private Float drawOdd;
    private Float loseOdd;
    private Date addDate;
    private Integer companyId;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGemeId() {
        return gemeId;
    }

    public void setGemeId(Integer gemeId) {
        this.gemeId = gemeId;
    }

    public Float getWinOdd() {
        return winOdd;
    }

    public void setWinOdd(Float winOdd) {
        this.winOdd = winOdd;
    }

    public Float getDrawOdd() {
        return drawOdd;
    }

    public void setDrawOdd(Float drawOdd) {
        this.drawOdd = drawOdd;
    }

    public Float getLoseOdd() {
        return loseOdd;
    }

    public void setLoseOdd(Float loseOdd) {
        this.loseOdd = loseOdd;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
