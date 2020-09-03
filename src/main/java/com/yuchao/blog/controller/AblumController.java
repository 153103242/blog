package com.yuchao.blog.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yuchao.blog.domain.Ablum;
import com.yuchao.blog.domain.Blog;
import com.yuchao.blog.domain.Image;
import com.yuchao.blog.dto.PageBean;
import com.yuchao.blog.service.AblumCommentService;
import com.yuchao.blog.service.AblumService;
import com.yuchao.blog.service.ImageService;
import com.yuchao.blog.utils.YuchaoUtils;

@Controller
public class AblumController {

	
	@Autowired
	private AblumService ablumService;
	@Autowired
	private ImageService imageService;
	@Autowired
	private AblumCommentService ablumCommentService;
	//�ϴ�����
	@RequestMapping("/uploadAblumCover")
	@ResponseBody
	public String uploadAblumCover(MultipartFile file) throws IllegalStateException, IOException {
		
		String afterClassPathUrl = "static/upload/ablumCover";
		String fileName = YuchaoUtils.saveImage2ServerReturnString(file, afterClassPathUrl);
		
		return fileName;
	}
	//ת����Ԥ��ҳ��
	@RequestMapping("/previewAblum")
	public String previewAblum(Ablum ablum,Model model)  {
		//����ʱ��
		ablum.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
		
		model.addAttribute("ablum",ablum);
		return "preview-ablum.html";
	}
	//������
	@RequestMapping("/addAblum")
	public String addAblum(Ablum ablum) {
		ablum.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
		ablum.setImageNumber(0L);
		ablumService.saveAblum(ablum);
		
		return "redirect:/findAblum";
		
	}
	//�����������
	@RequestMapping("/findAblum")
	public String findAblum(Model model) {
		
		List<Ablum> ablumList = ablumService.findAblum();
		model.addAttribute("ablumList", ablumList);
		
		return "/ablum";
	}
	//��ѡ����ᵯ����
	@RequestMapping("/openSelectAblum")
	public String openSelectAblum(Model model) {
		
		List<Ablum> ablumList = ablumService.findAblum();
		model.addAttribute("ablumList", ablumList);
		
		return "select-ablum.html";
	}
	//�ϴ����ͼƬ
	@RequestMapping("/uploadAblumImage")
	@ResponseBody
	public String uploadAblumImage(MultipartFile[] files,String ablumId) throws IllegalStateException, IOException {
		
		//������
		Ablum ablum = ablumService.findAblumById(new Long(ablumId));
		//���ͼƬ����������
		String afterClassPathUrl = "static/upload/ablum"+ablumId;
		for (MultipartFile file : files) {
			String fileName = YuchaoUtils.saveImage2ServerReturnString(file, afterClassPathUrl);
			//ά����ϵ
			Image image = new Image(null, fileName, ablum);
			imageService.saveImage(image);
		}
		
		return "success";
	}
	//������ᵽ����ҳ��
	@RequestMapping("/ablumDetail")
	public String ablumDetail(String id,Model model,Integer currentPage) {
		
		Ablum ablum = ablumService.findAblumById(new Long(id));
		Ablum preAblum =null;
		Ablum sufAblum=null;
		List<Ablum> ablumList = ablumService.findAllAblumOrderByCreateTime();
		int temp=0;
		for (int i = 0; i < ablumList.size(); i++) {
			if (ablum.getId() == ablumList.get(i).getId()) {
				temp = i;
				break;
			}
		}
		if (temp == 0) {
			sufAblum = ablumList.get(temp+1);
		}else if (temp==ablumList.size()-1)  {
			preAblum=ablumList.get(temp-1);
		}else {
			sufAblum = ablumList.get(temp+1);
			preAblum=ablumList.get(temp-1);
		}
		model.addAttribute("sufAblum", sufAblum);
		model.addAttribute("preAblum", preAblum);
		model.addAttribute("ablum", ablum);
		//������������
		PageBean ablumCommentPageBean = ablumCommentService.findAblumCommentPageBean(currentPage,id);
		model.addAttribute("ablumCommentPageBean", ablumCommentPageBean);
		return "detail-ablum.html";
	}
}
