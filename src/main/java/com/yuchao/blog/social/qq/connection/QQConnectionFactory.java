package com.yuchao.blog.social.qq.connection;

import org.springframework.social.connect.support.OAuth2ConnectionFactory;

import com.yuchao.blog.social.qq.api.QQ;

public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {

	//QQ���ӹ���
	public QQConnectionFactory(String providerId,String appId,String appSecret) {
		super(providerId, new QQServiceProvider(appId, appSecret), new QQAdapter());
	}

	//�ı����ص�����
}
