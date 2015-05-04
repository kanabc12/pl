package org.hxy.pl.service.excel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 15-5-4.
 */
public class Excel2007Reader extends DefaultHandler {

    // 共享字符串表
    private SharedStringsTable sst;
    // 上一次的内容
    private String             lastContents;
    private boolean            nextIsString;

    private boolean            cellNull;

    private int                sheetIndex = -1;
    private List<String> rowlist    = new ArrayList<String>();
    // 当前行
    private int                curRow     = 0;
    // 当前列
    private int                curCol     = 0;

    private IRowReader         rowReader;

    public void setRowReader(IRowReader rowReader) {
        this.rowReader = rowReader;
    }

    /**
     * 只遍历一个电子表格，其中sheetId为要遍历的sheet索引，从1开始，1-3
     *
     * @param filename
     * @param sheetId
     * @throws Exception
     */
    public void processOneSheet(String filename, int sheetId) throws Exception {
        OPCPackage pkg = OPCPackage.open(filename);
        XSSFReader r = new XSSFReader(pkg);
        SharedStringsTable sst = r.getSharedStringsTable();
        XMLReader parser = fetchSheetParser(sst);

        // 根据 rId# 或 rSheet# 查找sheet
        InputStream sheet2 = r.getSheet("rId" + sheetId);
        sheetIndex++;
        InputSource sheetSource = new InputSource(sheet2);
        parser.parse(sheetSource);
        sheet2.close();
    }

    public void process(InputStream inputStream) throws Exception {
        OPCPackage pkg = OPCPackage.open(inputStream);
        proccessintern(pkg);
    }

    /**
     * 遍历工作簿中所有的电子表格

     *
     * @param filename
     * @throws Exception
     */
    public void process(String filename) throws Exception {
        OPCPackage pkg = OPCPackage.open(filename);
        proccessintern(pkg);
    }

    private void proccessintern(OPCPackage pkg) throws IOException, OpenXML4JException, InvalidFormatException,
            SAXException {
        XSSFReader r = new XSSFReader(pkg);
        SharedStringsTable sst = r.getSharedStringsTable();
        XMLReader parser = fetchSheetParser(sst);
        Iterator<InputStream> sheets = r.getSheetsData();
        while (sheets.hasNext()) {
            curRow = 0;
            sheetIndex++;
            InputStream sheet = sheets.next();
            InputSource sheetSource = new InputSource(sheet);
            parser.parse(sheetSource);
            sheet.close();
        }
    }

    public XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException {
        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
        this.sst = sst;
        parser.setContentHandler(this);
        return parser;
    }

    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {

        // c => 单元格
        if ("c".equals(name)) {
            // 如果下一个元素是 SST 的索引，则将nextIsString标记为true
            String cellType = attributes.getValue("t");
            if ("s".equals(cellType)) {
                nextIsString = true;
                cellNull = false;
            }
            else {
                nextIsString = false;
                cellNull = true;
            }
        }

        // 置空
        lastContents = "";
    }

    public void endElement(String uri, String localName, String name) throws SAXException {

        // 根据SST的索引值的到单元格的真正要存储的字符串
        // 这时characters()方法可能会被调用多次
        if (nextIsString) {
            try {
                int idx = Integer.parseInt(lastContents);
                lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
            } catch (Exception e) {

            }
        }


        if ("v".equals(name) || "t".equals(name)) {
            String value = lastContents.trim();
            value = value.equals("") ? " " : value;
            rowlist.add(curCol, value);
            curCol++;
            cellNull = false;
        }else if("c".equals(name) && cellNull == true){
            rowlist.add(curCol, "");
            curCol++;
            cellNull = false;
        }
        else {
            // 如果标签名称为 row ，这说明已到行尾，调用 optRows() 方法
            if (name.equals("row")) {
                rowReader.getRows(sheetIndex, curRow, rowlist);
                rowlist.clear();
                curRow++;
                curCol = 0;
            }
        }

    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        // 得到单元格内容的值

        lastContents += new String(ch, start, length);
    }
}

