package com.yuchao.blog.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuchao.blog.domain.User;
import com.yuchao.blog.service.UserService;


@Component
@WebFilter(urlPatterns = "/*",filterName = "ownerFilter")
public class GetOwnerFilter implements Filter{

	@Autowired
	private UserService userService;
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		User owner = userService.getOwner();
		request.setAttribute("owner", owner);
		
		chain.doFilter(request, response);
	}
	
	
}
