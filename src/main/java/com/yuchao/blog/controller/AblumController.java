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
	//上传封面
	@RequestMapping("/uploadAblumCover")
	@ResponseBody
	public String uploadAblumCover(MultipartFile file) throws IllegalStateException, IOException {
		
		String afterClassPathUrl = "static/upload/ablumCover";
		String fileName = YuchaoUtils.saveImage2ServerReturnString(file, afterClassPathUrl);
		
		return fileName;
	}
	//转发到预览页面
	@RequestMapping("/previewAblum")
	public String previewAblum(Ablum ablum,Model model)  {
		//创建时间
		ablum.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
		
		model.addAttribute("ablum",ablum);
		return "preview-ablum.html";
	}
	//添加相册
	@RequestMapping("/addAblum")
	public String addAblum(Ablum ablum) {
		ablum.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
		ablum.setImageNumber(0L);
		ablumService.saveAblum(ablum);
		
		return "redirect:/findAblum";
		
	}
	//查找所有相册
	@RequestMapping("/findAblum")
	public String findAblum(Model model) {
		
		List<Ablum> ablumList = ablumService.findAblum();
		model.addAttribute("ablumList", ablumList);
		
		return "/ablum";
	}
	//打开选择相册弹出层
	@RequestMapping("/openSelectAblum")
	public String openSelectAblum(Model model) {
		
		List<Ablum> ablumList = ablumService.findAblum();
		model.addAttribute("ablumList", ablumList);
		
		return "select-ablum.html";
	}
	//上传相册图片
	@RequestMapping("/uploadAblumImage")
	@ResponseBody
	public String uploadAblumImage(MultipartFile[] files,String ablumId) throws IllegalStateException, IOException {
		
		//获得相册
		Ablum ablum = ablumService.findAblumById(new Long(ablumId));
		//添加图片到服务器中
		String afterClassPathUrl = "static/upload/ablum"+ablumId;
		for (MultipartFile file : files) {
			String fileName = YuchaoUtils.saveImage2ServerReturnString(file, afterClassPathUrl);
			//维护关系
			Image image = new Image(null, fileName, ablum);
			imageService.saveImage(image);
		}
		
		return "success";
	}
	//查找相册到详情页面
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
		//查找相册的评论
		PageBean ablumCommentPageBean = ablumCommentService.findAblumCommentPageBean(currentPage,id);
		model.addAttribute("ablumCommentPageBean", ablumCommentPageBean);
		return "detail-ablum.html";
	}
}
