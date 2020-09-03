package com.yuchao.blog.social.qq.config;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

public class YuchaoSpringSocialConfigurer extends SpringSocialConfigurer {

	String filterProcessesUrl;
	
	
	//初始化filterProcessesUrl
	public YuchaoSpringSocialConfigurer(String filterProcessesUrl) {
		this.filterProcessesUrl=filterProcessesUrl;
	}

	//改变拦截路径
	protected <T> T postProcess(T object) {
		//获得拦截器
		SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
		filter.setFilterProcessesUrl(filterProcessesUrl);
		return (T) filter;
	}
	
	
}
