package org.hxy.pl.vo.bd;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class TeamVO {
	private Long id;
	private String name;
	private String nameAbbr;
	private Long contry;
	private String logo;
	private String coach;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date buildDate;
	private Long type;
    private String contryName;
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
	
}
