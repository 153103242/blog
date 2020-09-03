package com.yuchao.blog.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yuchao.blog.domain.Mood;
import com.yuchao.blog.repository.MoodRepository;
import com.yuchao.blog.service.MoodService;
@Component
public class MoodServiceImpl implements MoodService {

	@Autowired
	private MoodRepository  moodRepository;
	
	public void addMood(Mood mood) {
		moodRepository.save(mood);
	}

	public List<Mood> findMood() {
		return moodRepository.findAllOrderByCreateTime();
	}
	public void deleteMood(Long id) {
		moodRepository.deleteById(id);
	}

	public Mood findMoodById(Long moodId) {
		return moodRepository.findById(moodId).get();
	}

	public List<Mood> findIndexMood() {
		return moodRepository.findIndexMood();
	}


	

}
