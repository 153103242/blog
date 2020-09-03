package com.yuchao.blog.dto;

import java.util.List;


public class PageBean {

	//页面大小
	private Integer pageSize;
	//总页数
	private Integer totalPage;
	//总条数
	private Integer totalCount;
	//当前页数
	private Integer currentPage;
	//数据
	private List list;
	
	public PageBean(Integer pageSize ,Integer totalCount,Integer currentPage ) {
		this.pageSize=pageSize;
		this.totalCount=totalCount;
		this.currentPage=currentPage;
		
		//空校验
		if (this.pageSize==null) {
			this.pageSize=6;
		}
		if (this.currentPage==null) {
			this.currentPage=1;
		}
		this.totalPage=(int) Math.ceil(1.0*this.totalCount/this.pageSize);
		//当前页数校验
		if (this.currentPage<1) {
			this.currentPage=1;
		}
		if (this.currentPage>this.totalPage && this.totalCount!=0) {
			this.currentPage=this.totalPage;
		}
	}
	//获取开始索引
	public Integer getStartIndex() {
		return (this.currentPage-1)*this.pageSize;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	
}
