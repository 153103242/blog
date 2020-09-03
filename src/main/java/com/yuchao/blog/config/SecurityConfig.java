package com.yuchao.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SpringSocialConfigurer;

import com.yuchao.blog.handler.LoginFailureHandler;
import com.yuchao.blog.handler.LoginSuccessHandler;

//��ȫ������
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Autowired
	private SpringSocialConfigurer springSocialConfigurer;
	@Autowired
	private LoginSuccessHandler loginSuccessHandler;
	@Autowired
	private LoginFailureHandler loginFailureHandler;
	//������
	protected void configure(HttpSecurity http) throws Exception {
		//������Ȩ
		http.formLogin()
		.loginPage("/require")
		.loginProcessingUrl("/loginPage")
		.successHandler(loginSuccessHandler)
		.failureHandler(loginFailureHandler)
		.and().authorizeRequests()
		//��������
		.antMatchers("/searchCatalog","/search","/require","/addVisitor","/judgeValidateCode","/sendSMS","/visitorRegister","/index","/visitorLogin","/","/findMood","/findBlog","/findAblum","/link","/findAllArchives","/gustbook",
		"/css/**","/images/**","/js/**","/layer/**","/social/**","/statics/**","/upload/**").permitAll()
		.anyRequest()
		//��Ҫ�����֤
		.authenticated()
		.and()
		//����springSecurity����ʹ��iframeǶ��ҳ��
		.headers().frameOptions().disable()
		.and()
		//��վ����α��ķ���
		.csrf().disable()
		//���social����
		.apply(springSocialConfigurer);
	}

	
}
