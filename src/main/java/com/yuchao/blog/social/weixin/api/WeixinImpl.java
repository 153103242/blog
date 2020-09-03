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
	//获取用户信息
	public WeixinUserInfo getUserInfo(String openId) {
		// 拼接参数
		String url = URL_GET_USERINFO + openId;
		// 发送请求
		String response = getRestTemplate().getForObject(url, String.class);
		// 处理返回值
		WeixinUserInfo weixinUserInfo = null;
		try {
			weixinUserInfo = objectMapper.readValue(response, WeixinUserInfo.class);
		} catch (IOException e) {
			throw new RuntimeException("获取用户信息失败!");
		}
		return weixinUserInfo;
	}
	//将accessToken挂在URL上
	
	public WeixinImpl(String accessToken) {
		super(accessToken,TokenStrategy.ACCESS_TOKEN_PARAMETER);
	}
	
	//处理微信乱码问题：微信的返回是UTF-8,而oauth2中的处理方式是ISO8859-1
	protected List<HttpMessageConverter<?>> getMessageConverters() {
		List<HttpMessageConverter<?>> converters = super.getMessageConverters();
		converters.remove(0);
		converters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
		return converters;
	}
	

}
