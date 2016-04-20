package com.cj.weathbean;

public class Now {
	private CondNow cond;//天气代码和天气详情

	private String fl;//体感温度

	private String hum;//温度%

	private String pcpn;//降雨量(MM)

	private String pres;//气压

	private String tmp;//当前温度 

	private String vis;//能见度

	private Wind wind;//风力状况

	public void setCond(CondNow cond) {
		this.cond = cond;
	}

	public CondNow getCond() {
		return this.cond;
	}

	public void setFl(String fl) {
		this.fl = fl;
	}

	public String getFl() {
		return this.fl;
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

	public void setPres(String pres) {
		this.pres = pres;
	}

	public String getPres() {
		return this.pres;
	}

	public void setTmp(String tmp) {
		this.tmp = tmp;
	}

	public String getTmp() {
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
