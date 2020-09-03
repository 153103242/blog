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
	//��¼֮��QQ����Ϣ���浽���ݿ���
	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		JdbcUsersConnectionRepository jdbcUsersConnectionRepository=new JdbcUsersConnectionRepository(dataSource, connectionFactoryLocator, Encryptors.noOpText());
		jdbcUsersConnectionRepository.setConnectionSignUp(connectionSignUp);
		return jdbcUsersConnectionRepository;
	}
	//�ı�����·��/auth ��/qqLogin
	@Bean
	public SpringSocialConfigurer yuchaoSpringSocialConfigurer() {
		String filterProcessesUrl = blogSecurityProperties.getQqProperties().getFilterProcessesUrl();
		return new YuchaoSpringSocialConfigurer(filterProcessesUrl);
	}
	//��ע��Ĺ������õ�SpringSocial�е���Ϣ
	//��ҵ�����֮�󣬰��û���id������SpringSocial
	@Bean 
	public ProviderSignInUtils providerSignInUtils() {
		return new ProviderSignInUtils(connectionFactoryLocator, getUsersConnectionRepository(connectionFactoryLocator));
	}
	//��connectController
	@Bean 
	public ConnectController connectController(ConnectionFactoryLocator connectionFactoryLocator,ConnectionRepository connectionRepository) {
		
		return new ConnectController(connectionFactoryLocator, connectionRepository);
	}
}
