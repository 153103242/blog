package com.yuchao.blog.serviceImpl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuchao.blog.domain.Image;
import com.yuchao.blog.repository.ImageRepository;
import com.yuchao.blog.service.ImageService;
@Service
public class ImageServiceImpl implements ImageService {

	@Autowired
	private ImageRepository imageRepository;
	
	@Transactional
	public void saveImage(Image image) {
		imageRepository.save(image);
		image.getAblum().setImageNumber(image.getAblum().getImageNumber()+1);
	}

}
