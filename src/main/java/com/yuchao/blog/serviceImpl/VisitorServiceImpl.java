package com.yuchao.blog.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuchao.blog.domain.Visitor;
import com.yuchao.blog.repository.VisitorRepository;
import com.yuchao.blog.service.VisitorService;

@Component
public class VisitorServiceImpl implements VisitorService {

	@Autowired
	private VisitorRepository visitorRepository;
	
	
	public Visitor save(Visitor visitor) {
		return visitorRepository.save(visitor);
	}


	public Visitor findVisitorByUsername(String telephone) {
		return visitorRepository.findVisitorByUsername(telephone);
	}

}
