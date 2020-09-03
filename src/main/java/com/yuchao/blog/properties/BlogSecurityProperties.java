package com.yuchao.blog.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "blog.security")
public class BlogSecurityProperties {

	//qq��¼������
	private QQProperties qqProperties = new QQProperties();
	//΢�ŵ�¼������
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
