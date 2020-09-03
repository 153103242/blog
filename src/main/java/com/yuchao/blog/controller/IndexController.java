package com.yuchao.blog.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuchao.blog.domain.Ablum;
import com.yuchao.blog.domain.Blog;
import com.yuchao.blog.domain.Mood;
import com.yuchao.blog.dto.TimeLineObject;
import com.yuchao.blog.service.AblumService;
import com.yuchao.blog.service.BlogService;
import com.yuchao.blog.service.MoodService;

@Controller
public class IndexController {

	@Autowired
	private BlogService blogService;
	@Autowired
	private AblumService ablumService;
	@Autowired
	private MoodService moodService;
	
	// 跳转到首页
	@RequestMapping("/index")
	public String index(Model model) {
		
		List<Mood> moodList = moodService.findIndexMood();
		List<Ablum> ablumList = ablumService.findIndexAblum();
		List<Blog> blogList = blogService.findIndexBlog();
		
		model.addAttribute("moodList", moodList);
		model.addAttribute("ablumList", ablumList);
		model.addAttribute("blogList", blogList);
		
		return "index.html";
	}
	
	// 查找博客和相册的数据
	@RequestMapping("/findAllArchives")
	public String findAllArchives(Model model) {

		// 博客
		List<Blog> blogList = blogService.findAllBlogOrderByCreateTime();
		// 相册
		List<Ablum> ablumList = ablumService.findAllAblumOrderByCreateTime();

		// 获得时间线
		List<String> blogTimeLine = blogService.findBlogTimeLineOrderByCreateTime();
		List<String> ablumTimeLine = ablumService.findAblumTimeLineOrderByCreateTime();
		// yyyy-MM-dd HH:mm:ss → yyyy-MM-dd
		preList(blogList, ablumList);
		preTimeLine(blogTimeLine);
		preTimeLine(ablumTimeLine);
		// 处理后的时间线
		List<String> timeLineList = getTimeLine(blogTimeLine, ablumTimeLine);
		List<TimeLineObject> timeLineObjectList = getTimeLineObject(blogList, ablumList);

		model.addAttribute("timeLine", timeLineList);
		model.addAttribute("timeLineObjectList", timeLineObjectList);

		return "/archives";
	}

	// 将两个有序的博客和相册，变为一个有序TimeLineObject
	private List<TimeLineObject> getTimeLineObject(List<Blog> blogList, List<Ablum> ablumList) {
		List<TimeLineObject> target = new ArrayList<TimeLineObject>();
		// 博客指针
		int i = 0;
		// 相册指针
		int j = 0;
		// 目标指针
		int k = 0;
		while (i<blogList.size() || j<ablumList.size()) {
			//边界判断
			if (i>=blogList.size()) {
				k=addObjectAndJudgeK(target,ablumList.get(j),k);
				j++;
				continue;
			}
			if (j>=ablumList.size()) {
				k=addObjectAndJudgeK(target,blogList.get(i),k);
				i++;
				continue;
			}
			if (blogList.get(i).getCreateTime().equals(ablumList.get(j).getCreateTime())) {
				//博客与相册时间相等,都添加进target中
				k=addObjectAndJudgeK(target,ablumList.get(j),k);
				k=addObjectAndJudgeK(target,blogList.get(i),k);
				i++;
				j++;
			
			}else if (blogList.get(i).getCreateTime().compareTo(ablumList.get(j).getCreateTime())>0) {
				//博客大于相册
				k=addObjectAndJudgeK(target,blogList.get(i),k);
				i++;
			}else {
				//相册大于博客
				k=addObjectAndJudgeK(target,ablumList.get(j),k);
				j++;
			}
		}
		return target;
	}

	//添加blog对象到target中,并增加k
	private int addObjectAndJudgeK(List<TimeLineObject> target, Blog blog, int k) {
		
		TimeLineObject timeLineObject= new TimeLineObject();
		timeLineObject.setTitle("博客名："+blog.getTitle());
		timeLineObject.setCreateTime(blog.getCreateTime());
		timeLineObject.setHref("/blogDetail?id="+blog.getId());
		timeLineObject.setNumber("浏览数量："+blog.getViewNumber());
		
		target.add(timeLineObject);
		k++;
		
		return k;
	}
	//添加ablum对象到target中,并增加k
	private int addObjectAndJudgeK(List<TimeLineObject> target, Ablum ablum, int k) {
		
		TimeLineObject timeLineObject= new TimeLineObject();
		timeLineObject.setTitle("相册名："+ablum.getTitle());
		timeLineObject.setCreateTime(ablum.getCreateTime());
		timeLineObject.setHref("/ablumDetail?id="+ablum.getId());
		timeLineObject.setNumber("照片数量："+ablum.getImageNumber());
		
		target.add(timeLineObject);
		k++;
		
		return k;
	}

	// 合并时间线
	private List<String> getTimeLine(List<String> blogTimeLine, List<String> ablumTimeLine) {
		// 合并后的时间线
		List<String> timeLine = new ArrayList<String>();
		// 博客指针
		int i = 0;
		// 相册指针
		int j = 0;
		// 目标时间线指针
		int k = 0;

		while (i < blogTimeLine.size() || j < ablumTimeLine.size()) {
			// 处理边界
			if (i >= blogTimeLine.size()) {
				k = addStringAndJudgeK(timeLine, ablumTimeLine.get(j), k);
				j++;
				continue;
			}
			if (j >= ablumTimeLine.size()) {
				k = addStringAndJudgeK(timeLine, blogTimeLine.get(i), k);
				i++;
				continue;
			}

			// 博客和相册相同时，博客添加
			if (blogTimeLine.get(i).equals(ablumTimeLine.get(j))) {
				k = addStringAndJudgeK(timeLine, blogTimeLine.get(i), k);
				i++;
				j++;
				// 博客>相册
			} else if (blogTimeLine.get(i).compareTo(ablumTimeLine.get(j)) > 0) {
				k = addStringAndJudgeK(timeLine, blogTimeLine.get(i), k);
				i++;
				// 相册大于博客
			} else {
				k = addStringAndJudgeK(timeLine, ablumTimeLine.get(j), k);
				j++;
			}
		}
		return timeLine;
	}

	// 处理合并时间线的重复问题
	private int addStringAndJudgeK(List<String> timeLine, String addString, int k) {
		// 判断是否是第一个添加的
		if (k == 0) {
			timeLine.add(addString);
			k++;
			return k;
		}
		// 判断添加的数据是否与之前的重复
		if (addString.equals(timeLine.get(k - 1))) {
			return k;
		} else {
			// 如果不重复
			timeLine.add(addString);
			k++;
			return k;
		}
	}

	// 预处理数据
	public void preList(List<Blog> blogList, List<Ablum> ablumList) {
		// yyyy-MM-dd HH:mm:ss → yyyy-MM-dd
		for (Ablum ablum : ablumList) {
			ablum.setCreateTime(ablum.getCreateTime().substring(0, 10));
		}
		for (Blog blog : blogList) {
			blog.setCreateTime(blog.getCreateTime().substring(0, 10));
		}
	}

	// 预处理时间线
	public void preTimeLine(List<String> timeLine) {
		// yyyy-MM-dd HH:mm:ss → yyyy-MM
		for (int i = 0; i < timeLine.size(); i++) {
			timeLine.set(i, timeLine.get(i).substring(0, 7));
		}
	}

}
