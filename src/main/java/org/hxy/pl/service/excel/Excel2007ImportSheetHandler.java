package org.hxy.pl.service.excel;

import com.google.common.collect.Lists;
import org.hxy.pl.vo.excel.ExcelVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.List;

/**
 * Created by Administrator on 15-5-3.
 */
public class Excel2007ImportSheetHandler extends DefaultHandler {
    private final Logger log = LoggerFactory.getLogger(Excel2007ImportSheetHandler.class);

    private int batchSize; //批处理大小
    private int totalSize = 0; //总行数

    private int rowNumber = 1;
    private String lastContents;
    private List<ExcelVO> dataList;
    private ExcelDataService excelDataService;
    private List<String> currentCellData = Lists.newArrayList();

    Excel2007ImportSheetHandler(
            final ExcelDataService excelDataService, final List<ExcelVO> dataList, final int batchSize) {
        this.excelDataService = excelDataService;
        this.dataList = dataList;
        this.batchSize = batchSize;
    }

    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
        if ("row".equals(name)) {//如果是行开始 清空cell数据 重来
            rowNumber = Integer.valueOf(attributes.getValue("r"));//当前行号
            if (rowNumber == 1) {
                return;
            }
            currentCellData.clear();
        }

        lastContents = "";
    }

    public void endElement(String uri, String localName, String name) throws SAXException {

        if ("row".equals(name)) {//如果是行开始 清空cell数据 重来
            if (rowNumber == 1) {
                return;
            }
            ExcelVO data = new ExcelVO();
            data.setLeagueName(currentCellData.get(0));
            data.setGameTimeStr(currentCellData.get(1));
            data.setHomeTeam(currentCellData.get(2));
            data.setResultStr(currentCellData.get(2));
            data.setCustomTeam(currentCellData.get(3));
            data.setWwiOdd(currentCellData.get(4));
            data.setWdiOdd(currentCellData.get(5));
            data.setWliOdd(currentCellData.get(6));
            data.setLwiOdd(currentCellData.get(7));
            data.setLdiOdd(currentCellData.get(8));
            data.setLliOdd(currentCellData.get(9));
            data.setLwfOdd(currentCellData.get(10));
            data.setLdfOdd(currentCellData.get(11));
            data.setLlfOdd(currentCellData.get(12));
            dataList.add(data);

            totalSize++;

            if (totalSize % batchSize == 0) {
//                try {
//                    excelDataService.doBatchSave(dataList);
//                } catch (Exception e) {
//                    Long fromId = dataList.get(0).getId();
//                    Long endId = dataList.get(dataList.size() - 1).getId();
//                    log.error("from " + fromId + " to " + endId + ", error", e);
//                }
                dataList.clear();
            }
        }

        if ("c".equals(name)) {//按照列顺序添加数据
            currentCellData.add(lastContents);
        }


    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        lastContents += new String(ch, start, length);
    }
}
