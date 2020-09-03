package com.yuchao.blog.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class Blog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	//标题
	private String title;
	//内容
	private String content;
	//创建时间
	private String createTime;
	//浏览数
	private Long viewNumber;
	//回复数
	private Long commentNumber;
	//封面
	private String cover;
	//图片
	private String image; 
	//小标题
	private String littleTitle;
	//小内容
	private String littleContent;
	
	//多个博客可以对应一个说说
	@ManyToOne(targetEntity = Mood.class)
	@JoinColumn(name = "mood_id")
	private Mood mood;
	//多个博客多赢多个标签
	@ManyToMany( cascade = CascadeType.PERSIST)//标签将维护关系交给博客,设置级联
	@JoinTable(
			name = "blog_catalog",//第三方表名
			joinColumns = @JoinColumn(name = "blog_id"),//维护关系方的外键
			inverseJoinColumns = @JoinColumn(name="catalog_id")//放弃维护方的外键
			)
	private Set<Catalog> catalogs = new HashSet<Catalog>();
	//一个博客拥有多个博客评论
	@OneToMany(targetEntity = BlogComment.class)
	@JoinColumn(name = "blog_id")
	private Set<BlogComment> blogComments = new HashSet<BlogComment>();
	
	public Mood getMood() {
		return mood;
	}
	public void setMood(Mood mood) {
		this.mood = mood;
	}
	public Long getId() {
		return id;
	}
	public Set<Catalog> getCatalogs() {
		return catalogs;
	}
	public void setCatalogs(Set<Catalog> catalogs) {
		this.catalogs = catalogs;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getLittleTitle() {
		return littleTitle;
	}
	public void setLittleTitle(String littleTitle) {
		this.littleTitle = littleTitle;
	}
	public String getLittleContent() {
		return littleContent;
	}
	public void setLittleContent(String littleContent) {
		this.littleContent = littleContent;
	}
	public Blog() {
	}
	@Override
	public String toString() {
		return "Blog [id=" + id + ", title=" + title + ", content=" + content + ", createTime=" + createTime + ", viewNumber=" + viewNumber + ", commentNumber=" + commentNumber + ", cover=" + cover + ", image=" + image + ", littleTitle=" + littleTitle + ", littleContent=" + littleContent + ", mood="
				+ mood + ", catalogs=" + catalogs + "]";
	}
	public Long getViewNumber() {
		return viewNumber;
	}
	public void setViewNumber(Long viewNumber) {
		this.viewNumber = viewNumber;
	}
	public Long getCommentNumber() {
		return commentNumber;
	}
	public void setCommentNumber(Long commentNumber) {
		this.commentNumber = commentNumber;
	}
	public Set<BlogComment> getBlogComments() {
		return blogComments;
	}
	public void setBlogComments(Set<BlogComment> blogComments) {
		this.blogComments = blogComments;
	}
	protected Blog(Long id, String title, String content, String createTime, Long viewNumber, Long commentNumber, String cover, String image, String littleTitle, String littleContent, Mood mood, Set<Catalog> catalogs, Set<BlogComment> blogComments) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.createTime = createTime;
		this.viewNumber = viewNumber;
		this.commentNumber = commentNumber;
		this.cover = cover;
		this.image = image;
		this.littleTitle = littleTitle;
		this.littleContent = littleContent;
		this.mood = mood;
		this.catalogs = catalogs;
		this.blogComments = blogComments;
	}
	
}
