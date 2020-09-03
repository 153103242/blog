package com.yuchao.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.yuchao.blog.domain.Blog;

public interface BlogRepository extends CrudRepository<Blog, Long> {

	
	@Query(value = "select * from blog order by create_time desc" ,nativeQuery = true)
	List<Blog> findAllBlogOrderByCreateTime();
	@Query(value = "select create_time from blog order by create_time desc" ,nativeQuery = true)
	List<String> findBlogTimeLineOrderByCreateTime();
	@Query(value = "select * from blog  where title like %?1% or content like %?1% order by create_time desc",nativeQuery = true)
	List<Blog> findBlogListByKeyword(String keyword);
	@Query(value = "select * from blog order by create_time Desc limit 0,3",nativeQuery = true)
	List<Blog> findIndexBlog();
	@Query(value = "select * from blog order by create_time Desc",nativeQuery = true)
	List<Blog> findAllOrderByCreateTime();

}
