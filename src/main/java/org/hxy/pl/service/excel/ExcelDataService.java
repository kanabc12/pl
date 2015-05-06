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
            resultVO.setHomeTeam(Integer.parseInt(data.getHomeTeam()));
            resultVO.setCustomTeam(Integer.parseInt(data.getCustomTeam()));
            resultVO.setActualTime(gameDate);
            resultVO.setCustomGoals(parseResultStr("custom",data.getResultStr()));
            resultVO.setPlanDate(gameDate);
            resultVO.setGameStatus(1);
            resultVO.setHomeGoals(parseResultStr("home",data.getResultStr()));
            if(resultVO.getHomeGoals()>resultVO.getCustomGoals()){
                resultVO.setResultType(1);
            }else if(resultVO.getHomeGoals()<resultVO.getCustomGoals()){
                resultVO.setResultType(-1);
            }else{
                resultVO.setResultType(0);
            }
            if("英超".equals(data.getLeagueName())){
                resultVO.setLeagueId(1);
            }else if("西甲".equals(data.getLeagueName())){
                resultVO.setLeagueId(2);
            }
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

    public Integer parseResultStr(String teamType,String resultStr){
        if("home".equals(teamType)){
            return Integer.parseInt(resultStr.substring(0,1));
        }else if("custom".equals(teamType)){
            return Integer.parseInt(resultStr.substring(2,3));
        }
        return 0;
    }
    public Integer getGameResult(String result){
        int homeGoals = Integer.parseInt(result.substring(0,1));
        int customGoals = Integer.parseInt(result.substring(2,3));
        if(homeGoals>customGoals){
            return 1;
        } else if(homeGoals<customGoals){
                return -1;
        }else{
            return 0;
        }
    }

}
