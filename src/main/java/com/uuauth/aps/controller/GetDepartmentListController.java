/*
 * CopyRight (c) 2005-2012 SHOWWA Co, Ltd. All rights reserved.
 * Filename:    GetDepartmentListController.java
 * Creator:     joe.zhao
 * Create-Date: 下午03:33:42
 */
package com.uuauth.aps.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uuauth.ams.domain.entity.BaseDepartment;
import com.uuauth.ams.domain.repository.BaseDepartmentRepository;

/**
 * TODO
 * 
 * @author joe.zhao
 * @version $Id: GetDepartmentListController, v 0.1 2012-6-16 下午03:33:42 Exp $
 */
@Controller
public class GetDepartmentListController {
	
	@RequestMapping("/remoteauth/get_department_list")
	@ResponseBody
	public List<BaseDepartment> getDepartmentList(HttpServletRequest req) {
		String token = req.getParameter("token");
		if (token == null)
			return null;
		List<BaseDepartment> list = baseDepartmentRepository.list();
		return list;
	}
	
	@Autowired(required = true)
	public void setBaseDepartmentRepository(
			BaseDepartmentRepository baseDepartmentRepository) {
		this.baseDepartmentRepository = baseDepartmentRepository;
	}
	
	private BaseDepartmentRepository	baseDepartmentRepository;
	
}
