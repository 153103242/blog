package com.yuchao.blog.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Catalog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String catalog;
	
	//�����ǩ��Ӧ�������
	@ManyToMany(mappedBy = "catalogs")//����ά����ά����ϵ�������͵�catalogs
	private Set<Blog> blogs =new HashSet<Blog>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public Set<Blog> getBlogs() {
		return blogs;
	}

	public void setBlogs(Set<Blog> blogs) {
		this.blogs = blogs;
	}

	public Catalog(Long id, String catalog, Set<Blog> blogs) {
		super();
		this.id = id;
		this.catalog = catalog;
		this.blogs = blogs;
	}

	public Catalog() {
		super();
	}
	
	
	
}
