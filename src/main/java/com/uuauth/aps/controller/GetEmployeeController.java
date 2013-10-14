/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    GetEmployeeController.java
 * Creator:     qiaofeng
 * Create-Date: 上午10:38:20
 */
package com.uuauth.aps.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uuauth.ams.domain.entity.EmployeeInfo;
import com.uuauth.ams.domain.repository.EmployeeInfoRepository;

/**
 * TODO
 * 
 * @author qiaofeng
 * @version $Id: GetEmployeeController, v 0.1 2012-10-8 上午10:38:20 Exp $
 */
@Controller
public class GetEmployeeController {
	
	private EmployeeInfoRepository	employeeInfoRepository;
	
	@Autowired(required = true)
	public void setEmployeeInfoRepository(
			EmployeeInfoRepository employeeInfoRepository) {
		this.employeeInfoRepository = employeeInfoRepository;
	}
	
	@RequestMapping("/remoteauth/get_employee")
	@ResponseBody
	public EmployeeInfo getEmployee(HttpServletRequest req) {
		String loginName = req.getParameter("u");
		String proj = req.getParameter("proj");
		if (StringUtils.isEmpty(loginName) || StringUtils.isEmpty(proj))
			return null;
		EmployeeInfo employee = employeeInfoRepository.getUserInfo(loginName);
		if (null != employee)
			employee.setLoginPassword("");
		return employee;
	}
	
}
