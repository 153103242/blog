package com.yuchao.blog.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "blog.security")
public class BlogSecurityProperties {

	//qqµÇÂ¼µÄÅäÖÃ
	private QQProperties qqProperties = new QQProperties();
	//Î¢ÐÅµÇÂ¼µÄÅäÖÃ
	private WeixinProperties weixinProperties = new WeixinProperties();
	
	public QQProperties getQqProperties() {
		return qqProperties;
	}

	public void setQqProperties(QQProperties qqProperties) {
		this.qqProperties = qqProperties;
	}

	public WeixinProperties getWeixinProperties() {
		return weixinProperties;
	}

	public void setWeixinProperties(WeixinProperties weixinProperties) {
		this.weixinProperties = weixinProperties;
	}
	
	
}
