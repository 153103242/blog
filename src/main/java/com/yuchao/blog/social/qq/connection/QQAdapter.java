package com.yuchao.blog.social.qq.connection;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

import com.yuchao.blog.social.qq.api.QQ;
import com.yuchao.blog.social.qq.api.QQUserInfo;


//qq��Ϣ������
public class QQAdapter implements ApiAdapter<QQ> {

	public boolean test(QQ api) {
		return true;
	}

	//�������
	public void setConnectionValues(QQ api, ConnectionValues values) {
		//��ȡQQ��Ϣ
		QQUserInfo userInfo = api.getUserInfo();
		//��ȡ����
		values.setDisplayName(userInfo.getNickname());
		//��ȡͷ��
		values.setImageUrl(userInfo.getFigureurl_qq_1());
		//��ȡ������ҳ
		values.setProfileUrl(null);
		//��ȡ�û�ΨһID
		values.setProviderUserId(userInfo.getOpenId());
	}

	public UserProfile fetchUserProfile(QQ api) {
		return null;
	}

	public void updateStatus(QQ api, String message) {
		
	}

}
