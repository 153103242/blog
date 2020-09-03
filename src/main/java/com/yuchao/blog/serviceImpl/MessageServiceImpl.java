package com.yuchao.blog.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuchao.blog.domain.Message;
import com.yuchao.blog.dto.PageBean;
import com.yuchao.blog.repository.MessageRepository;
import com.yuchao.blog.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageRepository messageRepository;
	
	public void saveMessage(Message message) {
		messageRepository.save(message);
	}

	public PageBean getMessagePageBean(Integer currentPage) {
		
		//总记录数
		Integer totalCount = messageRepository.findMessageCount();
		//分页模型
		PageBean pageBean = new PageBean(6, totalCount, currentPage);
		//数据
		List<Message> messageList = messageRepository.findMessageByLimit(pageBean.getStartIndex(),pageBean.getPageSize());
		pageBean.setList(messageList);
		
		return pageBean;
	}


}
