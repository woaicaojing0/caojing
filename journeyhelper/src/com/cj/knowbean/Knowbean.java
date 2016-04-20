package com.cj.knowbean;

import java.util.List;

public class Knowbean {
	private String date;
	private List<stories> stories;
	private List<top_stories> top_stories;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<stories> getStories() {
		return stories;
	}

	public void setStories(List<stories> stories) {
		this.stories = stories;
	}

	public List<top_stories> getTop_stories() {
		return top_stories;
	}

	public void setTop_stories(List<top_stories> top_stories) {
		this.top_stories = top_stories;
	}

}
