package com.yuchao.blog.dto;

public class TimeLineObject {

	
	private String createTime;
	private String title;
	private String href;
	private String number;
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public TimeLineObject() {
		super();
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public TimeLineObject(String createTime, String title, String href, String number) {
		super();
		this.createTime = createTime;
		this.title = title;
		this.href = href;
		this.number = number;
	}
	
	
	
}
