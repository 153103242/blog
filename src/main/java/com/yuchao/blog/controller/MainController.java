package com.yuchao.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	// ת������ҳ��controller
	@RequestMapping("/")
	public String root() {
		return "redirect:/index";
	}

	// ��ת������
	@RequestMapping("/blog")
	public String blog() {
		return "blog.html";
	}

	// ��ת�����
	@RequestMapping("/ablum")
	public String ablum() {
		return "ablum.html";
	}

	// ��ת������
	@RequestMapping("/link")
	public String link() {
		return "link.html";
	}

	// ��ת���鵵
	@RequestMapping("/archives")
	public String archives() {
		return "archives.html";
	}

	// ��ת������
	@RequestMapping("/gustbook")
	public String gustbook() {
		return "gustbook.html";
	}

	// ��ת���û���Ϣ
	@RequestMapping("/user")
	public String user() {
		return "user.html";
	}

	// ��ת����Ӳ�������
	@RequestMapping("/toAddBlog")
	public String toAddBlog() {
		return "add-blog.html";
	}
	//��ת��������
	@RequestMapping("/toAddAblum")
	public String toAddAblum() {
		return "add-ablum.html";
	}
	//��ת�������ߵ�¼
	@RequestMapping("/visitorLogin")
	public String visitoryLogin() {
		return "login.html";
	}
	//��¼�ɹ�֮�����ת
	@RequestMapping("/signin")
	public String signin() {
		return "redirect:/index";
	}
	//��ת��ע�����
	@RequestMapping("/visitorRegister")
	public String visitorRegister() {
		return "register.html";
	}
}
