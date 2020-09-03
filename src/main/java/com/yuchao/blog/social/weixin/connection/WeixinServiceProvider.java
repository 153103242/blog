package com.yuchao.blog.social.weixin.connection;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

import com.yuchao.blog.social.weixin.api.Weixin;
import com.yuchao.blog.social.weixin.api.WeixinImpl;
import com.yuchao.blog.social.weixin.template.WeixinOauth2Template;

public class WeixinServiceProvider extends AbstractOAuth2ServiceProvider<Weixin> {

	private static final String URL_AUTHORIZE ="https://open.weixin.qq.com/connect/qrconnect";
	private static final String URL_ACCESSTOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token" ;
	
	//1~6
	public WeixinServiceProvider(String appId,String appSerect) {
		super(new WeixinOauth2Template(appId, appSerect, URL_AUTHORIZE, URL_ACCESSTOKEN));
	}

	
	//7~8
	public Weixin getApi(String accessToken) {
		return new WeixinImpl(accessToken);
	}

	
}
