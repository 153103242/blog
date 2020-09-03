package com.yuchao.blog.social.qq.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

import com.yuchao.blog.properties.BlogSecurityProperties;

@Configuration
@EnableSocial
@Order(1)

public class SocialConfig extends SocialConfigurerAdapter {


	@Autowired
	private DataSource dataSource;
	@Autowired
	private BlogSecurityProperties blogSecurityProperties;
	@Autowired
	private ConnectionFactoryLocator connectionFactoryLocator;
	@Autowired
	private ConnectionSignUp connectionSignUp;
	//登录之后将QQ的信息保存到数据库中
	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		JdbcUsersConnectionRepository jdbcUsersConnectionRepository=new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
		jdbcUsersConnectionRepository.setConnectionSignUp(connectionSignUp);
		return jdbcUsersConnectionRepository;
	}
	//改变拦截路径/auth →/qqLogin
	@Bean
	public SpringSocialConfigurer yuchaoSpringSocialConfigurer() {
		String filterProcessesUrl = blogSecurityProperties.getQqProperties().getFilterProcessesUrl();
		return new YuchaoSpringSocialConfigurer(filterProcessesUrl);
	}
	//在注册的过程中拿到SpringSocial中的信息
	//在业务完成之后，把用户的id传给了SpringSocial
	@Bean 
	public ProviderSignInUtils providerSignInUtils() {
		return new ProviderSignInUtils(connectionFactoryLocator, getUsersConnectionRepository(connectionFactoryLocator));
	}
	//打开connectController
	@Bean 
	public ConnectController connectController(ConnectionFactoryLocator connectionFactoryLocator,ConnectionRepository connectionRepository) {
		
		return new ConnectController(connectionFactoryLocator, connectionRepository);
	}
}
