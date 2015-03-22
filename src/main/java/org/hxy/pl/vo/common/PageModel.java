package org.hxy.pl.vo.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 15-3-22.
 */
public class PageModel implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -2954869061356657945L;
    public static int PAGE_SIZE = 10;
    public static int FIRST_PAGE = 1;
    public static int MAX_PAGE_COUNT = 15;


    public static String DB_TYPE_MYSQL = "mysql";
    public static String DB_TYPE_ORACLE = "oracle";
    /**当前页**/
    private int toPage = FIRST_PAGE;
    /**每一页条数**/
    private int pageSize = PAGE_SIZE;

    private int maxPageCount = MAX_PAGE_COUNT;
    /**总记录数**/
    private int totalItem;
    /***总页数***/
    private int totalPage;
    private List<Integer> pageNumList;

    private String DBType = DB_TYPE_MYSQL;
    /**数据列表**/
    private List list = new ArrayList();

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public String getDBType() {
        return DBType;
    }

    public void setDBType(String dBType) {
        DBType = dBType;
    }
    public int getMaxPageCount() {
        return maxPageCount < 1? MAX_PAGE_COUNT:maxPageCount;
    }

    public void setMaxPageCount(int maxPageCount) {
        this.maxPageCount = maxPageCount;
    }

    public int getToPage() {
        return toPage;
    }

    public void setToPage(int toPage) {
        this.toPage = toPage;
    }

    public void setTotalPage(int totalPage) {

        this.totalPage = totalPage;
    }

    public int getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
    }

    public int getPageSize() {
        return pageSize <= 0?PAGE_SIZE:pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<Integer> getPageNumList() {
        pageNumList = new ArrayList<Integer>();
        int totalPage = this.getTotalPage();
        toPage = toPage< 1?FIRST_PAGE:(toPage >totalPage?totalPage:toPage);
        int currentOption = toPage;
        int endOption = currentOption + getMaxPageCount()/2;
        if(endOption > totalPage){
            endOption =  totalPage;
        }
        int beginOption = endOption - getMaxPageCount() + 1;
        if(beginOption <= 0){
            beginOption = 1;
        }
        for(int i=beginOption;i<=endOption;i++){
            pageNumList.add(i);
        }
        return pageNumList;
    }


    public boolean getIsFirstPage() {
        return toPage == FIRST_PAGE;
    }


    public boolean getIsLastPage() {
        int totalPage = this.getTotalPage();
        return totalPage == 0 || toPage == totalPage;
    }


    public int getPrevPage() {
        int back = toPage - 1;
        return back < 1?FIRST_PAGE:back;
    }


    public int getNextPage() {
        int next = toPage + 1;
        int totalPage = this.getTotalPage();
        if (next >= totalPage) {
            next = totalPage;
        }
        return next;
    }

    public int getFirstPage() {
        return FIRST_PAGE;
    }


    public int getLastPage() {
        int totalPage = this.getTotalPage();
        return totalPage <= 0?FIRST_PAGE:totalPage;
    }


    public int getTotalPage() {
        totalPage = this.totalItem/this.pageSize;
        if (totalPage == 0 || totalItem % pageSize != 0) {
            totalPage++;
        }
        return totalPage;
    }


    public Integer getStartRow() {
        int totalPage = this.getTotalPage();
        toPage = toPage< 1?FIRST_PAGE:(toPage >totalPage?totalPage:toPage);
        Integer row =Integer.valueOf((toPage - 1) * getPageSize());
        if(DB_TYPE_ORACLE.equals(DBType)){
            row = row + 1;
        }
        return row;
    }

    public Integer getEndRow() {
        int totalPage = this.getTotalPage();
        toPage = toPage< 1?FIRST_PAGE:(toPage >totalPage?totalPage:toPage);
        return Integer.valueOf(toPage * getPageSize());
    }

}
