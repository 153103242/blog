package com.yuchao.blog.service;

import com.yuchao.blog.domain.AblumComment;
import com.yuchao.blog.dto.PageBean;

public interface AblumCommentService {

	void saveAblumComment(AblumComment ablumComment);

	PageBean findAblumCommentPageBean(Integer currentPage, String id);

}
