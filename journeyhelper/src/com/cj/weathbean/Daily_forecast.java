package com.cj.weathbean;

public class Daily_forecast {
	private Astro astro;//天文数值

	private Cond cond;//天气状况

	private String date;//当地时间

	private String hum;//湿度%

	private String pcpn;//降雨量(mm)

	private String pop;//降水概率

	private String pres;//气压

	private Tmp tmp;//温度

	private String vis;//能见度

	private Wind wind;//

	public void setAstro(Astro astro) {
		this.astro = astro;
	}

	public Astro getAstro() {
		return this.astro;
	}

	public void setCond(Cond cond) {
		this.cond = cond;
	}

	public Cond getCond() {
		return this.cond;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDate() {
		return this.date;
	}

	public void setHum(String hum) {
		this.hum = hum;
	}

	public String getHum() {
		return this.hum;
	}

	public void setPcpn(String pcpn) {
		this.pcpn = pcpn;
	}

	public String getPcpn() {
		return this.pcpn;
	}

	public void setPop(String pop) {
		this.pop = pop;
	}

	public String getPop() {
		return this.pop;
	}

	public void setPres(String pres) {
		this.pres = pres;
	}

	public String getPres() {
		return this.pres;
	}

	public void setTmp(Tmp tmp) {
		this.tmp = tmp;
	}

	public Tmp getTmp() {
		return this.tmp;
	}

	public void setVis(String vis) {
		this.vis = vis;
	}

	public String getVis() {
		return this.vis;
	}

	public void setWind(Wind wind) {
		this.wind = wind;
	}

	public Wind getWind() {
		return this.wind;
	}
}
