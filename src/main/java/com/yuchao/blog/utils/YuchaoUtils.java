package com.yuchao.blog.utils;

import java.io.File;
import java.io.IOException;

import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class YuchaoUtils {

	public static String saveImage2ServerReturnString(MultipartFile file,String afterClassPathUrl) throws IllegalStateException, IOException {
		// 文件名前缀
		String fileNamePrefix = System.currentTimeMillis() + "";
		// 文件名后缀
		String fileNameSuffix = StringUtils.getFilenameExtension(file.getOriginalFilename());
		// 拼接完整文件名
		String fileName = fileNamePrefix + "." + fileNameSuffix;
		// 路径
		String path = ResourceUtils.getURL("classpath:").getPath() + afterClassPathUrl;
		File headImage = new File(path + "/" + fileName);
		if (!headImage.getParentFile().exists()) {
			headImage.getParentFile().mkdirs();
		}
		// 保存文件
		file.transferTo(headImage);
		return fileName;
	}
}
