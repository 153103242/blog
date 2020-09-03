package com.yuchao.blog.controller;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuchao.blog.domain.Ablum;
import com.yuchao.blog.domain.AblumComment;
import com.yuchao.blog.domain.Visitor;
import com.yuchao.blog.service.AblumCommentService;
import com.yuchao.blog.service.AblumService;

@Controller
public class AblumCommentController {

	@Autowired
	private AblumService ablumService;
	@Autowired
	private HttpSession session;
	@Autowired
	private AblumCommentService ablumCommentService;
	//添加相册评论
	@RequestMapping("/addAblumComment")
	public String addAblumComment(AblumComment ablumComment,String ablumId) {
		
		Ablum ablum = ablumService.findAblumById(new Long(ablumId));
		Object visitor = session.getAttribute("visitor");
		if (visitor instanceof Visitor) {
			ablumComment.setVisitor((Visitor)visitor);
		}else {
			return "redirect:/ablumDetail?id="+ablumId;
		}
		//封装参数
		ablumComment.setAblum(ablum);
		ablumComment.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
		
		//保存
		ablumCommentService.saveAblumComment(ablumComment);
		
		return "redirect:/ablumDetail?id="+ablumId;
	}
}
