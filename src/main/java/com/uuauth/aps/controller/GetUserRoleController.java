/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    GetUserRoleController.java
 * Creator:     joe.zhao
 * Create-Date: 下午02:40:57
 */
package com.uuauth.aps.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.toolkit.lang.StringUtils;
import com.uuauth.ams.domain.entity.ProjectInfo;
import com.uuauth.aps.domain.service.RemoteAuthService;

/**
 * TODO
 * 
 * @author joe.zhao
 * @version $Id: GetUserRoleController, v 0.1 2012-5-16 下午02:40:57 Exp $
 */
@Controller
public class GetUserRoleController {
	
	@RequestMapping("/remoteauth/get_user_role")
	@ResponseBody
	public String getUserRole(HttpServletRequest req) {
		String token = req.getParameter("token");
		String employeeId = req.getParameter("eid");
		if (StringUtils.isEmpty(token) || StringUtils.IsEmailAddr(employeeId))
			return "";
		ProjectInfo proj = remoteAuthService.getProjectInfo(token);
		String role = remoteAuthService.getUserRole(proj,
				Integer.valueOf(employeeId));
		return role;
	}
	
	@Autowired(required = true)
	public void setRemoteAuthService(RemoteAuthService remoteAuthService) {
		this.remoteAuthService = remoteAuthService;
	}
	
	private RemoteAuthService	remoteAuthService;
	
}
