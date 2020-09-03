package com.yuchao.blog.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuchao.blog.domain.AblumComment;
import com.yuchao.blog.dto.PageBean;
import com.yuchao.blog.repository.AblumCommentRepository;
import com.yuchao.blog.service.AblumCommentService;

@Service
public class AblumCommentServiceImpl implements AblumCommentService {

	@Autowired
	private AblumCommentRepository ablumCommentRepository;

	public void saveAblumComment(AblumComment ablumComment) {
		ablumCommentRepository.save(ablumComment);
	}

	public PageBean findAblumCommentPageBean(Integer currentPage, String id) {
		
		Integer totalCount = ablumCommentRepository.findAblumCommentCountByAblumId(id);
		PageBean pageBean = new PageBean(6, totalCount, currentPage);
		List<AblumComment> list = ablumCommentRepository.findAblumCommentListByLimit(id,pageBean.getStartIndex(),pageBean.getPageSize());
		pageBean.setList(list);
		return pageBean;
	}

}
