package com.yuchao.blog.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.yuchao.blog.domain.Blog;
import com.yuchao.blog.domain.Catalog;
import com.yuchao.blog.domain.Mood;
import com.yuchao.blog.dto.PageBean;
import com.yuchao.blog.service.BlogCommentService;
import com.yuchao.blog.service.BlogService;
import com.yuchao.blog.service.CatalogService;
import com.yuchao.blog.service.MoodService;
import com.yuchao.blog.utils.YuchaoUtils;

@Controller
public class BlogController {

	@Autowired
	private MoodService moodService;
	@Autowired
	private BlogService blogService;
	@Autowired
	private CatalogService catalogService;
	@Autowired
	private BlogCommentService blogCommentService;

	// ͨ��id ��ѯ����blog��ת����detailҳ��
	@RequestMapping("/blogDetail")
	public String blogDetail(Model model, String id, Integer currentPage) {
		// ��ѯ����
		Blog blog = blogService.findBlogById(new Long(id));
		Blog preBlog = null;
		Blog sufBlog = null;
		List<Blog> blogList = blogService.findAllBlogOrderByCreateTime();
		int temp = 0;
		for (int i = 0; i < blogList.size(); i++) {
			if (blog.getId() == blogList.get(i).getId()) {
				temp = i;
				break;
			}
		}
		if (temp == 0) {
			sufBlog = blogList.get(temp+1);
		}else if (temp==blogList.size()-1)  {
			preBlog=blogList.get(temp-1);
		}else {
			sufBlog = blogList.get(temp+1);
			preBlog=blogList.get(temp-1);
		}
		model.addAttribute("sufBlog", sufBlog);
		model.addAttribute("preBlog", preBlog);
		model.addAttribute("blog", blog);
		// ��ѯ��������
		PageBean blogCommentPageBean = blogCommentService.findBlogCommentByBlogIdAndCurrentPage(new Long(id), currentPage);
		model.addAttribute("blogCommentPageBean", blogCommentPageBean);

		return "detail-blog.html";
	}

	// �ϴ����ͷ���
	@RequestMapping("/uploadBlogCover")
	@ResponseBody
	public String uploadBlogCover(MultipartFile blogCoverFile) throws IllegalStateException, IOException {

		String afterClassPathUrl = "static/upload/blogCover";
		String fileName = YuchaoUtils.saveImage2ServerReturnString(blogCoverFile, afterClassPathUrl);
		return fileName;
	}

	// �ϴ�����ͼƬ
	@RequestMapping("/uploadBlogImage")
	@ResponseBody
	public String uploadBlogImage(MultipartFile blogImageFile) throws IllegalStateException, IOException {

		String afterClassPathUrl = "static/upload/blogImage";
		String fileName = YuchaoUtils.saveImage2ServerReturnString(blogImageFile, afterClassPathUrl);
		return fileName;
	}

	// ��Ӳ���
	@RequestMapping("/addBlog")
	public String addBlog(Blog blog, String moodId, String catalogString) {
		// ����ʱ��
		blog.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
		// �����
		blog.setViewNumber(0L);
		// �ظ���
		blog.setCommentNumber(0L);
		// ά��mood��ϵ
		Mood mood = null;
		if (!moodId.isEmpty()) {
			mood = moodService.findMoodById(new Long(moodId));
		}
		blog.setMood(mood);

		// TODO ά�����������Ĺ�ϵ
		Set<Catalog> catalogSet = new HashSet<Catalog>();
		// ����catalogs��ͨ�������
		String[] catalogArray = catalogString.split(";");
		for (String catalog : catalogArray) {
			// �ж��Ƿ������catalog
			Catalog selectCatalog = catalogService.findCatalogByCatalog(catalog);
			if (selectCatalog == null) {
				// ��������
				Catalog createCatalog = new Catalog(null, catalog, new HashSet<Blog>());
				// û�о����
				createCatalog = catalogService.saveCatalog(createCatalog);
				catalogSet.add(createCatalog);
			} else {
				catalogSet.add(selectCatalog);
			}
		}
		blog.setCatalogs(catalogSet);
		// ��Ӳ���
		blogService.saveBlog(blog);
		return "redirect:/findBlog";
	}

	// ��ѡ��˵˵��
	@RequestMapping("/openSelectMood")
	public String openSelectMood(Model model) {
		List<Mood> moodList = moodService.findMood();
		model.addAttribute("moodList", moodList);
		return "select-mood.html";
	}

	// Ԥ������
	@RequestMapping("/previewBlog")
	public String previewBlog(Blog blog, String moodId, String catalogString, Model model) {
		// �����
		blog.setViewNumber(0L);
		// �ظ���
		blog.setCommentNumber(0L);
		// ����ʱ��
		blog.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));

		// ����
		Mood mood = null;
		if (!moodId.isEmpty()) {
			mood = moodService.findMoodById(new Long(moodId));
		}
		blog.setMood(mood);
		// ����
		String[] catalogArray = catalogString.split(";");
		Set<Catalog> catalogSet = new HashSet<Catalog>();
		for (String catalog : catalogArray) {
			// ��������
			Catalog createCatalog = new Catalog(null, catalog, new HashSet<Blog>());
			catalogSet.add(createCatalog);
		}
		blog.setCatalogs(catalogSet);

		model.addAttribute("blog", blog);

		return "preview-detail.html";
	}

	// ��ѯ����blog��blog�ķ��࣬��ת��blogҳ��
	@RequestMapping("/findBlog")
	public String findBlog(Model model) {

		List<Blog> blogList = blogService.findBlog();
		List<Catalog> catalogList = catalogService.findCatalog();

		model.addAttribute("blogList", blogList);
		model.addAttribute("catalogList", catalogList);

		return "/blog";
	}
}
