package com.yuchao.blog.social.qq.connection;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

import com.yuchao.blog.social.qq.api.QQ;
import com.yuchao.blog.social.qq.api.QQUserInfo;


//qq信息适配器
public class QQAdapter implements ApiAdapter<QQ> {

	public boolean test(QQ api) {
		return true;
	}

	//完成适配
	public void setConnectionValues(QQ api, ConnectionValues values) {
		//获取QQ信息
		QQUserInfo userInfo = api.getUserInfo();
		//获取名称
		values.setDisplayName(userInfo.getNickname());
		//获取头像
		values.setImageUrl(userInfo.getFigureurl_qq_1());
		//获取个人主页
		values.setProfileUrl(null);
		//获取用户唯一ID
		values.setProviderUserId(userInfo.getOpenId());
	}

	public UserProfile fetchUserProfile(QQ api) {
		return null;
	}

	public void updateStatus(QQ api, String message) {
		
	}

}
