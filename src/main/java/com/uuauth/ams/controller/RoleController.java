/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    RoleController.java
 * Creator:     joe.zhao
 * Create-Date: 下午07:50:43
 */
package com.uuauth.ams.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uuauth.ams.domain.entity.ProjectRole;
import com.uuauth.ams.domain.entity.TemplateContent;
import com.uuauth.ams.domain.repository.EmployeeRoleRepository;
import com.uuauth.ams.domain.repository.ProjectRoleRepository;
import com.uuauth.ams.domain.repository.TemplateContentRepository;

/**
 * 工程角色操作控制器
 * 
 * @author joe.zhao
 * @version $Id: RoleController, v 0.1 2012-5-11 下午07:50:43 Exp $
 */
@Controller
@RequestMapping(value = "/role")
public class RoleController extends BaseController {
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(@RequestParam("token") String token, Model model) {
		setValue(model, "token", token);
		List<ProjectRole> list = projectRoleRepository.list(token);
		setValue(model, "list", list);
		return forward("/role/list");
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(@RequestParam("token") String token, Model model) {
		ProjectRole role = new ProjectRole();
		role.setProToken(token);
		setValue(model, "role", role);
		return forward("/role/add");
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@ModelAttribute("role") ProjectRole role, Model model) {
		projectRoleRepository.add(role);
		setValue(model, "token", role.getProToken());
		return redirect("/role/list");
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(@RequestParam("id") Integer id, Model model) {
		ProjectRole role = projectRoleRepository.getOne(id);
		setValue(model, "role", role);
		return forward("/role/edit");
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String edit(@ModelAttribute("role") ProjectRole role, Model model) {
		projectRoleRepository.modify(role);
		setValue(model, "token", role.getProToken());
		return redirect("/role/list");
	}
	
	@RequestMapping(value = "/del", method = RequestMethod.GET)
	public String del(@RequestParam("id") Integer id, Model model) {
		ProjectRole role = projectRoleRepository.getOne(id);
		String token = role.getProToken();
		String roleName = role.getRoleName();
		setValue(model, "token", token);
		List<TemplateContent> tcList = templateContentRepository
				.listByTokenAndRoleName(token, roleName);
		List<com.uuauth.ams.domain.entity.EmployeeRole> erList = employeeRoleRepository
				.listByTokenAndRoleName(token, roleName);
		
		if ((null == tcList || tcList.isEmpty())
				&& (null == erList || erList.isEmpty())) {
			projectRoleRepository.del(id);
		} else {
			setValue(model, "msg", "undel");
		}
		return redirect("/role/list");
	}
	
	@Autowired(required = true)
	public void setProjectRoleRepository(
			ProjectRoleRepository projectRoleRepository) {
		this.projectRoleRepository = projectRoleRepository;
	}
	
	@Autowired(required = true)
	public void setTemplateContentRepository(
			TemplateContentRepository templateContentRepository) {
		this.templateContentRepository = templateContentRepository;
	}
	
	@Autowired(required = true)
	public void setEmployeeRoleRepository(
			EmployeeRoleRepository employeeRoleRepository) {
		this.employeeRoleRepository = employeeRoleRepository;
	}
	
	private ProjectRoleRepository		projectRoleRepository;
	private EmployeeRoleRepository		employeeRoleRepository;
	private TemplateContentRepository	templateContentRepository;
	
}
