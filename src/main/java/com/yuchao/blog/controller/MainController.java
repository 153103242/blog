package com.yuchao.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	// 转发到首页的controller
	@RequestMapping("/")
	public String root() {
		return "redirect:/index";
	}

	// 跳转到博客
	@RequestMapping("/blog")
	public String blog() {
		return "blog.html";
	}

	// 跳转到相册
	@RequestMapping("/ablum")
	public String ablum() {
		return "ablum.html";
	}

	// 跳转到连接
	@RequestMapping("/link")
	public String link() {
		return "link.html";
	}

	// 跳转到归档
	@RequestMapping("/archives")
	public String archives() {
		return "archives.html";
	}

	// 跳转到留言
	@RequestMapping("/gustbook")
	public String gustbook() {
		return "gustbook.html";
	}

	// 跳转到用户信息
	@RequestMapping("/user")
	public String user() {
		return "user.html";
	}

	// 跳转到添加博客详情
	@RequestMapping("/toAddBlog")
	public String toAddBlog() {
		return "add-blog.html";
	}
	//跳转到添加相册
	@RequestMapping("/toAddAblum")
	public String toAddAblum() {
		return "add-ablum.html";
	}
	//跳转到访问者登录
	@RequestMapping("/visitorLogin")
	public String visitoryLogin() {
		return "login.html";
	}
	//登录成功之后的跳转
	@RequestMapping("/signin")
	public String signin() {
		return "redirect:/index";
	}
	//跳转到注册界面
	@RequestMapping("/visitorRegister")
	public String visitorRegister() {
		return "register.html";
	}
}
