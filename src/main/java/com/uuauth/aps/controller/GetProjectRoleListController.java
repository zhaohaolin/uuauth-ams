/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    GetProjectRoleListController.java
 * Creator:     joe.zhao
 * Create-Date: 下午08:43:47
 */
package com.uuauth.aps.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uuauth.ams.domain.entity.ProjectRole;
import com.uuauth.aps.domain.service.RemoteAuthService;

/**
 * TODO
 * 
 * @author joe.zhao
 * @version $Id: GetProjectRoleListController, v 0.1 2012-5-20 下午08:43:47 Exp $
 */
@Controller
public class GetProjectRoleListController {
	
	@RequestMapping("/remoteauth/get_proj_role_list")
	@ResponseBody
	public List<ProjectRole> getProjectRoleList(HttpServletRequest req) {
		String token = req.getParameter("token");
		if (StringUtils.isEmpty(token))
			return null;
		List<ProjectRole> list = remoteAuthService.getProjRoleList(token);
		return list;
	}
	
	@Autowired(required = true)
	public void setRemoteAuthService(RemoteAuthService remoteAuthService) {
		this.remoteAuthService = remoteAuthService;
	}
	
	private RemoteAuthService	remoteAuthService;
	
}
