package com.yuchao.blog.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class BlogComment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String content;
	private String createTime;
	
	@ManyToOne(targetEntity = Blog.class)
	@JoinColumn(name = "blog_id")
	private Blog blog;
	
	@ManyToOne(targetEntity = Visitor.class)
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

	public Blog getBlog() {
		return blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
	}

	public Visitor getVisitor() {
		return visitor;
	}

	public void setVisitor(Visitor visitor) {
		this.visitor = visitor;
	}

	public BlogComment(Long id, String content, String createTime, Blog blog, Visitor visitor) {
		this.id = id;
		this.content = content;
		this.createTime = createTime;
		this.blog = blog;
		this.visitor = visitor;
	}

	protected BlogComment() {
	}
	
	
	
}
