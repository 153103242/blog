package com.yuchao.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.yuchao.blog.domain.BlogComment;

public interface BlogCommentRepository extends CrudRepository<BlogComment, Long> {

	@Query(value =   "select count(*) from blog_comment where blog_id=?1",nativeQuery = true)
	Integer findBlogCommentCountByBlogId(Long id);
	
	@Query(value =   "select * from blog_comment where blog_id=?1 order by create_time DESC limit ?2,?3 ",nativeQuery = true)
	List<BlogComment> getBlogCommentPageBean(Long id, Integer startIndex, Integer pageSize);


}
