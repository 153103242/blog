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
	
	// ��ת����ҳ
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
	
	// ���Ҳ��ͺ���������
	@RequestMapping("/findAllArchives")
	public String findAllArchives(Model model) {

		// ����
		List<Blog> blogList = blogService.findAllBlogOrderByCreateTime();
		// ���
		List<Ablum> ablumList = ablumService.findAllAblumOrderByCreateTime();

		// ���ʱ����
		List<String> blogTimeLine = blogService.findBlogTimeLineOrderByCreateTime();
		List<String> ablumTimeLine = ablumService.findAblumTimeLineOrderByCreateTime();
		// yyyy-MM-dd HH:mm:ss �� yyyy-MM-dd
		preList(blogList, ablumList);
		preTimeLine(blogTimeLine);
		preTimeLine(ablumTimeLine);
		// ������ʱ����
		List<String> timeLineList = getTimeLine(blogTimeLine, ablumTimeLine);
		List<TimeLineObject> timeLineObjectList = getTimeLineObject(blogList, ablumList);

		model.addAttribute("timeLine", timeLineList);
		model.addAttribute("timeLineObjectList", timeLineObjectList);

		return "/archives";
	}

	// ����������Ĳ��ͺ���ᣬ��Ϊһ������TimeLineObject
	private List<TimeLineObject> getTimeLineObject(List<Blog> blogList, List<Ablum> ablumList) {
		List<TimeLineObject> target = new ArrayList<TimeLineObject>();
		// ����ָ��
		int i = 0;
		// ���ָ��
		int j = 0;
		// Ŀ��ָ��
		int k = 0;
		while (i<blogList.size() || j<ablumList.size()) {
			//�߽��ж�
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
				//���������ʱ�����,����ӽ�target��
				k=addObjectAndJudgeK(target,ablumList.get(j),k);
				k=addObjectAndJudgeK(target,blogList.get(i),k);
				i++;
				j++;
			
			}else if (blogList.get(i).getCreateTime().compareTo(ablumList.get(j).getCreateTime())>0) {
				//���ʹ������
				k=addObjectAndJudgeK(target,blogList.get(i),k);
				i++;
			}else {
				//�����ڲ���
				k=addObjectAndJudgeK(target,ablumList.get(j),k);
				j++;
			}
		}
		return target;
	}

	//���blog����target��,������k
	private int addObjectAndJudgeK(List<TimeLineObject> target, Blog blog, int k) {
		
		TimeLineObject timeLineObject= new TimeLineObject();
		timeLineObject.setTitle("��������"+blog.getTitle());
		timeLineObject.setCreateTime(blog.getCreateTime());
		timeLineObject.setHref("/blogDetail?id="+blog.getId());
		timeLineObject.setNumber("���������"+blog.getViewNumber());
		
		target.add(timeLineObject);
		k++;
		
		return k;
	}
	//���ablum����target��,������k
	private int addObjectAndJudgeK(List<TimeLineObject> target, Ablum ablum, int k) {
		
		TimeLineObject timeLineObject= new TimeLineObject();
		timeLineObject.setTitle("�������"+ablum.getTitle());
		timeLineObject.setCreateTime(ablum.getCreateTime());
		timeLineObject.setHref("/ablumDetail?id="+ablum.getId());
		timeLineObject.setNumber("��Ƭ������"+ablum.getImageNumber());
		
		target.add(timeLineObject);
		k++;
		
		return k;
	}

	// �ϲ�ʱ����
	private List<String> getTimeLine(List<String> blogTimeLine, List<String> ablumTimeLine) {
		// �ϲ����ʱ����
		List<String> timeLine = new ArrayList<String>();
		// ����ָ��
		int i = 0;
		// ���ָ��
		int j = 0;
		// Ŀ��ʱ����ָ��
		int k = 0;

		while (i < blogTimeLine.size() || j < ablumTimeLine.size()) {
			// ����߽�
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

			// ���ͺ������ͬʱ���������
			if (blogTimeLine.get(i).equals(ablumTimeLine.get(j))) {
				k = addStringAndJudgeK(timeLine, blogTimeLine.get(i), k);
				i++;
				j++;
				// ����>���
			} else if (blogTimeLine.get(i).compareTo(ablumTimeLine.get(j)) > 0) {
				k = addStringAndJudgeK(timeLine, blogTimeLine.get(i), k);
				i++;
				// �����ڲ���
			} else {
				k = addStringAndJudgeK(timeLine, ablumTimeLine.get(j), k);
				j++;
			}
		}
		return timeLine;
	}

	// ����ϲ�ʱ���ߵ��ظ�����
	private int addStringAndJudgeK(List<String> timeLine, String addString, int k) {
		// �ж��Ƿ��ǵ�һ����ӵ�
		if (k == 0) {
			timeLine.add(addString);
			k++;
			return k;
		}
		// �ж���ӵ������Ƿ���֮ǰ���ظ�
		if (addString.equals(timeLine.get(k - 1))) {
			return k;
		} else {
			// ������ظ�
			timeLine.add(addString);
			k++;
			return k;
		}
	}

	// Ԥ��������
	public void preList(List<Blog> blogList, List<Ablum> ablumList) {
		// yyyy-MM-dd HH:mm:ss �� yyyy-MM-dd
		for (Ablum ablum : ablumList) {
			ablum.setCreateTime(ablum.getCreateTime().substring(0, 10));
		}
		for (Blog blog : blogList) {
			blog.setCreateTime(blog.getCreateTime().substring(0, 10));
		}
	}

	// Ԥ����ʱ����
	public void preTimeLine(List<String> timeLine) {
		// yyyy-MM-dd HH:mm:ss �� yyyy-MM
		for (int i = 0; i < timeLine.size(); i++) {
			timeLine.set(i, timeLine.get(i).substring(0, 7));
		}
	}

}
