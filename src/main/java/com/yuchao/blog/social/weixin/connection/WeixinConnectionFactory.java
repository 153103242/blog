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

	// ����΢�����ӹ���
	public WeixinConnectionFactory(String providerId, String appId, String appSerect) {
		super(providerId, new WeixinServiceProvider(appId, appSerect), new WeixinAdapter());
	}
	//��д�����createConnection��������Ϊ��������������ֱ�ӷ��ش����adapter,�����ڹ��췽���У�û�а취����ֱ��Ϊadapter��ֵopenId.
	public Connection<Weixin> createConnection(AccessGrant accessGrant) {
		return new OAuth2Connection<Weixin>(getProviderId(), extractProviderUserId(accessGrant), accessGrant.getAccessToken(), accessGrant.getRefreshToken(), accessGrant.getExpireTime(), getOAuth2ServiceProvider(), getApiAdapter(extractProviderUserId(accessGrant)));
	}
	//��д�����createConnection��������Ϊ��������������ֱ�ӷ��ش����adapter,�����ڹ��췽���У�û�а취����ֱ��Ϊadapter��ֵopenId.
	public Connection<Weixin> createConnection(ConnectionData data) {
		return new OAuth2Connection<Weixin>(data, getOAuth2ServiceProvider(), getApiAdapter(data.getProviderUserId()));
	}

	//openid����Ϊ������ֱ��return null��������token�в���openid,����΢�ŵ�token��openid,���Բ���return null��Ҫreturn token���openid
	protected String extractProviderUserId(AccessGrant accessGrant) {
		if (accessGrant instanceof WeixinAccessGrant) {
			return ((WeixinAccessGrant) accessGrant).getOpenId();
		}
		return null;
	}

	//����serviceProvider
	protected OAuth2ServiceProvider<Weixin> getOAuth2ServiceProvider() {
		return (OAuth2ServiceProvider<Weixin>) getServiceProvider();
	}

	//����weixinAdapter
	protected ApiAdapter<Weixin> getApiAdapter(String providerUserId) {
		return new WeixinAdapter(providerUserId);
	}

}
