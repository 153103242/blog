package com.yuchao.blog.social.qq.signup;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

import com.yuchao.blog.domain.BlogComment;
import com.yuchao.blog.domain.Message;
import com.yuchao.blog.domain.Visitor;
import com.yuchao.blog.service.VisitorService;

@Component
public class DemoConnectionSignUp implements ConnectionSignUp {

	@Autowired
	private VisitorService visitorService;
	
	//将QQ信息添加到visitor中,并将Id返回
	public String execute(Connection<?> connection) {
		Visitor visitor = new Visitor(null, connection.getDisplayName(), "123456", connection.getImageUrl(),new HashSet<Message>(),new HashSet<BlogComment>());
		visitor = visitorService.save(visitor);
		return visitor.getId().toString();
	}

}
