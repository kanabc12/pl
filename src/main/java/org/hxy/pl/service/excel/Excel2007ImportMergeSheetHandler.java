package org.hxy.pl.service.excel;

import com.google.common.collect.Lists;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
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
public class Excel2007ImportMergeSheetHandler extends DefaultHandler {
    private final Logger log = LoggerFactory.getLogger(Excel2007ImportMergeSheetHandler.class);

    //共享字符串表
    private SharedStringsTable sst;
    private boolean nextIsString;

    private int rowNumber = 1;
    private String lastContents;
    private List<ExcelVO> dataList;

    private ExcelMergeDataService excelDataService;
    private List<String> currentCellData = Lists.newArrayList();
    private ExcelVO data = new ExcelVO();
    private String date;
    private String time;

    Excel2007ImportMergeSheetHandler(
            final ExcelMergeDataService excelDataService, final List<ExcelVO> dataList,  SharedStringsTable sst) {
        this.excelDataService = excelDataService;
        this.dataList = dataList;
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
        if ("c".equals(name)&&rowNumber!=1) {//按照列顺序添加数据
            if (nextIsString) {
                int idx = Integer.parseInt(lastContents);
                lastContents = new XSSFRichTextString(sst.getEntryAt(idx))
                        .toString();
            }
            currentCellData.add(lastContents);
        }

        if ("row".equals(name)) {//如果是行开始 清空cell数据
            if (rowNumber == 1) {
                return;
            }
            if(rowNumber%2==0){
                data.setLeagueName(currentCellData.get(0));
                date = currentCellData.get(1);
                data.setHomeTeam(currentCellData.get(2));
                data.setResultStr(currentCellData.get(3));
                data.setCustomTeam(currentCellData.get(4));
                data.setWwiOdd(currentCellData.get(5));
                data.setWdiOdd(currentCellData.get(6));
                data.setWliOdd(currentCellData.get(7));
                data.setLwiOdd(currentCellData.get(8));
                data.setLdiOdd(currentCellData.get(9));
                data.setLliOdd(currentCellData.get(10));
                data.setRound(currentCellData.get(11));
                data.setSeasonId(currentCellData.get(12));
            }else {
                time = currentCellData.get(1);
                data.setGameTimeStr(date +" " +time);
                if("".equals(currentCellData.get(5))||currentCellData.get(5)==null){
                    data.setWwfOdd(data.getWwiOdd());
                }else{
                    data.setWwfOdd(currentCellData.get(5));
                }
                if("".equals(currentCellData.get(6))||currentCellData.get(6)==null){
                    data.setWdfOdd(data.getWdiOdd());
                }else{
                    data.setWdfOdd(currentCellData.get(6));
                }
                if("".equals(currentCellData.get(7))||currentCellData.get(7)==null){
                    data.setWlfOdd(data.getWliOdd());
                }else{
                    data.setWlfOdd(currentCellData.get(7));
                }
                if("".equals(currentCellData.get(8))||currentCellData.get(8)==null){
                    data.setLwfOdd(data.getLwiOdd());
                }else{
                    data.setLwfOdd(currentCellData.get(8));
                }
                if("".equals(currentCellData.get(9))||currentCellData.get(9)==null){
                    data.setLdfOdd(data.getLdiOdd());
                }else{
                    data.setLdfOdd(currentCellData.get(9));
                }
                if("".equals(currentCellData.get(10))||currentCellData.get(10)==null){
                    data.setLlfOdd(data.getLliOdd());
                }else{
                    data.setLlfOdd(currentCellData.get(10));
                }
                try{
                    excelDataService.doBatchSave(data);
                }catch (Exception e) {
                    log.error("save error", e);
                }
            }
        }

    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        lastContents += new String(ch, start, length);
    }

}
