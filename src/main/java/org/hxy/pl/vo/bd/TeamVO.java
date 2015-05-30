package org.hxy.pl.vo.bd;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class TeamVO {
	private Long id;
	private String name;
    private String nameEn;
	private String nameAbbr;
	private Long contry;
	private String logo;
	private String coach;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date buildDate;
	private Long type;
    private String contryName;

    private int homeWin;
    private int homeDraw;
    private int homeLose;
    private int customWin;
    private int customDraw;
    private int customLose;

    public String getContryName() {
        return contryName;
    }

    public void setContryName(String contryName) {
        this.contryName = contryName;
    }

    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNameAbbr() {
		return nameAbbr;
	}
	public void setNameAbbr(String nameAbbr) {
		this.nameAbbr = nameAbbr;
	}
	public Long getContry() {
		return contry;
	}
	public void setContry(Long contry) {
		this.contry = contry;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getCoach() {
		return coach;
	}
	public void setCoach(String coach) {
		this.coach = coach;
	}
	public Date getBuildDate() {
		return buildDate;
	}
	public void setBuildDate(Date buildDate) {
		this.buildDate = buildDate;
	}
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public int getHomeWin() {
        return homeWin;
    }

    public void setHomeWin(int homeWin) {
        this.homeWin = homeWin;
    }

    public int getHomeDraw() {
        return homeDraw;
    }

    public void setHomeDraw(int homeDraw) {
        this.homeDraw = homeDraw;
    }

    public int getHomeLose() {
        return homeLose;
    }

    public void setHomeLose(int homeLose) {
        this.homeLose = homeLose;
    }

    public int getCustomWin() {
        return customWin;
    }

    public void setCustomWin(int customWin) {
        this.customWin = customWin;
    }

    public int getCustomDraw() {
        return customDraw;
    }

    public void setCustomDraw(int customDraw) {
        this.customDraw = customDraw;
    }

    public int getCustomLose() {
        return customLose;
    }

    public void setCustomLose(int customLose) {
        this.customLose = customLose;
    }
}
