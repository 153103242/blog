package com.yuchao.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.yuchao.blog.domain.Message;

public interface MessageRepository extends CrudRepository<Message, Long> {

	@Query(value = "select count(*) from message", nativeQuery = true)
	Integer findMessageCount();

	@Query(value = "select * from message order by create_time Desc limit ?1,?2" , nativeQuery = true)
	List<Message> findMessageByLimit(Integer startIndex, Integer pageSize);

}
