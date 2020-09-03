package com.yuchao.blog.dto;

import java.util.List;


public class PageBean {

	//ҳ���С
	private Integer pageSize;
	//��ҳ��
	private Integer totalPage;
	//������
	private Integer totalCount;
	//��ǰҳ��
	private Integer currentPage;
	//����
	private List list;
	
	public PageBean(Integer pageSize ,Integer totalCount,Integer currentPage ) {
		this.pageSize=pageSize;
		this.totalCount=totalCount;
		this.currentPage=currentPage;
		
		//��У��
		if (this.pageSize==null) {
			this.pageSize=6;
		}
		if (this.currentPage==null) {
			this.currentPage=1;
		}
		this.totalPage=(int) Math.ceil(1.0*this.totalCount/this.pageSize);
		//��ǰҳ��У��
		if (this.currentPage<1) {
			this.currentPage=1;
		}
		if (this.currentPage>this.totalPage && this.totalCount!=0) {
			this.currentPage=this.totalPage;
		}
	}
	//��ȡ��ʼ����
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
