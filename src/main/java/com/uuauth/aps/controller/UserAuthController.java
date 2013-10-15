/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    UserAuthController.java
 * Creator:     joe.zhao
 * Create-Date: 下午04:33:09
 */
package com.uuauth.aps.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.toolkit.lang.EncryptUtil;
import com.uuauth.ams.domain.entity.EmployeeInfo;
import com.uuauth.ams.domain.entity.ProjectInfo;
import com.uuauth.aps.domain.service.AutoLoginService;
import com.uuauth.aps.domain.service.RemoteAuthService;
import com.uuauth.aps.domain.service.SessionEmployeeService;

/**
 * TODO
 * 
 * @author joe.zhao
 * @version $Id: UserAuthController, v 0.1 2012-5-10 下午04:33:09 Exp $
 */
@Controller
public class UserAuthController implements AuthConstants {
	
	// private:
	private RemoteAuthService		remoteAuthService;
	private AutoLoginService		autoLoginService;
	private SessionEmployeeService	sessionEmployeeService;
	private Logger					log	= LoggerFactory.getLogger(getClass());
	
	// zhl@2012-05-24:修改接口，兼容客户端登录验证功能
	@RequestMapping(value = "/remoteauth/user_auth")
	@ResponseBody
	public ApiResult userAuth(HttpServletRequest req, HttpServletResponse resp)
			throws DecoderException {
		log.info("user login auth req=[{}], resp=[{}]", new Object[] { req,
				resp });
		
		ApiResult result = new ApiResult();
		result.setStatus(-1);
		result.setMessage("未知错误！");
		String pwd = req.getParameter("pwd");
		String u = req.getParameter("u");
		String proj = req.getParameter("proj");
		String mac = req.getParameter("mac");
		String loginIp = req.getRemoteAddr();
		
		log.info(
				"pwdEncode=[{}], user=[{}], projToken=[{}], mac=[{}], clientIp=[{}]",
				new Object[] { pwd, u, proj, mac, loginIp });
		
		// 参数检查
		if (pwd == null || u == null || proj == null || mac == null) {
			result.setMessage("参数不正确！");
			return result;
		}
		
		// 对u进行Base64解码
		u = new String(new Base64().decode(u.getBytes()));
		
		// mac=(md5后的password+用户名+项目token) 再对上面的值进行md5,转大写字母
		String srvMac = EncryptUtil.getMD5Data(pwd, u, proj).toUpperCase();
		
		// MAC认证
		if (!srvMac.equals(mac)) {
			result.setMessage("认证MAC校验错误！");
			log.warn("user auth mac=[{}] is err of srvMac=[{}].", new Object[] {
					mac, srvMac });
			return result;
		}
		
		// 从数据库中查询用户是否存在
		EmployeeInfo user = null;
		
		// 当使用英文名登录时
		if (u.length() == u.getBytes().length)
			user = remoteAuthService.getUserInfo(u);
		
		// 当使用中文花名登录时
		else
			user = remoteAuthService.getUserInfoByNick(u);
		
		if (null == user) {
			result.setMessage("用户名不存在！");
			log.warn("employee=[{}] user is not exist. ", user);
			return result;
		}
		
		// 用户名和密码校验
		HttpSession session = req.getSession();
		
		// 按登录的token保存进缓存中
		String sessionToken = (String) session.getAttribute(CLIENT_TOKEN);
		if (StringUtils.isEmpty(sessionToken)) {
			result.setMessage("登录异常！");
			log.warn("employee=[{}] session token is error. ", user);
			return result;
		}
		
		if (!checkUserPwd(req, u, pwd, user.getLoginPassword(), sessionToken)) {
			result.setMessage("用户名或者密码错误！");
			log.warn("employee=[{}] username or password is error. ", user);
			return result;
		}
		
		// 用户使用状态判断
		if (user.getStatus() == 2) {
			result.setMessage("用户已经被禁用！");
			log.warn("employee=[{}] is disabled to use.", user);
			return result;
		}
		
		ProjectInfo project = new ProjectInfo();
		project.setToken(proj);
		
		// 查询登录用户对当前系统角色
		String role = remoteAuthService.getUserRole(project, user.getId());
		if (StringUtils.isEmpty(role)) {
			result.setMessage("您无权登录此系统！");
			log.warn("employee=[{}] is not allowed to use.", user);
			return result;
		}
		
		user.setSessionToken(sessionToken);
		// 把用户信息保存进缓存中
		sessionEmployeeService.add(CLIENT_TOKEN + "$" + u, sessionToken);
		sessionEmployeeService.add(sessionToken, user);
		
		String url = autoLoginService.getLoginUrl(user, project, role, loginIp);
		url += "&url=";
		result.setStatus(0);
		result.setMessage(url);
		log.info("employee=[{}] is login successed.", user);
		return result;
	}
	
	// 校验登录用户密码是否正确
	// 校验方式：取clientToken+password(分别转大写字母) 再md5
	public boolean checkUserPwd(HttpServletRequest req, String name,
			String pwd, String dbPwd, String sessionToken) {
		// 从session中读取client token值
		log.info("sessionToken=" + sessionToken);
		
		if (!StringUtils.isEmpty(sessionToken)) {
			String authToken = EncryptUtil.getEncryptToken(sessionToken, dbPwd);
			if (authToken.compareToIgnoreCase(pwd) == 0) {
				return true;
			}
		}
		
		return false;
	}
	
	@Autowired(required = true)
	public void setRemoteAuthService(RemoteAuthService remoteAuthService) {
		this.remoteAuthService = remoteAuthService;
	}
	
	@Autowired(required = true)
	public void setAutoLoginService(AutoLoginService autoLoginService) {
		this.autoLoginService = autoLoginService;
	}
	
	@Autowired(required = true)
	public void setSessionEmployeeService(
			SessionEmployeeService sessionEmployeeService) {
		this.sessionEmployeeService = sessionEmployeeService;
	}
	
}
