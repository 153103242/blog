package com.yuchao.blog.utils;

import java.io.File;
import java.io.IOException;

import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class YuchaoUtils {

	public static String saveImage2ServerReturnString(MultipartFile file,String afterClassPathUrl) throws IllegalStateException, IOException {
		// �ļ���ǰ׺
		String fileNamePrefix = System.currentTimeMillis() + "";
		// �ļ�����׺
		String fileNameSuffix = StringUtils.getFilenameExtension(file.getOriginalFilename());
		// ƴ�������ļ���
		String fileName = fileNamePrefix + "." + fileNameSuffix;
		// ·��
		String path = ResourceUtils.getURL("classpath:").getPath() + afterClassPathUrl;
		File headImage = new File(path + "/" + fileName);
		if (!headImage.getParentFile().exists()) {
			headImage.getParentFile().mkdirs();
		}
		// �����ļ�
		file.transferTo(headImage);
		return fileName;
	}
}
