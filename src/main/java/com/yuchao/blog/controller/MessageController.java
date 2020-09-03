package com.yuchao.blog.controller;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuchao.blog.domain.Message;
import com.yuchao.blog.domain.Visitor;
import com.yuchao.blog.dto.PageBean;
import com.yuchao.blog.service.MessageService;

@Controller
public class MessageController {

	@Autowired
	private HttpSession httpSession;
	@Autowired
	private MessageService messageService;
	
	//保存留言
	@RequestMapping("/saveMessage")
	public String saveMessage(Message message) {

		// 获取登录人
		Object visitor = httpSession.getAttribute("visitor");
		if (visitor instanceof Visitor) {
			message.setVisitor((Visitor) visitor);
		}else {
			return "redirect:/gustbook";
		}
		// 设置创建时间
		message.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
		
		messageService.saveMessage(message);
		
		
		return "redirect:/findAllMessage";
	}
	//查找所有留言
	@RequestMapping("/findAllMessage")
	public String findAllMessage(Integer currentPage,Model model) {
		PageBean messagePageBean = messageService.getMessagePageBean(currentPage);
		
		model.addAttribute("messagePageBean", messagePageBean);
		
		return "gustbook.html";
	}
	
}
