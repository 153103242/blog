package com.yuchao.blog.serviceImpl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.stereotype.Component;

import com.yuchao.blog.domain.User;
import com.yuchao.blog.domain.Visitor;
import com.yuchao.blog.service.UserService;
import com.yuchao.blog.service.VisitorService;

@Component
public class SecurityUserService  implements UserDetailsService{

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserService userService;
	@Autowired
	private VisitorService visitorService;
	@Autowired
	private HttpSession httpSession;
	//用户名密码登录，调用的函数
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user =userService.findUserByUsername(username);
		Visitor visitor = visitorService.findVisitorByUsername(username);
		if (user!=null) {
			httpSession.setAttribute("visitor", user);
			return new SocialUser(username, passwordEncoder.encode(user.getPassword()),AuthorityUtils.commaSeparatedStringToAuthorityList("ADMIN"));
		}else if (visitor!=null){
			httpSession.setAttribute("visitor", visitor);
			return new SocialUser(username, passwordEncoder.encode(visitor.getPassword()),AuthorityUtils.commaSeparatedStringToAuthorityList("VISITOR"));
		}else {
			throw new UsernameNotFoundException("用户名或者密码错误！");
		}
	}

	
}
