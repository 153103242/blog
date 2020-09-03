package com.yuchao.blog.service;

import com.yuchao.blog.domain.Visitor;

public interface VisitorService {

	Visitor save(Visitor visitor);

	Visitor findVisitorByUsername(String telephone);

}
