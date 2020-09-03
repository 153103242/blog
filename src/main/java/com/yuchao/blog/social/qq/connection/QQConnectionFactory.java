package com.yuchao.blog.social.qq.connection;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;

import com.yuchao.blog.social.qq.api.QQ;

public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {

	//QQ连接工厂
	public QQConnectionFactory(String providerId,String appId,String appSecret) {
		super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());
	}

	//改变拦截的请求
}
