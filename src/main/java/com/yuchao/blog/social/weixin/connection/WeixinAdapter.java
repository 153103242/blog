package com.yuchao.blog.social.weixin.connection;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

import com.yuchao.blog.social.weixin.api.Weixin;
import com.yuchao.blog.social.weixin.api.WeixinUserInfo;

public class WeixinAdapter implements ApiAdapter<Weixin> {

	private String openId;
	
	
	
	protected WeixinAdapter(String openId) {
		this.openId = openId;
	}

	
	
	protected WeixinAdapter() {
	}



	public boolean test(Weixin api) {
		// TODO Auto-generated method stub
		return true;
	}

	public void setConnectionValues(Weixin api, ConnectionValues values) {
		WeixinUserInfo info = api.getUserInfo(openId);
		values.setDisplayName(info.getNickname());
		values.setImageUrl(info.getHeadimgurl());
		values.setProfileUrl(null);
		values.setProviderUserId(info.getOpenid());
	}

	@Override
	public UserProfile fetchUserProfile(Weixin api) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateStatus(Weixin api, String message) {
		// TODO Auto-generated method stub
		
	}

	
	
}
