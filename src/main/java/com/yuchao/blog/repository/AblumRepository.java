package com.yuchao.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.yuchao.blog.domain.Ablum;

public interface AblumRepository extends CrudRepository<Ablum, Long> {

	
	@Query(value = "select * from ablum order by create_time desc",nativeQuery =  true)
	List<Ablum> findAllAblumOrderByCreateTime();
	
	@Query(value = "select create_time from ablum order by create_time desc",nativeQuery =  true)
	List<String> findAblumTimeLineOrderByCreateTime();
	@Query(value = "select * from ablum where title like %?1% or content like %?1% order by create_time desc ",nativeQuery = true)
	List<Ablum> findAblumListByKeyword(String keyword);

	@Query(value = "select * from ablum order by create_time Desc limit 0,3",nativeQuery = true)
	List<Ablum> findIndexAblum();
	@Query(value = "select * from ablum order by create_time Desc",nativeQuery = true)
	List<Ablum> findAllOrderByCreateTime();

}
