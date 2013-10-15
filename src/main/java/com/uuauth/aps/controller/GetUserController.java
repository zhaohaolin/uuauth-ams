/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    GetUserController.java
 * Creator:     joe.zhao
 * Create-Date: 下午04:57:50
 */
package com.uuauth.aps.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.toolkit.lang.SecurityUtil;
import com.uuauth.ams.domain.entity.EmployeeInfo;
import com.uuauth.ams.domain.entity.ProjectInfo;
import com.uuauth.ams.domain.repository.EmployeeInfoRepository;
import com.uuauth.aps.domain.service.RemoteAuthService;

/**
 * TODO
 * 
 * @author joe.zhao
 * @version $Id: GetUserController, v 0.1 2012-5-10 下午04:57:50 Exp $
 */
@Controller
public class GetUserController {
	
	@RequestMapping("/remoteauth/get_user")
	@ResponseBody
	public EmployeeInfo getUser(HttpServletRequest req) {
		String id = req.getParameter("id");
		String token = req.getParameter("token");
		String check = req.getParameter("check");
		if (id == null || token == null || check == null)
			return null;
		ProjectInfo proj = remoteAuthService.getProjectInfo(token);
		if (proj == null)
			return null;
		if (!check.equals(SecurityUtil.MD5(id + proj.getLoginpassword()
				+ proj.getToken())))
			return null;
		EmployeeInfo employee = employeeInfoRepository.getOne(Integer
				.parseInt(id));
		employee.setLoginPassword("");
		return employee;
	}
	
	@Autowired(required = true)
	public void setEmployeeInfoRepository(
			EmployeeInfoRepository employeeInfoRepository) {
		this.employeeInfoRepository = employeeInfoRepository;
	}
	
	@Autowired(required = true)
	public void setRemoteAuthService(RemoteAuthService remoteAuthService) {
		this.remoteAuthService = remoteAuthService;
	}
	
	private EmployeeInfoRepository	employeeInfoRepository;
	private RemoteAuthService		remoteAuthService;
	
}
