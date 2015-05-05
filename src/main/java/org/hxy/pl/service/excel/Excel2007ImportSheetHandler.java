package org.hxy.pl.service.excel;

import com.google.common.collect.Lists;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.hxy.pl.common.utils.date.DateStyle;
import org.hxy.pl.vo.excel.ExcelVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 15-5-3.
 */
public class Excel2007ImportSheetHandler extends DefaultHandler {
    private final Logger log = LoggerFactory.getLogger(Excel2007ImportSheetHandler.class);

    //共享字符串表
    private SharedStringsTable sst;
    private boolean nextIsString;
    private int curCol = 0;    //当前列

    private int batchSize; //批处理大小
    private int totalSize = 0; //总行数
    private int rowNumber = 1;
    private String lastContents;
    private List<ExcelVO> dataList;

    private ExcelDataService excelDataService;
    private List<String> currentCellData = Lists.newArrayList();

    Excel2007ImportSheetHandler(
            final ExcelDataService excelDataService, final List<ExcelVO> dataList, final int batchSize,SharedStringsTable sst) {
        this.excelDataService = excelDataService;
        this.dataList = dataList;
        this.batchSize = batchSize;
        this.sst = sst;
    }

    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
        if ("row".equals(name)) {//如果是行开始 清空cell数据 重来
            rowNumber = Integer.valueOf(attributes.getValue("r"));//当前行号
            if (rowNumber == 1) {
                return;
            }
            currentCellData.clear();
        }
        if ("c".equals(name)) {
            String cellType = attributes.getValue("t");
            if ("s".equals(cellType)) {
                nextIsString = true;
            } else {
                nextIsString = false;
            }
        }
        // 置空
        lastContents = "";
    }

    public void endElement(String uri, String localName, String name) throws SAXException {
            if ("row".equals(name)) {//如果是行开始 清空cell数据
                if (rowNumber == 1) {
                    return;
                }
                ExcelVO data = new ExcelVO();
                data.setLeagueName(currentCellData.get(0));
                data.setGameTimeStr(currentCellData.get(1));
                data.setHomeTeam(currentCellData.get(2));
                data.setResultStr(currentCellData.get(3));
                data.setCustomTeam(currentCellData.get(4));
                data.setWwiOdd(currentCellData.get(5));
                data.setWdiOdd(currentCellData.get(6));
                data.setWliOdd(currentCellData.get(7));
                data.setWwfOdd(currentCellData.get(8));
                data.setWdfOdd(currentCellData.get(9));
                data.setWlfOdd(currentCellData.get(10));
                data.setLwiOdd(currentCellData.get(11));
                data.setLdiOdd(currentCellData.get(12));
                data.setLliOdd(currentCellData.get(13));
                data.setLwfOdd(currentCellData.get(14));
                data.setLdfOdd(currentCellData.get(15));
                data.setLlfOdd(currentCellData.get(16));
                dataList.add(data);
                totalSize++;
//            if (totalSize % batchSize == 0) {
//                try {
//                    excelDataService.doBatchSave(dataList);
//                } catch (Exception e) {
//                    log.error("save error", e);
//                }
//                dataList.clear();
//            }
            }

        if ("c".equals(name)) {//按照列顺序添加数据
            if(nextIsString){
                int idx = Integer.parseInt(lastContents);
                lastContents = new XSSFRichTextString(sst.getEntryAt(idx))
                        .toString();
            }
            currentCellData.add(lastContents);
        }
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        lastContents += new String(ch, start, length);
    }

}
