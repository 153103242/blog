package com.yuchao.blog.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;



@Component
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	
	//登录失败之后的处理
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		request.setAttribute("error", "用户名或者密码错误!");
		request.getRequestDispatcher(request.getContextPath()+"/visitorLogin").forward(request, response);
	}
}
