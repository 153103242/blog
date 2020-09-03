package com.yuchao.blog.social.qq.config;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

public class YuchaoSpringSocialConfigurer extends SpringSocialConfigurer {

	String filterProcessesUrl;
	
	
	//��ʼ��filterProcessesUrl
	public YuchaoSpringSocialConfigurer(String filterProcessesUrl) {
		this.filterProcessesUrl=filterProcessesUrl;
	}

	//�ı�����·��
	protected <T> T postProcess(T object) {
		//���������
		SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
		filter.setFilterProcessesUrl(filterProcessesUrl);
		return (T) filter;
	}
	
	
}
