package com.yuchao.blog.controller;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuchao.blog.domain.Blog;
import com.yuchao.blog.domain.BlogComment;
import com.yuchao.blog.domain.Visitor;
import com.yuchao.blog.service.BlogCommentService;
import com.yuchao.blog.service.BlogService;

@Controller
public class BlogCommentController {

	@Autowired
	private BlogService blogService;
	@Autowired
	private HttpSession session;
	@Autowired
	private BlogCommentService blogCommentService;
	// ��Ӳ�������
	@RequestMapping("/addBlogComment")
	public String addBlogComment(BlogComment blogComment,String blogId) {
		Object visitor = session.getAttribute("visitor");
		Blog blog = blogService.findBlogById(new Long(blogId));
		//��װ����
		if (visitor instanceof Visitor) {
			blogComment.setVisitor((Visitor) visitor);
		}else {
			return "redirect:/blogDetail?id="+blogId;
		}
		
		blogComment.setBlog(blog);
		blogComment.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
		//ά�����͵Ļظ����Ĺ�ϵ
		blog.setCommentNumber(blog.getCommentNumber()+1);
		blogCommentService.saveBlogComment(blogComment);
		
		return "redirect:/blogDetail?id="+blogId;
	}
}
