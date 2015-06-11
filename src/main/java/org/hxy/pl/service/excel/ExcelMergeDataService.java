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
public class ExcelMergeDataService {
    private final Logger log = LoggerFactory.getLogger(ExcelMergeDataService.class);

    @Autowired
    private ResultDao resultDao;
    @Autowired
    private OddDao oddDao;

    /**
     * 导出文件的最大大小 超过这个大小会压缩
     */

    public void doBatchSave(final ExcelVO data) {
            ResultVO resultVO =  new ResultVO();
            Date  gameDate = DateUtils.StringToDate(data.getGameTimeStr(), DateStyle.YYYY_MM_DD_HH_MM_SS);
            resultVO.setHomeTeam(parseTeam(data.getHomeTeam()));
            resultVO.setCustomTeam(parseTeam(data.getCustomTeam()));
            resultVO.setActualTime(gameDate);
            resultVO.setCustomGoals(parseResultStr("custom",data.getResultStr()));
            resultVO.setPlanDate(gameDate);
            resultVO.setGameStatus(1);
            resultVO.setResultStr(data.getResultStr());
            resultVO.setHomeGoals(parseResultStr("home",data.getResultStr()));
            if(resultVO.getHomeGoals()>resultVO.getCustomGoals()){
                resultVO.setResultType(1);
            }else if(resultVO.getHomeGoals()<resultVO.getCustomGoals()){
                resultVO.setResultType(-1);
            }else{
                resultVO.setResultType(0);
            }
            resultVO.setLeagueId(Integer.parseInt(data.getLeagueName()));
            resultVO.setHomeTeamRanking(parseRanking(data.getHomeTeam()));
            resultVO.setCustomTeamRanking(parseRanking(data.getCustomTeam()));
            resultVO.setRound(Integer.parseInt(data.getRound()));
            resultVO.setSeasonId(Integer.parseInt(data.getSeasonId()));
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
            oddDao.saveGameOdd(wOdd);
            OddVO wOddFinal = new OddVO();
            wOddFinal.setGemeId(gameId);
            wOddFinal.setCompanyId(1);
            wOddFinal.setAddDate(gameDate);
            wOddFinal.setWinOdd(Float.parseFloat(data.getWwfOdd()));
            wOddFinal.setDrawOdd(Float.parseFloat(data.getWdfOdd()));
            wOddFinal.setLoseOdd(Float.parseFloat(data.getWlfOdd()));
            wOddFinal.setType(2);
            oddDao.saveGameOdd(wOddFinal);
            OddVO lOdd = new OddVO();
            lOdd.setGemeId(gameId);
            lOdd.setCompanyId(2);
            lOdd.setAddDate(gameDate);
            lOdd.setWinOdd(Float.parseFloat(data.getLwiOdd()));
            lOdd.setDrawOdd(Float.parseFloat(data.getLdiOdd()));
            lOdd.setLoseOdd(Float.parseFloat(data.getLliOdd()));
            lOdd.setType(1);
            oddDao.saveGameOdd(lOdd);
            OddVO lOddFinal = new OddVO();
            lOddFinal.setGemeId(gameId);
            lOddFinal.setCompanyId(2);
            lOddFinal.setAddDate(gameDate);
            lOddFinal.setWinOdd(Float.parseFloat(data.getLwfOdd()));
            lOddFinal.setDrawOdd(Float.parseFloat(data.getLdfOdd()));
            lOddFinal.setLoseOdd(Float.parseFloat(data.getLlfOdd()));
            lOddFinal.setType(2);
            oddDao.saveGameOdd(lOddFinal);
    }


    public Integer parseResultStr(String teamType,String resultStr){
        if("home".equals(teamType)){
            return Integer.parseInt(resultStr.substring(0,1));
        }else if("custom".equals(teamType)){
            return Integer.parseInt(resultStr.substring(2,3));
        }
        return 0;
    }
    private Integer parseRanking(String teamName){
        int posStart  = teamName.indexOf("[");
        int posEnd = teamName.indexOf("]");
        int ranking = Integer.parseInt(teamName.substring(posStart + 1, posEnd));
        return  ranking;
    }

    private Integer parseTeam(String teamName){
        int posStart  = teamName.indexOf("[");
        int ranking = Integer.parseInt(teamName.substring(0,posStart));
        return  ranking;
    }

    @Async
    public void importMergeExcel2007(final InputStream is){
        ExcelMergeDataService proxy = ((ExcelMergeDataService) AopContext.currentProxy());
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
            ContentHandler handler = new Excel2007ImportMergeSheetHandler(proxy, dataList,sst);

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
        }catch (Exception e){
            log.error("excel import error", e);
        }finally {
            IOUtils.closeQuietly(bis);
        }
    }

}
