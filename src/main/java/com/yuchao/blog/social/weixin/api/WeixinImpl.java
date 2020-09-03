package com.yuchao.blog.social.weixin.api;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import com.fasterxml.jackson.databind.ObjectMapper;

public class WeixinImpl extends AbstractOAuth2ApiBinding implements Weixin {

	private static final String URL_GET_USERINFO="https://api.weixin.qq.com/sns/userinfo?openid=";
	
	private ObjectMapper objectMapper = new ObjectMapper();
	//��ȡ�û���Ϣ
	public WeixinUserInfo getUserInfo(String openId) {
		// ƴ�Ӳ���
		String url = URL_GET_USERINFO + openId;
		// ��������
		String response = getRestTemplate().getForObject(url, String.class);
		// ������ֵ
		WeixinUserInfo weixinUserInfo = null;
		try {
			weixinUserInfo = objectMapper.readValue(response, WeixinUserInfo.class);
		} catch (IOException e) {
			throw new RuntimeException("��ȡ�û���Ϣʧ��!");
		}
		return weixinUserInfo;
	}
	//��accessToken����URL��
	
	public WeixinImpl(String accessToken) {
		super(accessToken,TokenStrategy.ACCESS_TOKEN_PARAMETER);
	}
	
	//����΢���������⣺΢�ŵķ�����UTF-8,��oauth2�еĴ���ʽ��ISO8859-1
	protected List<HttpMessageConverter<?>> getMessageConverters() {
		List<HttpMessageConverter<?>> converters = super.getMessageConverters();
		converters.remove(0);
		converters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
		return converters;
	}
	

}
