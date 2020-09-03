package com.yuchao.blog.serviceImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuchao.blog.domain.Blog;
import com.yuchao.blog.repository.BlogRepository;
import com.yuchao.blog.service.BlogService;

@Service
public class BlogServiceImpl implements BlogService {

	@Autowired
	private BlogRepository blogRepository;
	
	public void saveBlog(Blog blog) {
		blogRepository.save(blog);
	}
	public List<Blog> findBlog() {
		return blogRepository.findAllOrderByCreateTime();
	}
	@Transactional
	public Blog findBlogById(Long id) {
		Blog blog = blogRepository.findById(id).get();
		blog.setViewNumber(blog.getViewNumber()+1);
		return	blog;
	}
	public List<Blog> findAllBlogOrderByCreateTime() {
		return blogRepository.findAllBlogOrderByCreateTime();
	}
	public List<String> findBlogTimeLineOrderByCreateTime() {
		return blogRepository.findBlogTimeLineOrderByCreateTime();
	}
	public List<Blog> findBlogListByKeyword(String keyword) {
		return blogRepository.findBlogListByKeyword(keyword);
	}
	@Override
	public List<Blog> findIndexBlog() {
		return blogRepository.findIndexBlog();
	}
}
