package com.yuchao.blog.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

//����
@Configuration
//��properties������Ч
@EnableConfigurationProperties(BlogSecurityProperties.class)
public class BlogSecurityConfig {

}
