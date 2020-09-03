package com.yuchao.blog.serviceImpl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

import com.yuchao.blog.domain.Visitor;
import com.yuchao.blog.repository.VisitorRepository;
@Component
public class SocialVisitorServiceImpl implements SocialUserDetailsService {

	@Autowired
	private VisitorRepository visitorRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private HttpSession httpSession;
	//根据Id查找用户
	public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {

		Visitor visitor = visitorRepository.findById(new Long(userId)).get();
		if (visitor==null) {
			throw new UsernameNotFoundException(userId);
		}
		httpSession.setAttribute("visitor", visitor);
		return new SocialUser(visitor.getUsername(),passwordEncoder.encode(visitor.getPassword()),true,true,true,true,AuthorityUtils.commaSeparatedStringToAuthorityList("VISITOR"));
	}

}
