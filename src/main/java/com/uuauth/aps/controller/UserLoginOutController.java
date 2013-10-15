/*
 * CopyRight (c) 2005-2012 SHOWWA Co, Ltd. All rights reserved.
 * Filename:    UserLoginOutController.java
 * Creator:     joe.zhao
 * Create-Date: 上午10:32:43
 */
package com.uuauth.aps.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.toolkit.lang.SecurityUtil;
import com.uuauth.ams.domain.entity.ProjectInfo;
import com.uuauth.aps.domain.service.RemoteAuthService;
import com.uuauth.aps.domain.service.SessionEmployeeService;

/**
 * 用户退出登录
 * 
 * @author joe.zhao
 * @version $Id: UserLoginOutController, v 0.1 2012-7-4 上午10:32:43 Exp $
 */
@Controller
public class UserLoginOutController implements AuthConstants {
	
	@RequestMapping("/remoteauth/user_login_out")
	@ResponseBody
	public boolean loginOut(HttpServletRequest req) {
		String user = req.getParameter("user");
		String token = req.getParameter("token");
		String check = req.getParameter("check");
		if (user == null || token == null || check == null)
			return false;
		ProjectInfo proj = remoteAuthService.getProjectInfo(token);
		if (proj == null)
			return false;
		if (!check.equals(SecurityUtil.MD5(user + proj.getLoginpassword()
				+ proj.getToken())))
			return false;
		
		String key = CLIENT_TOKEN + "$" + user;
		String sessionToken = (String) sessionEmployeeService.get(key);
		if (StringUtils.isEmpty(sessionToken))
			return true;
		sessionEmployeeService.del(sessionToken);
		boolean bool = sessionEmployeeService.del(key);
		return bool;
	}
	
	@Autowired(required = true)
	public void setSessionEmployeeService(
			SessionEmployeeService sessionEmployeeService) {
		this.sessionEmployeeService = sessionEmployeeService;
	}
	
	@Autowired(required = true)
	public void setRemoteAuthService(RemoteAuthService remoteAuthService) {
		this.remoteAuthService = remoteAuthService;
	}
	
	private RemoteAuthService		remoteAuthService;
	private SessionEmployeeService	sessionEmployeeService;
	
}
