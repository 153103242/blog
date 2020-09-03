package com.yuchao.blog.service;

import java.util.List;

import com.yuchao.blog.domain.Ablum;

public interface AblumService {

	void saveAblum(Ablum ablum);

	List<Ablum> findAblum();

	Ablum findAblumById(Long id);

	List<Ablum> findAllAblumOrderByCreateTime();

	List<String> findAblumTimeLineOrderByCreateTime();

	List<Ablum> findAblumListByKeyword(String keyword);

	List<Ablum> findIndexAblum();

}
