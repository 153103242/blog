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

//安全配置类
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
	//做拦截
	protected void configure(HttpSecurity http) throws Exception {
		//请求授权
		http.formLogin()
		.loginPage("/require")
		.loginProcessingUrl("/loginPage")
		.successHandler(loginSuccessHandler)
		.failureHandler(loginFailureHandler)
		.and().authorizeRequests()
		//所有请求
		.antMatchers("/searchCatalog","/search","/require","/addVisitor","/judgeValidateCode","/sendSMS","/visitorRegister","/index","/visitorLogin","/","/findMood","/findBlog","/findAblum","/link","/findAllArchives","/gustbook",
		"/css/**","/images/**","/js/**","/layer/**","/social/**","/statics/**","/upload/**").permitAll()
		.anyRequest()
		//需要身份认证
		.authenticated()
		.and()
		//配置springSecurity允许使用iframe嵌入页面
		.headers().frameOptions().disable()
		.and()
		//跨站请求伪造的防护
		.csrf().disable()
		//添加social配置
		.apply(springSocialConfigurer);
	}

	
}
