package com.yuchao.blog.service;

import com.yuchao.blog.domain.BlogComment;
import com.yuchao.blog.dto.PageBean;

public interface BlogCommentService {

	void saveBlogComment(BlogComment blogComment);

	PageBean findBlogCommentByBlogIdAndCurrentPage(Long id, Integer currentPage);

}
