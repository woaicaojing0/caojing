package com.cj.weathbean;

public class Basic {
	private String city;//城市名称

	private String cnty;//国家名称

	private String id;//城市ID

	private String lat;//纬度

	private String lon;//经度

	private Update update;//数据更新时间

	public void setCity(String city) {
		this.city = city;
	}

	public String getCity() {
		return this.city;
	}

	public void setCnty(String cnty) {
		this.cnty = cnty;
	}

	public String getCnty() {
		return this.cnty;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLat() {
		return this.lat;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getLon() {
		return this.lon;
	}

	public void setUpdate(Update update) {
		this.update = update;
	}

	public Update getUpdate() {
		return this.update;
	}
}
