package org.hxy.pl.service.excel;

import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.hxy.pl.common.utils.date.DateStyle;
import org.hxy.pl.common.utils.date.DateUtils;
import org.hxy.pl.dao.game.OddDao;
import org.hxy.pl.dao.game.ResultDao;
import org.hxy.pl.vo.excel.ExcelVO;
import org.hxy.pl.vo.game.OddVO;
import org.hxy.pl.vo.game.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 15-5-3.
 */
@Service
public class ExcelDataService {
    private final Logger log = LoggerFactory.getLogger(ExcelDataService.class);
    private int batchSize = 1000; //批处理大小
    private int pageSize = 1000;//查询时每页大小

    @Autowired
    private ResultDao resultDao;
    @Autowired
    private OddDao oddDao;

    /**
     * 导出文件的最大大小 超过这个大小会压缩
     */
    private final int MAX_EXPORT_FILE_SIZE = 10 * 1024 * 1024; //10MB

    private final String storePath = "upload/excel";
    private final String EXPORT_FILENAME_PREFIX = "excel";

    public void doBatchSave(final List<ExcelVO> dataList) {
        for(ExcelVO data : dataList) {
            ResultVO resultVO =  new ResultVO();
            Date  gameDate = DateUtils.StringToDate(data.getGameTimeStr(), DateStyle.YYYY_MM_DD_HH_MM_SS);
            resultVO.setHomeTeam(parseTeam(data.getHomeTeam(),"home"));
            resultVO.setCustomTeam(parseTeam(data.getCustomTeam(),"custom"));
            resultVO.setActualTime(gameDate);
            resultVO.setCustomGoals(parseResultStr("custom", data.getResultStr()));
            resultVO.setCustomHalfGoals(getCustomHalfGoals(data.getResultStr()));
            resultVO.setCustomSecGoals(parseResultStr("custom", data.getResultStr())-getCustomHalfGoals(data.getResultStr()));
            resultVO.setCustomTeamRanking(parseRanking(data.getCustomTeam()));
            resultVO.setPlanDate(gameDate);
            resultVO.setGameStatus(1);
            resultVO.setResultStr(data.getResultStr());
            resultVO.setHomeGoals(parseResultStr("home",data.getResultStr()));
            resultVO.setHomeHalfGoals(getHomeHalfGoals(data.getResultStr()));
            resultVO.setHomeSecGoals(parseResultStr("home",data.getResultStr())-getHomeHalfGoals(data.getResultStr()));
            resultVO.setHomeTeamRanking(parseRanking(data.getHomeTeam()));
            if(resultVO.getHomeGoals()>resultVO.getCustomGoals()){
                resultVO.setResultType(1);
            }else if(resultVO.getHomeGoals()<resultVO.getCustomGoals()){
                resultVO.setResultType(-1);
            }else{
                resultVO.setResultType(0);
            }
            resultVO.setLeagueId(Integer.parseInt(data.getLeagueName()));
            resultVO.setSeasonId(Integer.parseInt(data.getSeasonId()));
            resultVO.setRound(Integer.parseInt(data.getRound()));
            resultDao.saveGameResult(resultVO);
            int gameId = resultVO.getId();
            OddVO  wOdd = new OddVO();
            wOdd.setGemeId(gameId);
            wOdd.setCompanyId(1);
            wOdd.setAddDate(gameDate);
            wOdd.setWinOdd(Float.parseFloat(data.getWwiOdd()));
            wOdd.setDrawOdd(Float.parseFloat(data.getWdfOdd()));
            wOdd.setLoseOdd(Float.parseFloat(data.getWliOdd()));
            wOdd.setType(1);
            if(wOdd.getWinOdd()!=0f){
                oddDao.saveGameOdd(wOdd);
            }
            OddVO wOddFinal = new OddVO();
            wOddFinal.setGemeId(gameId);
            wOddFinal.setCompanyId(1);
            wOddFinal.setAddDate(gameDate);
            wOddFinal.setWinOdd(Float.parseFloat(data.getWwfOdd()));
            wOddFinal.setDrawOdd(Float.parseFloat(data.getWdfOdd()));
            wOddFinal.setLoseOdd(Float.parseFloat(data.getWlfOdd()));
            wOddFinal.setType(2);
            if(wOddFinal.getWinOdd()!=0f){
                oddDao.saveGameOdd(wOddFinal);
            }
            OddVO lOdd = new OddVO();
            lOdd.setGemeId(gameId);
            lOdd.setCompanyId(2);
            lOdd.setAddDate(gameDate);
            lOdd.setWinOdd(Float.parseFloat(data.getLwiOdd()));
            lOdd.setDrawOdd(Float.parseFloat(data.getLdiOdd()));
            lOdd.setLoseOdd(Float.parseFloat(data.getLliOdd()));
            lOdd.setType(1);
            if(lOdd.getWinOdd()!=0f){
                oddDao.saveGameOdd(lOdd);
            }
            OddVO lOddFinal = new OddVO();
            lOddFinal.setGemeId(gameId);
            lOddFinal.setCompanyId(2);
            lOddFinal.setAddDate(gameDate);
            lOddFinal.setWinOdd(Float.parseFloat(data.getLwfOdd()));
            lOddFinal.setDrawOdd(Float.parseFloat(data.getLdfOdd()));
            lOddFinal.setLoseOdd(Float.parseFloat(data.getLlfOdd()));
            lOddFinal.setType(2);
            if(lOddFinal.getWinOdd()!=0f){
                oddDao.saveGameOdd(lOddFinal);
            }
        }
    }

    public void doBatchSaveSchedule(final List<ExcelVO> dataList) {
        for(ExcelVO data : dataList) {
            ResultVO resultVO =  new ResultVO();
            Date  gameDate = DateUtils.StringToDate(data.getGameTimeStr(), DateStyle.YYYY_MM_DD_HH_MM_SS);
            resultVO.setHomeTeam(Integer.parseInt(data.getHomeTeam()));
            resultVO.setCustomTeam(Integer.parseInt(data.getCustomTeam()));
            resultVO.setPlanDate(gameDate);
            resultVO.setGameStatus(0);//未赛
            resultVO.setLeagueId(Integer.parseInt(data.getLeagueName()));
            resultVO.setSeasonId(Integer.parseInt(data.getSeasonId()));
            resultVO.setRound(Integer.parseInt(data.getRound()));
            resultDao.saveGameResult(resultVO);
        }
    }

    @Async
    public void importExcel2007(final InputStream is){
        ExcelDataService proxy = ((ExcelDataService) AopContext.currentProxy());
        BufferedInputStream bis = null;
        //共享字符串表
        SharedStringsTable sst;
        try{
            List<ExcelVO> dataList = Lists.newArrayList();
            bis = new BufferedInputStream(is);
            OPCPackage pkg = OPCPackage.open(bis);
            XSSFReader r = new XSSFReader(pkg);
            XMLReader parser =
                    XMLReaderFactory.createXMLReader();
            sst = r.getSharedStringsTable();
            ContentHandler handler = new Excel2007ImportSheetHandler(proxy, dataList, batchSize,sst);

            parser.setContentHandler(handler);
            Iterator<InputStream> sheets = r.getSheetsData();
            while (sheets.hasNext()) {
                InputStream sheet = null;
                try {
                    sheet = sheets.next();
                    InputSource sheetSource = new InputSource(sheet);
                    parser.parse(sheetSource);
                } catch (Exception e) {
                    throw e;
                } finally {
                    IOUtils.closeQuietly(sheet);
                }
            }
            //把最后剩下的不足batchSize大小
            if (dataList.size() > 0) {
                proxy.doBatchSave(dataList);
            }
        }catch (Exception e){
            log.error("excel import error", e);
        }finally {
            IOUtils.closeQuietly(bis);
        }
    }

    @Async
    public void importScheduleExcel2007(final InputStream is){
        ExcelDataService proxy = ((ExcelDataService) AopContext.currentProxy());
        BufferedInputStream bis = null;
        //共享字符串表
        SharedStringsTable sst;
        try{
            List<ExcelVO> dataList = Lists.newArrayList();
            bis = new BufferedInputStream(is);
            OPCPackage pkg = OPCPackage.open(bis);
            XSSFReader r = new XSSFReader(pkg);
            XMLReader parser =
                    XMLReaderFactory.createXMLReader();
            sst = r.getSharedStringsTable();
            ContentHandler handler = new Excel2007ImportScheduleSheetHandler(proxy, dataList, batchSize,sst);

            parser.setContentHandler(handler);
            Iterator<InputStream> sheets = r.getSheetsData();
            while (sheets.hasNext()) {
                InputStream sheet = null;
                try {
                    sheet = sheets.next();
                    InputSource sheetSource = new InputSource(sheet);
                    parser.parse(sheetSource);
                } catch (Exception e) {
                    throw e;
                } finally {
                    IOUtils.closeQuietly(sheet);
                }
            }
            //把最后剩下的不足batchSize大小
            if (dataList.size() > 0) {
                proxy.doBatchSaveSchedule(dataList);
            }
        }catch (Exception e){
            log.error("excel import error", e);
        }finally {
            IOUtils.closeQuietly(bis);
        }
    }

    public Integer parseResultStr(String teamType,String resultStr){
        String fullResultStr = resultStr.substring(0,resultStr.indexOf("(") );
        if("home".equals(teamType)){
            return Integer.parseInt(fullResultStr.substring(0,fullResultStr.indexOf(":")));
        }else if("custom".equals(teamType)){
            return Integer.parseInt(fullResultStr.substring(fullResultStr.indexOf(":")+1));
        }
        return 0;
    }

    private Integer parseTeam(String teamName,String teamType){
        int team = 0;
        if("home".equals(teamType)){
            team = Integer.parseInt(teamName.substring(teamName.indexOf("]")+1));
        }else if("custom".equals(teamType)){
            team = Integer.parseInt(teamName.substring(0,teamName.indexOf("[")));
        }
        return  team;
    }
    private Integer parseRanking(String teamName){
        int posStart  = teamName.indexOf("[");
        int team = Integer.parseInt(teamName.substring(posStart+1,teamName.indexOf("]")));
        return  team;
    }
    private Integer getHomeHalfGoals(String resultStr){
        String halfResultStr = resultStr.substring(resultStr.indexOf("(") + 1, resultStr.indexOf(")"));
        int homeHalfGoals = Integer.parseInt(halfResultStr.substring(0,halfResultStr.indexOf(":")));
        return  homeHalfGoals;
    }

    private Integer getCustomHalfGoals(String resultStr){
        String halfResultStr = resultStr.substring(resultStr.indexOf("(") + 1, resultStr.indexOf(")"));
        int homeHalfGoals = Integer.parseInt(halfResultStr.substring(halfResultStr.indexOf(":")+1));
        return  homeHalfGoals;
    }

}
