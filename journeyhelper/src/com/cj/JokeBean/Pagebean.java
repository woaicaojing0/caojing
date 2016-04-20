package com.cj.JokeBean;

import java.util.List;

public class Pagebean {
	private int allNum;

	private int allPages;

	private List<Contentlist> contentlist ;

	public int getAllNum() {
		return allNum;
	}

	public void setAllNum(int allNum) {
		this.allNum = allNum;
	}

	public int getAllPages() {
		return allPages;
	}

	public void setAllPages(int allPages) {
		this.allPages = allPages;
	}

	public List<Contentlist> getContentlist() {
		return contentlist;
	}

	public void setContentlist(List<Contentlist> contentlist) {
		this.contentlist = contentlist;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getMaxResult() {
		return maxResult;
	}

	public void setMaxResult(int maxResult) {
		this.maxResult = maxResult;
	}

	private int currentPage;

	private int maxResult;

}
