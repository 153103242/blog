package com.yuchao.blog.social.weixin.template;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuchao.blog.social.weixin.connection.WeixinAccessGrant;

public class WeixinOauth2Template extends OAuth2Template {

	private String appId;
	private String appSecret;
	private String accessTokenUrl;

	private ObjectMapper objectMapper = new ObjectMapper();

	private static final String REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token";

	public WeixinOauth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
		super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
		this.appId = clientId;
		this.appSecret = clientSecret;
		this.accessTokenUrl = accessTokenUrl;
	}

	// 用code交换请求
	public AccessGrant exchangeForAccess(String authorizationCode, String redirectUri, MultiValueMap<String, String> additionalParameters) {

		// 获得请求
		StringBuffer accessTokenRequestUrl = new StringBuffer(accessTokenUrl);
		// 拼接参数
		// https://api.weixin.qq.com/sns/oauth2/access_token
		// ?appid=APPID
		accessTokenRequestUrl.append("?appid=" + appId);
		// &secret=SECRET
		accessTokenRequestUrl.append("&secret=" + appSecret);
		// &code=CODE
		accessTokenRequestUrl.append("&code=" + authorizationCode);
		// &grant_type=authorization_code
		accessTokenRequestUrl.append("&grant_type=authorization_code");
		// &redirect_uri=
		accessTokenRequestUrl.append("&redirect_uri=" + redirectUri);
		// 返回令牌
		return getAccessToken(accessTokenRequestUrl);
	}

	// 发送请求，获取令牌
	private AccessGrant getAccessToken(StringBuffer accessTokenRequestUrl) {
		// 发送请求
		String response = getRestTemplate().getForObject(accessTokenRequestUrl.toString(), String.class);
//		{ 
//			"access_token":"ACCESS_TOKEN", 
//			"expires_in":7200, 
//			"refresh_token":"REFRESH_TOKEN",
//			"openid":"OPENID", 
//			"scope":"SCOPE",
//			"unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
//			}
		Map<String, Object> result = null;
		try {
			// 处理返回值
			result = objectMapper.readValue(response, Map.class);
		} catch (IOException e) {
			throw new RuntimeException("获取令牌失败！");
		}
		// 将返回值转换为令牌
		WeixinAccessGrant weixinAccessGrant = new WeixinAccessGrant((String) result.get("access_token"), (String) result.get("scope"), (String) result.get("refresh_token"), new Long((Integer)result.get("expires_in")) );
		// 设置微信令牌的openId
		weixinAccessGrant.setOpenId((String) result.get("openid"));
		return weixinAccessGrant;
	}

	// 处理乱码问题
	protected RestTemplate getRestTemplate() {
		RestTemplate template = super.getRestTemplate();
		template.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));

		return template;
	}

	// 令牌失效后的处理
	public AccessGrant refreshAccess(String refreshToken, String scope, MultiValueMap<String, String> additionalParameters) {
		// 获得请求
		StringBuffer refreshTokenUrl = new StringBuffer(REFRESH_TOKEN_URL);
		// 拼接参数
		// https://api.weixin.qq.com/sns/oauth2/refresh_token
		// ?appid=APPID
		refreshTokenUrl.append("?appid="+appId);
		// &grant_type=refresh_token
		refreshTokenUrl.append("&grant_type=refresh_token");
		// &refresh_token=REFRESH_TOKEN
		refreshTokenUrl.append("&refresh_token="+refreshToken);
		
		return getAccessToken(refreshTokenUrl);

	}

	public String buildAuthorizeUrl(OAuth2Parameters parameters) {
		return buildAuthenticateUrl(parameters);
	}

	public String buildAuthenticateUrl(OAuth2Parameters parameters) {
		String url = super.buildAuthenticateUrl(parameters);
		url=url+"&appid="+appId+"&scope=snsapi_login";
		return url;
	}

}
