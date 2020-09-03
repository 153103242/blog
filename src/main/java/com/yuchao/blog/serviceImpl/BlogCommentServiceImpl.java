package com.yuchao.blog.serviceImpl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuchao.blog.domain.BlogComment;
import com.yuchao.blog.dto.PageBean;
import com.yuchao.blog.repository.BlogCommentRepository;
import com.yuchao.blog.service.BlogCommentService;

@Service
public class BlogCommentServiceImpl implements BlogCommentService {

	@Autowired
	private BlogCommentRepository blogCommentRepository;

	public void saveBlogComment(BlogComment blogComment) {
		blogCommentRepository.save(blogComment);
	}

	public PageBean findBlogCommentByBlogIdAndCurrentPage(Long id, Integer currentPage) {
		// ��ü�¼��
		Integer totalCount = blogCommentRepository.findBlogCommentCountByBlogId(id);
//		 ��÷�ҳģ��
		PageBean pageBean = new PageBean(6, totalCount, currentPage);
//		 ��÷�ҳģ�͵�����
		List<BlogComment> blogCommentList = blogCommentRepository.getBlogCommentPageBean(id, pageBean.getStartIndex(), pageBean.getPageSize());
		// ���÷�ҳģ�͵�����
		pageBean.setList(blogCommentList);
		
		return pageBean;
	}

}
