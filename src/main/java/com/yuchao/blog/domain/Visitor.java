package com.yuchao.blog.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;


@Entity
public class Visitor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String username;
	private String password;
	private String image;
	
	@OneToMany(targetEntity =  Message.class)
	@JoinColumn(name = "visitor_id")
	private Set<Message> messages = new HashSet<Message>();
	//一个游客可以有多个博客评论
	@OneToMany(targetEntity = BlogComment.class)
	@JoinColumn(name = "visitor_id")
	private Set<BlogComment> blogComments = new HashSet<BlogComment>();
	
	@OneToMany(targetEntity = AblumComment.class)
	@JoinColumn(name = "visitor_id")
	private Set<AblumComment> ablumComments = new HashSet<AblumComment>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	protected Visitor() {
	}
	public Set<Message> getMessages() {
		return messages;
	}
	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}
	public Set<BlogComment> getBlogComments() {
		return blogComments;
	}
	public void setBlogComments(Set<BlogComment> blogComments) {
		this.blogComments = blogComments;
	}
	public Visitor(Long id, String username, String password, String image, Set<Message> messages, Set<BlogComment> blogComments) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.image = image;
		this.messages = messages;
		this.blogComments = blogComments;
	}
	
	
}
