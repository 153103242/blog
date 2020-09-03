package com.yuchao.blog.repository;

import org.springframework.data.repository.CrudRepository;

import com.yuchao.blog.domain.Visitor;

public interface VisitorRepository extends CrudRepository<Visitor, Long> {

	Visitor findVisitorByUsername(String telephone);

	
}
