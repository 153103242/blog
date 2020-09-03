package com.yuchao.blog.social.weixin.connection;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

import com.yuchao.blog.social.weixin.api.Weixin;

public class WeixinConnectionFactory extends OAuth2ConnectionFactory<Weixin> {

	// 创建微信连接工厂
	public WeixinConnectionFactory(String providerId, String appId, String appSerect) {
		super(providerId, new WeixinServiceProvider(appId, appSerect), new WeixinAdapter());
	}
	//重写父类的createConnection方法，因为父类的这个方法，直接返回传入的adapter,但是在构造方法中，没有办法可以直接为adapter赋值openId.
	public Connection<Weixin> createConnection(AccessGrant accessGrant) {
		return new OAuth2Connection<Weixin>(getProviderId(), extractProviderUserId(accessGrant), accessGrant.getAccessToken(), accessGrant.getRefreshToken(), accessGrant.getExpireTime(), getOAuth2ServiceProvider(), getApiAdapter(extractProviderUserId(accessGrant)));
	}
	//重写父类的createConnection方法，因为父类的这个方法，直接返回传入的adapter,但是在构造方法中，没有办法可以直接为adapter赋值openId.
	public Connection<Weixin> createConnection(ConnectionData data) {
		return new OAuth2Connection<Weixin>(data, getOAuth2ServiceProvider(), getApiAdapter(data.getProviderUserId()));
	}

	//openid，因为父类中直接return null，正常的token中不带openid,但是微信的token带openid,所以不能return null，要return token里的openid
	protected String extractProviderUserId(AccessGrant accessGrant) {
		if (accessGrant instanceof WeixinAccessGrant) {
			return ((WeixinAccessGrant) accessGrant).getOpenId();
		}
		return null;
	}

	//返回serviceProvider
	protected OAuth2ServiceProvider<Weixin> getOAuth2ServiceProvider() {
		return (OAuth2ServiceProvider<Weixin>) getServiceProvider();
	}

	//返回weixinAdapter
	protected ApiAdapter<Weixin> getApiAdapter(String providerUserId) {
		return new WeixinAdapter(providerUserId);
	}

}
