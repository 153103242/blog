package com.yuchao.blog.social.qq.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.security.AuthenticationNameUserIdSource;

import com.yuchao.blog.properties.BlogSecurityProperties;
import com.yuchao.blog.properties.QQProperties;
import com.yuchao.blog.social.qq.connection.QQConnectionFactory;

@Configuration
@EnableSocial
@Order(2)
public class QQConfig extends SocialConfigurerAdapter{


	@Autowired
	private BlogSecurityProperties blogSecurityProperties;
	
	//添加QQ连接工厂
	public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
		
		//创建QQ属性
		QQProperties qqConfig= blogSecurityProperties.getQqProperties();
		
		//创建QQ连接工厂
		QQConnectionFactory qqConnectionFactory = new QQConnectionFactory(qqConfig.getProviderId(), qqConfig.getAppId(), qqConfig.getAppSecret());
		
		//添加QQ连接工厂
		connectionFactoryConfigurer.addConnectionFactory(qqConnectionFactory);
		
	}

	//获取登录人
	public UserIdSource getUserIdSource() {
		return new AuthenticationNameUserIdSource();
	}

}
