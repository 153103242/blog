package com.yuchao.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuchao.blog.domain.Ablum;
import com.yuchao.blog.domain.Blog;
import com.yuchao.blog.domain.Catalog;
import com.yuchao.blog.service.AblumService;
import com.yuchao.blog.service.BlogService;
import com.yuchao.blog.service.CatalogService;

@Controller
public class SearchController {

	@Autowired
	private BlogService blogService;
	@Autowired
	private AblumService ablumService;
	@Autowired
	private CatalogService catalogService;
	//����
	@RequestMapping("/search")
	public String search(String keyword,Model model,String type) {
		
		//��ȡ����
		List<Blog> blogList = blogService.findBlogListByKeyword(keyword);
		//��ȡ���
		List<Ablum> ablumList = ablumService.findAblumListByKeyword(keyword);
		
		
		model.addAttribute("blogList", blogList);
		model.addAttribute("ablumList", ablumList);
		
		model.addAttribute("keyword", keyword);
		model.addAttribute("type", type);
		return "search.html";
	}
	//������ǩ
	@RequestMapping("/searchCatalog")
	public String searchCatalog(String id,Model model) {
		
		//��ȡ��ǩ
		Catalog catalog = catalogService.findCatalogById(new Long(id));
		
		model.addAttribute("blogList", catalog.getBlogs());
		model.addAttribute("keyword", catalog.getCatalog());
		
		
		return "searchCatalog.html";
	}
}
