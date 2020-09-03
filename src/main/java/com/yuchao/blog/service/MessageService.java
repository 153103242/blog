package com.yuchao.blog.service;

import com.yuchao.blog.domain.Message;
import com.yuchao.blog.dto.PageBean;

public interface MessageService {

	void saveMessage(Message message);

	PageBean getMessagePageBean(Integer currentPage);

}
