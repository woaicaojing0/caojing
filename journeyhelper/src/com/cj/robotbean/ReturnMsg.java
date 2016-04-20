package com.cj.robotbean;

import java.util.Date;

public class ReturnMsg {

	private String msg;
	private Type type;
	private Date date;

	public enum Type {
		INCOMING, OUTCOMING
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
