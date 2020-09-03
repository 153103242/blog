package com.yuchao.blog.serviceImpl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuchao.blog.domain.User;
import com.yuchao.blog.repository.UserRepository;
import com.yuchao.blog.service.UserService;

@Component
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public void modifyUsername(String username) {
		userRepository.modifyUsername(username);
	}

	public User getOwner() {
		return userRepository.findById(1L).get();
	}

	@Transactional
	public void modifyPassword(String password) {
		userRepository.modifyPassword(password);
	}
	@Transactional
	public void modifyPersonalSign(String personalSign) {
		userRepository.modifyPersonalSign(personalSign);
	}

	@Transactional
	public void modifyHeadImage(String headImage) {
		userRepository.modifyHeadImage(headImage);
	}

	public User findUserByUsername(String username) {
		return userRepository.findUserByUsername(username);
	}

}
