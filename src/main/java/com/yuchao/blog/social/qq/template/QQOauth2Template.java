package com.yuchao.blog.social.qq.template;

import java.nio.charset.Charset;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

public class QQOauth2Template extends OAuth2Template {

	public QQOauth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
		super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
		//自动拼接appId和appSecret
		setUseParametersForClientAuthentication(true);
	}

	//添加restTemplate的处理方式 text/html
	protected RestTemplate createRestTemplate() {
		RestTemplate template = super.createRestTemplate();
		template.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
		
		return template;
	}

	@Override
	protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
		//拿到这个请求的结果字符串
		String responseStr = getRestTemplate().postForObject(accessTokenUrl,parameters, String.class);
		//access_token=FE04************************CCE2 items[0]
		//expires_in=7776000&refresh_token=88E4************************BE14 items[1]
		
		//expires_in=7776000 item[0]
		//refresh_token=88E4************************BE14 item[1]
		//处理字符串
		String[] items = StringUtils.split(responseStr,"&");
		String[] item = StringUtils.split(items[1], "&");
		
		String access_token = StringUtils.replace(items[0], "access_token=", "");
		Long expires_in = new Long(StringUtils.replace(item[0], "expires_in=", ""));
		String refresh_token = StringUtils.replace(item[1], "refresh_token=", "");
		
		return new AccessGrant(access_token, null, refresh_token, expires_in);
	}
	
	//自己处理请求
	
	
}
