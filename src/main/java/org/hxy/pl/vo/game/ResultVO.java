package org.hxy.pl.vo.game;

import com.alibaba.fastjson.annotation.JSONField;
import org.hxy.pl.vo.BaseVO;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by Administrator on 15-5-3.
 */
public class ResultVO extends BaseVO {
    private Integer id;
    private Integer homeTeam;
    private String homeTeamName;
    private Integer customTeam;
    private String customTeamName;
    private Integer homeGoals;
    private Integer homeHalfGoals;
    private Integer homeSecGoals;
    private Integer customGoals;
    private Integer customHalfGoals;
    private Integer customSecGoals;
    private String resultStr;
    private Integer resultType;
    private Integer homeTeamRanking;
    private Integer customTeamRanking;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date planDate;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date actualTime;
    private String gameCity;
    private Integer leagueId;
    private String leagueName;
    private Integer seasonId;
    private String seasonName;
    private Integer round ;
    private Integer  gameStatus;
    private Integer oddCounts;
    private String gameSummarize;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Integer homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Integer getCustomTeam() {
        return customTeam;
    }

    public void setCustomTeam(Integer customTeam) {
        this.customTeam = customTeam;
    }

    public Integer getHomeGoals() {
        return homeGoals;
    }

    public void setHomeGoals(Integer homeGoals) {
        this.homeGoals = homeGoals;
    }

    public Integer getHomeHalfGoals() {
        return homeHalfGoals;
    }

    public void setHomeHalfGoals(Integer homeHalfGoals) {
        this.homeHalfGoals = homeHalfGoals;
    }

    public Integer getCustomGoals() {
        return customGoals;
    }

    public void setCustomGoals(Integer customGoals) {
        this.customGoals = customGoals;
    }

    public Integer getCustomHalfGoals() {
        return customHalfGoals;
    }

    public void setCustomHalfGoals(Integer customHalfGoals) {
        this.customHalfGoals = customHalfGoals;
    }

    public Integer getResultType() {
        return resultType;
    }

    public void setResultType(Integer resultType) {
        this.resultType = resultType;
    }

    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public Date getActualTime() {
        return actualTime;
    }

    public void setActualTime(Date actualTime) {
        this.actualTime = actualTime;
    }

    public String getGameCity() {
        return gameCity;
    }

    public void setGameCity(String gameCity) {
        this.gameCity = gameCity;
    }

    public Integer getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(Integer leagueId) {
        this.leagueId = leagueId;
    }

    public Integer getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(Integer seasonId) {
        this.seasonId = seasonId;
    }

    public Integer getRound() {
        return round;
    }

    public void setRound(Integer round) {
        this.round = round;
    }

    public Integer getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(Integer gameStatus) {
        this.gameStatus = gameStatus;
    }

    public String getResultStr() {
        return resultStr;
    }

    public void setResultStr(String resultStr) {
        this.resultStr = resultStr;
    }

    public Integer getHomeSecGoals() {
        return homeSecGoals;
    }

    public void setHomeSecGoals(Integer homeSecGoals) {
        this.homeSecGoals = homeSecGoals;
    }

    public Integer getCustomSecGoals() {
        return customSecGoals;
    }

    public void setCustomSecGoals(Integer customSecGoals) {
        this.customSecGoals = customSecGoals;
    }

    public Integer getOddCounts() {
        return oddCounts;
    }

    public void setOddCounts(Integer oddCounts) {
        this.oddCounts = oddCounts;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    public String getCustomTeamName() {
        return customTeamName;
    }

    public void setCustomTeamName(String customTeamName) {
        this.customTeamName = customTeamName;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }

    public String getGameSummarize() {
        return gameSummarize;
    }

    public void setGameSummarize(String gameSummarize) {
        this.gameSummarize = gameSummarize;
    }

    public Integer getHomeTeamRanking() {
        return homeTeamRanking;
    }

    public void setHomeTeamRanking(Integer homeTeamRanking) {
        this.homeTeamRanking = homeTeamRanking;
    }

    public Integer getCustomTeamRanking() {
        return customTeamRanking;
    }

    public void setCustomTeamRanking(Integer customTeamRanking) {
        this.customTeamRanking = customTeamRanking;
    }
}
