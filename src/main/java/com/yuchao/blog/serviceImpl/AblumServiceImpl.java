package com.yuchao.blog.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuchao.blog.domain.Ablum;
import com.yuchao.blog.repository.AblumRepository;
import com.yuchao.blog.service.AblumService;

@Service
public class AblumServiceImpl implements AblumService {

	@Autowired
	private AblumRepository ablumRepository;
	
	public void saveAblum(Ablum ablum) {
		ablumRepository.save(ablum);
	}

	public List<Ablum> findAblum() {
		return ablumRepository.findAllOrderByCreateTime();
	}

	public Ablum findAblumById(Long id) {
		return ablumRepository.findById(id).get();
	}

	public List<Ablum> findAllAblumOrderByCreateTime() {
		// TODO Auto-generated method stub
		return ablumRepository.findAllAblumOrderByCreateTime();
	}

	public List<String> findAblumTimeLineOrderByCreateTime() {
		return ablumRepository.findAblumTimeLineOrderByCreateTime();
	}

	public List<Ablum> findAblumListByKeyword(String keyword) {
		return ablumRepository.findAblumListByKeyword(keyword);
	}

	public List<Ablum> findIndexAblum() {
		return ablumRepository.findIndexAblum();
	}


}
