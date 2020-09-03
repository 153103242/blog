package com.yuchao.blog.social.qq.api;

import java.io.IOException;

import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {

	private static final String URL_GET_USERINFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";
	private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

	// 外界赋值,在QQProperties
	private String appId;
	// 用户的唯一标识,URL
	private String openId;

	// 处理json数据
	private ObjectMapper objectMapper = new ObjectMapper();

	public QQUserInfo getUserInfo() {

		// 拼接参数
		String url = String.format(URL_GET_USERINFO, appId, openId);
		// 发送请求
		String result = getRestTemplate().getForObject(url, String.class);
		// 处理返回值
		QQUserInfo userInfo = null;
		try {
			userInfo = objectMapper.readValue(result, QQUserInfo.class);
			userInfo.setOpenId(openId);
		} catch (IOException e) {
			throw new RuntimeException("用户信息获取失败！");
		}
		return userInfo;
	}

	// 初始化access_token和appId,openId是通过url获取的
	public QQImpl(String access_token, String appId) {
		// 自动拼接access_token
		super(access_token, TokenStrategy.ACCESS_TOKEN_PARAMETER);
		// 给appId赋值
		this.appId = appId;
		// 通过url获得openId
		// 拼接url的参数
		String url = String.format(URL_GET_OPENID, access_token);
		// 发送请求
		String result = getRestTemplate().getForObject(url, String.class);
		// callback( {"client_id":"YOUR_APPID","openid":"YOUR_OPENID"} );
		// 处理返回值
		result = StringUtils.replace(result, "callback( ", "");
		result = StringUtils.replace(result, " )", "");
		OpenId id=null;
		try {
			id = objectMapper.readValue(result, OpenId.class);
		} catch (IOException e) {
			throw new RuntimeException("获取openId失败");
		}
		// 赋值openId
		this.openId=id.getOpenid();
	}
}
