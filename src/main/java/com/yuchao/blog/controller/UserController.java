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
	// �����û���
	@RequestMapping("/modifyUsername")
	public String modifyUsername(String username, Model model) {
		modifyUser(ModifyType.USERNAME, username);
		return "user.html";
	}

	// ��������
	@RequestMapping("/modifyPassword")
	public String modifyPassword(String password, Model model) {
		modifyUser(ModifyType.PASSWORD, password);
		return "user.html";
	}

	// ���¸���ǩ��
	@RequestMapping("/modifyPersonalSign")
	public String modifyPersonalSign(String personalSign, Model model) {
		modifyUser(ModifyType.PERSONAL_SIGN, personalSign);
		return "user.html";
	}

	// �õ��û�
	@RequestMapping("/getOwner")
	public String getOwner(Model model) {
		modifyUser(ModifyType.NONE, null);
		return "user.html";
	}

	// �޸��û�
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

	// ����ͷ��
	@RequestMapping("/saveImage")
	@ResponseBody
	public String saveImage(MultipartFile file) throws IllegalStateException, IOException {

		String afterClassPathUrl = "static/upload";
		String fileName = YuchaoUtils.saveImage2ServerReturnString(file, afterClassPathUrl);

		return fileName;
	}

	// �ϴ�ͷ��
	@RequestMapping("/uploadHeadImage")
	public String uploadHeadImage(String base64) throws IOException {
		// �����ַ
		String path = ResourceUtils.getURL("classpath:").getPath() + "static/upload/headImage/";
		File fileLocation = new File(path);
		if (!fileLocation.exists()) {
			fileLocation.mkdirs();
		}
		// �滻����
		if (base64.indexOf("jpeg") != -1) {
			base64 = base64.replaceFirst("jpeg", "jpg");
		}
		// �ļ���
		String fileName = UUID.randomUUID().toString() + System.currentTimeMillis() + "." + base64.substring(11, 14);

		// ��base64���õ����ݱ�������
		String iconBase64 = base64.substring(22);
		// ��base64��λ�ֽ�����
		byte[] buffer = new BASE64Decoder().decodeBuffer(iconBase64);
		// ��fileOutputStreamд��
		FileOutputStream fos = new FileOutputStream(path + fileName);
		fos.write(buffer);
		fos.close();

		// ���浽���ݿ���
		modifyUser(ModifyType.HEAD, fileName);
		// ����session�е�ͷ��
		Object visitor = session.getAttribute("visitor");
		if (visitor instanceof User) {
			((User)visitor).setImage(fileName);
		}
		return "redirect:/user";
	}

	// ���Ͷ���
	@RequestMapping("/sendSMS")
	@ResponseBody
	public String sendSMS(String telephone, HttpSession session) {

		// �жϵ绰�����Ƿ��ظ�
		Visitor visitor = visitorService.findVisitorByUsername(telephone);
		if (visitor != null) {
			// �ظ�����false
			return "false";
		} else {
			// ���ظ�����true�����Ͷ���
			sms(telephone, session);
			return "true";
		}
	}

	private void sms(String telephone, HttpSession session) {
		// ��ȡAPPID
		int appid = 1400207237;
		// ��ȡAPPKEY
		String appkey = "8f4f2ac8b6b6bbe207c656b710fc4e5d";
		// ��ȡģ��
		int templateId = 323278;
		// ��ȡǩ��
		String smsSign = "tVirtue���ں�";
		// ��ȡ��֤��
		String[] params = new String[1];
		params[0] = "";
		Random r = new Random();
		for (int i = 0; i < 4; i++) {
			params[0] += r.nextInt(10);
		}
		session.setAttribute("validateCode", params[0]);
		System.out.println("��֤��:" + params[0]);
		// ��ȡ�绰����
		// ����������
//		SmsSingleSender sender = new SmsSingleSender(appid, appkey);
//		// ����
//		try {
//			sender.sendWithParam("86", telephone, templateId, params, smsSign, "", "");
//		} catch (HTTPException | JSONException | IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	//У����֤��
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
	//����û�
	@RequestMapping("/addVisitor")
	public String addVisitor(Visitor visitor) {
		Random r = new Random();
		int number=r.nextInt(8)+1;
		String imageUrl="/images/1-"+number+".jpg";
		visitor.setImage(imageUrl);
		
		visitorService.save(visitor);
		
		return "redirect:/visitorLogin";
	}
	//�ı��¼ҳ��
	@RequestMapping("/require")
	public String require() {
		return "redirect:/visitorLogin";
	}
	
}
