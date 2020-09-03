package com.yuchao.blog.social.qq.connection;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

import com.yuchao.blog.social.qq.api.QQ;
import com.yuchao.blog.social.qq.api.QQImpl;
import com.yuchao.blog.social.qq.template.QQOauth2Template;

public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQ> {

	
	private static final String URL_AUTHORIZE="https://graph.qq.com/oauth2.0/authorize";
	private static final String URL_ACCESSTOKEN="https://graph.qq.com/oauth2.0/token";
	
	private String appId;
	//1~6
	public QQServiceProvider(String clientId,String clientSecret) {
		super(new QQOauth2Template(clientId, clientSecret, URL_AUTHORIZE, URL_ACCESSTOKEN));
		this.appId=clientId;
	}

	//7~8
	public QQ getApi(String accessToken) {
		// TODO Auto-generated method stub
		return new QQImpl(accessToken,appId );
	}

	
}
