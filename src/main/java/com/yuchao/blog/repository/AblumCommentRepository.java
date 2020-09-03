package com.yuchao.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.yuchao.blog.domain.AblumComment;

public interface AblumCommentRepository extends CrudRepository<AblumComment, Long> {

	@Query(value = "select count(*) from ablum_comment where ablum_id=?1",nativeQuery = true)
	Integer findAblumCommentCountByAblumId(String id);
	@Query(value = "select * from ablum_comment where ablum_id=?1 order by create_time desc limit ?2,?3",nativeQuery = true)
	List<AblumComment> findAblumCommentListByLimit(String id, Integer startIndex, Integer currentPage);

}
