package com.yuchao.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.yuchao.blog.domain.Mood;

public interface MoodRepository extends CrudRepository<Mood, Long> {
	@Query(value = "select * from mood order by create_time Desc limit 0,3",nativeQuery = true)
	List<Mood> findIndexMood();
	@Query(value = "select * from mood order by create_time Desc ",nativeQuery = true)
	List<Mood> findAllOrderByCreateTime();

}
