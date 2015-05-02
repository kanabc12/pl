package org.hxy.pl.vo.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.JSON;
/**
 * Created by Administrator on 15-5-1.
 */
public class JqGridData<T> {

    /** Total number of pages */
    private int total;
    /** The current page number */
    private int page;
    /** Total number of records */
    private int records;
    /** The actual data */
    private List<T> rows;

    private String dateFormat  = "yyyy-MM-dd HH:mm:ss";//默认日期格式;

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public JqGridData(int total, int page, int records, List<T> rows) {
        this.total = total;
        this.page = page;
        this.records = records;
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public int getPage() {
        return page;
    }

    public int getRecords() {
        return records;
    }

    public List<T> getRows() {
        return rows;
    }

    public String getJsonString(){
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", page);
        map.put("total", total);
        map.put("records", records);
        map.put("rows", rows);
        return JSON.toJSONStringWithDateFormat(map, getDateFormat());
    }
}
