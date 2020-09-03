package com.yuchao.blog.service;

import java.util.List;

import com.yuchao.blog.domain.Blog;

public interface BlogService {

	void saveBlog(Blog blog);

	List<Blog> findBlog();

	Blog findBlogById(Long id);

	List<Blog> findAllBlogOrderByCreateTime();

	List<String> findBlogTimeLineOrderByCreateTime();

	List<Blog> findBlogListByKeyword(String keyword);

	List<Blog> findIndexBlog();

}
