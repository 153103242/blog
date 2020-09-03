package com.yuchao.blog.service;

import com.yuchao.blog.domain.User;

public interface UserService {

	void modifyUsername(String username);

	User getOwner();

	void modifyPassword(String password);

	void modifyPersonalSign(String personalSign);

	void modifyHeadImage(String headImage);

	User findUserByUsername(String username);

}
