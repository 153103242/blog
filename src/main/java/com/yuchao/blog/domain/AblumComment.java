package com.yuchao.blog.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class AblumComment {

	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY )
	private Long id;
	private String content;
	private String createTime;
	
	@ManyToOne(targetEntity = Ablum.class)
	@JoinColumn(name = "ablum_id")
	private Ablum ablum;
	
	@ManyToOne(targetEntity = Visitor.class )
	@JoinColumn(name = "visitor_id")
	private Visitor visitor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Ablum getAblum() {
		return ablum;
	}

	public void setAblum(Ablum ablum) {
		this.ablum = ablum;
	}

	public Visitor getVisitor() {
		return visitor;
	}

	public void setVisitor(Visitor visitor) {
		this.visitor = visitor;
	}

	public AblumComment(Long id, String content, String createTime, Ablum ablum, Visitor visitor) {
		this.id = id;
		this.content = content;
		this.createTime = createTime;
		this.ablum = ablum;
		this.visitor = visitor;
	}

	protected AblumComment() {
	}

	
	
	
}
