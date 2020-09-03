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
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Long id;
	private String username;
	private String password;
	private String personalSign;
	private String image;
	private String createTime;
	
	@OneToMany(targetEntity = Mood.class)
	@JoinColumn(name = "user_id")
	private Set<Mood> moods = new HashSet<Mood>();
	
	public User() {
	}

	public Set<Mood> getMoods() {
		return moods;
	}

	public void setMoods(Set<Mood> moods) {
		this.moods = moods;
	}

	public User(Long id, String username, String password, String personalSign, String image, String createTime, Set<Mood> moods) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.personalSign = personalSign;
		this.image = image;
		this.createTime = createTime;
		this.moods = moods;
	}

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
	public String getPersonalSign() {
		return personalSign;
	}
	public void setPersonalSign(String personalSign) {
		this.personalSign = personalSign;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	
}
