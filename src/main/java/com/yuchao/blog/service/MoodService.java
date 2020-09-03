package com.yuchao.blog.service;

import java.util.List;

import com.yuchao.blog.domain.Mood;

public interface MoodService {

	void addMood(Mood mood);

	List<Mood> findMood();

	void deleteMood(Long id);

	Mood findMoodById(Long moodId);

	List<Mood> findIndexMood();

}
