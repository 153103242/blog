package com.yuchao.blog.social.weixin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;

import com.yuchao.blog.properties.BlogSecurityProperties;
import com.yuchao.blog.properties.WeixinProperties;
import com.yuchao.blog.social.weixin.connection.WeixinConnectionFactory;

@Configuration
@EnableSocial
public class WeixinConfig extends SocialConfigurerAdapter  {

	@Autowired
	private BlogSecurityProperties blogSecurityProperties;
	
	public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {
		WeixinProperties weixinProperties = blogSecurityProperties.getWeixinProperties();
		WeixinConnectionFactory weixinConnectionFactory = new WeixinConnectionFactory(weixinProperties.getProviderId(),weixinProperties.getAppId(), weixinProperties.getAppSecret());
		connectionFactoryConfigurer.addConnectionFactory(weixinConnectionFactory);
	}

	
}
