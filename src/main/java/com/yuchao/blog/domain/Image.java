package com.yuchao.blog.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Image {

	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private Long id;
	private String image;
	
	@ManyToOne(targetEntity = Ablum.class)
	@JoinColumn(name = "ablum_id")
	private Ablum ablum;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Ablum getAblum() {
		return ablum;
	}

	public void setAblum(Ablum ablum) {
		this.ablum = ablum;
	}

	public Image(Long id, String image, Ablum ablum) {
		this.id = id;
		this.image = image;
		this.ablum = ablum;
	}

	public Image() {
	}
	
	
	
	
}
