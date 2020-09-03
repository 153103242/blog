package com.yuchao.blog.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yuchao.blog.domain.Mood;
import com.yuchao.blog.domain.User;
import com.yuchao.blog.service.MoodService;
import com.yuchao.blog.utils.YuchaoUtils;

@Controller
public class MoodController {

	@Autowired
	private MoodService moodService;
	
	//���˵˵
	@RequestMapping("/addMood")
	public String addMood(Mood mood,ServletRequest request) {
		//����ʱ��
		String createTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
		mood.setCreateTime(createTime);
		//ά����ϵ���������
		User owner = (User) request.getAttribute("owner");
		mood.setUser(owner);
		
		moodService.addMood(mood);
		
		return "redirect:/findMood";
	}
	//�ϴ�ͼƬ��������
	@RequestMapping("/uploadMoodImage")
	@ResponseBody
	public String uploadMoodImage(MultipartFile file) throws IllegalStateException, IOException {
		
		String afterClassPathUrl = "static/upload/moodImage";
		String fileName = YuchaoUtils.saveImage2ServerReturnString(file, afterClassPathUrl);
		
		return fileName;
	}
	//��������Mood
	@RequestMapping("/findMood")
	public String findMood(Model model) {
		List<Mood> moodList= moodService.findMood();
		model.addAttribute("moodList", moodList);
		
		return "mood.html";
	}
	//ɾ��Mood
	@RequestMapping("/deleteMood")
	public String deleteMood(String deleteId) {
		Long id =new Long(deleteId);
		moodService.deleteMood(id);
		
		return "redirect:/findMood";
	}
}
