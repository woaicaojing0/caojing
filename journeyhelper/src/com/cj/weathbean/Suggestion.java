package com.cj.weathbean;

public class Suggestion {
	private Comf comf;

	private Cw cw;//汽车指数

	private Drsg drsg;//穿衣指数

	private Flu flu;//感冒指数

	private Sport sport;//运动指数

	private Trav trav;//旅游指数

	private Uv uv;//紫外线指数

	public void setComf(Comf comf) {
		this.comf = comf;
	}

	public Comf getComf() {
		return this.comf;
	}

	public void setCw(Cw cw) {
		this.cw = cw;
	}

	public Cw getCw() {
		return this.cw;
	}

	public void setDrsg(Drsg drsg) {
		this.drsg = drsg;
	}

	public Drsg getDrsg() {
		return this.drsg;
	}

	public void setFlu(Flu flu) {
		this.flu = flu;
	}

	public Flu getFlu() {
		return this.flu;
	}

	public void setSport(Sport sport) {
		this.sport = sport;
	}

	public Sport getSport() {
		return this.sport;
	}

	public void setTrav(Trav trav) {
		this.trav = trav;
	}

	public Trav getTrav() {
		return this.trav;
	}

	public void setUv(Uv uv) {
		this.uv = uv;
	}

	public Uv getUv() {
		return this.uv;
	}
}
