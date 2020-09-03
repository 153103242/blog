package com.yuchao.blog.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.httpclient.HTTPException;
import com.yuchao.blog.domain.User;
import com.yuchao.blog.domain.Visitor;
import com.yuchao.blog.enumeration.ModifyType;
import com.yuchao.blog.service.UserService;
import com.yuchao.blog.service.VisitorService;
import com.yuchao.blog.utils.YuchaoUtils;

import sun.misc.BASE64Decoder;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private VisitorService visitorService;
	@Autowired
	private HttpSession session;
	// 更新用户名
	@RequestMapping("/modifyUsername")
	public String modifyUsername(String username, Model model) {
		modifyUser(ModifyType.USERNAME, username);
		return "user.html";
	}

	// 更新密码
	@RequestMapping("/modifyPassword")
	public String modifyPassword(String password, Model model) {
		modifyUser(ModifyType.PASSWORD, password);
		return "user.html";
	}

	// 更新个性签名
	@RequestMapping("/modifyPersonalSign")
	public String modifyPersonalSign(String personalSign, Model model) {
		modifyUser(ModifyType.PERSONAL_SIGN, personalSign);
		return "user.html";
	}

	// 得到用户
	@RequestMapping("/getOwner")
	public String getOwner(Model model) {
		modifyUser(ModifyType.NONE, null);
		return "user.html";
	}

	// 修改用户
	private void modifyUser(ModifyType type, String modifyInfo) {
		switch (type) {
		case USERNAME:
			userService.modifyUsername(modifyInfo);
			break;
		case PASSWORD:
			userService.modifyPassword(modifyInfo);
			break;
		case PERSONAL_SIGN:
			userService.modifyPersonalSign(modifyInfo);
			break;
		case HEAD:
			userService.modifyHeadImage(modifyInfo);
			break;
		default:
			break;
		}
	}

	// 保存头像
	@RequestMapping("/saveImage")
	@ResponseBody
	public String saveImage(MultipartFile file) throws IllegalStateException, IOException {

		String afterClassPathUrl = "static/upload";
		String fileName = YuchaoUtils.saveImage2ServerReturnString(file, afterClassPathUrl);

		return fileName;
	}

	// 上传头像
	@RequestMapping("/uploadHeadImage")
	public String uploadHeadImage(String base64) throws IOException {
		// 保存地址
		String path = ResourceUtils.getURL("classpath:").getPath() + "static/upload/headImage/";
		File fileLocation = new File(path);
		if (!fileLocation.exists()) {
			fileLocation.mkdirs();
		}
		// 替换数据
		if (base64.indexOf("jpeg") != -1) {
			base64 = base64.replaceFirst("jpeg", "jpg");
		}
		// 文件名
		String fileName = UUID.randomUUID().toString() + System.currentTimeMillis() + "." + base64.substring(11, 14);

		// 将base64有用的数据保存下来
		String iconBase64 = base64.substring(22);
		// 将base64换位字节数据
		byte[] buffer = new BASE64Decoder().decodeBuffer(iconBase64);
		// 用fileOutputStream写入
		FileOutputStream fos = new FileOutputStream(path + fileName);
		fos.write(buffer);
		fos.close();

		// 保存到数据库中
		modifyUser(ModifyType.HEAD, fileName);
		// 更新session中的头像
		Object visitor = session.getAttribute("visitor");
		if (visitor instanceof User) {
			((User)visitor).setImage(fileName);
		}
		return "redirect:/user";
	}

	// 发送短信
	@RequestMapping("/sendSMS")
	@ResponseBody
	public String sendSMS(String telephone, HttpSession session) {

		// 判断电话号码是否重复
		Visitor visitor = visitorService.findVisitorByUsername(telephone);
		if (visitor != null) {
			// 重复返回false
			return "false";
		} else {
			// 不重复返回true，发送短信
			sms(telephone, session);
			return "true";
		}
	}

	private void sms(String telephone, HttpSession session) {
		// 获取APPID
		int appid = 1400207237;
		// 获取APPKEY
		String appkey = "8f4f2ac8b6b6bbe207c656b710fc4e5d";
		// 获取模板
		int templateId = 323278;
		// 获取签名
		String smsSign = "tVirtue公众号";
		// 获取验证码
		String[] params = new String[1];
		params[0] = "";
		Random r = new Random();
		for (int i = 0; i < 4; i++) {
			params[0] += r.nextInt(10);
		}
		session.setAttribute("validateCode", params[0]);
		System.out.println("验证码:" + params[0]);
		// 获取电话号码
		// 创建发送者
//		SmsSingleSender sender = new SmsSingleSender(appid, appkey);
//		// 发送
//		try {
//			sender.sendWithParam("86", telephone, templateId, params, smsSign, "", "");
//		} catch (HTTPException | JSONException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	//校验验证码
	@RequestMapping("/judgeValidateCode")
	@ResponseBody
	public String judgeValidateCode(String validateCode,HttpSession session) {
		String sessionValidateCode = (String) session.getAttribute("validateCode");
		if (!validateCode.equals(sessionValidateCode)) {
			return "false";
		}else {
			return "true";
		}
	}
	//添加用户
	@RequestMapping("/addVisitor")
	public String addVisitor(Visitor visitor) {
		Random r = new Random();
		int number=r.nextInt(8)+1;
		String imageUrl="/images/1-"+number+".jpg";
		visitor.setImage(imageUrl);
		
		visitorService.save(visitor);
		
		return "redirect:/visitorLogin";
	}
	//改变登录页面
	@RequestMapping("/require")
	public String require() {
		return "redirect:/visitorLogin";
	}
	
}
