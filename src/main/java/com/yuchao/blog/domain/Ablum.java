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
public class Ablum {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String content;
	private String cover;
	private String createTime;
	private Long imageNumber;
	
	@OneToMany(targetEntity = Image.class )
	@JoinColumn(name = "ablum_id")
	private Set<Image> images = new HashSet<Image>();

	@OneToMany(targetEntity = AblumComment.class)
	@JoinColumn(name = "ablum_id")
	private Set<AblumComment> ablumComments = new HashSet<AblumComment>();
	
	public Long getId() {
		return id;
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

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Long getImageNumber() {
		return imageNumber;
	}

	public void setImageNumber(Long imageNumber) {
		this.imageNumber = imageNumber;
	}

	public Set<Image> getImages() {
		return images;
	}

	public void setImages(Set<Image> images) {
		this.images = images;
	}



	public Ablum(Long id, String title, String content, String cover, String createTime, Long imageNumber, Set<Image> images, Set<AblumComment> ablumComments) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.cover = cover;
		this.createTime = createTime;
		this.imageNumber = imageNumber;
		this.images = images;
		this.ablumComments = ablumComments;
	}

	public Set<AblumComment> getAblumComments() {
		return ablumComments;
	}

	public void setAblumComments(Set<AblumComment> ablumComments) {
		this.ablumComments = ablumComments;
	}

	public Ablum() {
	}
	
	
}
