package com.yuchao.blog.social.qq.api;

import java.io.IOException;

import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

	private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";
	private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

	// ��縳ֵ,��QQProperties
	private String appId;
	// �û���Ψһ��ʶ,URL
	private String openId;

	// ����json����
	private ObjectMapper objectMapper = new ObjectMapper();

	public QQUserInfo getUserInfo() {

		// ƴ�Ӳ���
		String url = String.format(URL_GET_USERINFO, appId, openId);
		// ��������
		String result = getRestTemplate().getForObject(url, String.class);
		// ������ֵ
		QQUserInfo userInfo = null;
		try {
			userInfo = objectMapper.readValue(result, QQUserInfo.class);
			userInfo.setOpenId(openId);
		} catch (IOException e) {
			throw new RuntimeException("�û���Ϣ��ȡʧ�ܣ�");
		}
		return userInfo;
	}

	// ��ʼ��access_token��appId,openId��ͨ��url��ȡ��
	public QQImpl(String access_token, String appId) {
		// �Զ�ƴ��access_token
		super(access_token, TokenStrategy.ACCESS_TOKEN_PARAMETER);
		// ��appId��ֵ
		this.appId = appId;
		// ͨ��url���openId
		// ƴ��url�Ĳ���
		String url = String.format(URL_GET_OPENID, access_token);
		// ��������
		String result = getRestTemplate().getForObject(url, String.class);
		// callback( {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"} );
		// ������ֵ
		result = StringUtils.replace(result, "callback( ", "");
		result = StringUtils.replace(result, " )", "");
		OpenId id=null;
		try {
			id = objectMapper.readValue(result, OpenId.class);
		} catch (IOException e) {
			throw new RuntimeException("��ȡopenIdʧ��");
		}
		// ��ֵopenId
		this.openId=id.getOpenid();
	}
}
