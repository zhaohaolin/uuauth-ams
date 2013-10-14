/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    TemplateController.java
 * Creator:     joe.zhao
 * Create-Date: 上午10:39:21
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

import com.uuauth.ams.domain.entity.BasePost;
import com.uuauth.ams.domain.entity.EmployeeInfo;
import com.uuauth.ams.domain.entity.EmployeeRole;
import com.uuauth.ams.domain.entity.ProjectInfo;
import com.uuauth.ams.domain.entity.TemplateContent;
import com.uuauth.ams.domain.entity.TemplateRole;
import com.uuauth.ams.domain.repository.BasePostRepository;
import com.uuauth.ams.domain.repository.EmployeeInfoRepository;
import com.uuauth.ams.domain.repository.EmployeeRoleRepository;
import com.uuauth.ams.domain.repository.ProjectInfoRepository;
import com.uuauth.ams.domain.repository.TemplateContentRepository;
import com.uuauth.ams.domain.repository.TemplateRoleRepository;

/**
 * 工程模板操作的控制器
 * 
 * @author joe.zhao
 * @version $Id: TemplateController, v 0.1 2012-5-12 上午10:39:21 Exp $
 */
@Controller
@RequestMapping("/template")
public class TemplateController extends BaseController {
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		List<ProjectInfo> list = projectInfoRepository.list();
		setValue(model, "list", list);
		setValue(model, "template", new TemplateRole());
		return forward("/template/add");
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@ModelAttribute("template") TemplateRole role, Model model) {
		templateRoleRepository.add(role);
		// /////////////////////////调整重复权限
		List<EmployeeInfo> emps = employeeInfoRepository.getNeedTemplates();
		List<ProjectInfo> projects = projectInfoRepository.list();
		List<EmployeeRole> employeeroles;
		List<TemplateContent> contents;
		for (EmployeeInfo employee : emps) {
			BasePost post = basePostRepository.getOne(employee.getPostid());
			if (null == post)
				continue;
			int templateId = post.getRoleTmpId();// 首先判断员工的是不是匹配了这个模版
			if (templateId == role.getId()) {
				for (ProjectInfo project : projects) {
					employeeroles = employeeRoleRepository
							.listByEmployeeIdAndToken(employee.getId(),
									project.getToken());
					contents = templateContentRepository
							.listByTemplateIdAndToken(role.getId(),
									project.getToken());
					
					for (EmployeeRole eRole : employeeroles) {// 清理重复权限
						for (TemplateContent content : contents) {
							if (content.getRoleName().equals(
									eRole.getRoleName())) {
								employeeRoleRepository.delById(eRole.getId()
										.intValue());
							}
						}
					}
				}
			}
			
		}
		templateRoleRepository.init();
		return redirect("/template/list");
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(@RequestParam("id") Integer id, Model model) {
		List<ProjectInfo> list = projectInfoRepository.list();
		setValue(model, "list", list);
		TemplateRole template = templateRoleRepository.getOne(id);
		setValue(model, "template", template);
		return forward("/template/edit");
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String edit(@ModelAttribute("template") TemplateRole role,
			Model model) {
		Integer id = role.getId();
		// 先删除
		templateContentRepository.del(id);
		String contentList = role.getContentList();
		if (contentList.indexOf(";") != -1) {
			TemplateContent content = new TemplateContent();
			String[] contents = contentList.split(";");
			for (int i = 0; i < contents.length; i++) {
				String[] templates = contents[i].split("-");
				content.setTmpId(role.getId());
				content.setProToken(templates[0]);
				content.setRoleName(templates[1]);
				templateContentRepository.add(content);
			}
		}
		// /////////////////////////调整重复权限
		List<EmployeeInfo> emps = employeeInfoRepository.getNeedTemplates();
		List<ProjectInfo> projects = projectInfoRepository.list();
		List<EmployeeRole> employeeroles;
		List<TemplateContent> contents;
		for (EmployeeInfo employee : emps) {
			BasePost post = basePostRepository.getOne(employee.getPostid());
			if (null == post)
				continue;
			int templateId = post.getRoleTmpId();// 首先判断员工的是不是匹配了这个模版
			if (templateId == role.getId()) {
				for (ProjectInfo project : projects) {
					employeeroles = employeeRoleRepository
							.listByEmployeeIdAndToken(employee.getId(),
									project.getToken());
					contents = templateContentRepository
							.listByTemplateIdAndToken(role.getId(),
									project.getToken());
					
					for (EmployeeRole eRole : employeeroles) {// 清理重复权限
						for (TemplateContent content : contents) {
							if (content.getRoleName().equals(
									eRole.getRoleName())) {
								employeeRoleRepository.delById(eRole.getId()
										.intValue());
							}
						}
					}
				}
			}
			
		}
		templateRoleRepository.init();
		setValue(model, "id", id);
		return redirect("/template/edit");
	}
	
	// 删除角色模板
	@RequestMapping(value = "/del", method = RequestMethod.GET)
	public String del(@RequestParam("id") Integer id, Model model) {
		List<BasePost> postlist = basePostRepository.listByTemplateId(id);
		if (null != postlist && !postlist.isEmpty()) {
			setValue(model, "msg", "undel");
		} else {
			// 删除模板
			templateRoleRepository.del(id);
			// 删除模板内容
			templateContentRepository.del(id);
		}
		return redirect("/template/list");
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		List<TemplateRole> list = templateRoleRepository.list();
		setValue(model, "list", list);
		return forward("/template/list");
	}
	
	@Autowired(required = true)
	public void setProjectInfoRepository(
			ProjectInfoRepository projectInfoRepository) {
		this.projectInfoRepository = projectInfoRepository;
	}
	
	@Autowired(required = true)
	public void setTemplateRoleRepository(
			TemplateRoleRepository templateRoleRepository) {
		this.templateRoleRepository = templateRoleRepository;
	}
	
	@Autowired(required = true)
	public void setTemplateContentRepository(
			TemplateContentRepository templateContentRepository) {
		this.templateContentRepository = templateContentRepository;
	}
	
	@Autowired(required = true)
	public void setBasePostRepository(BasePostRepository basePostRepository) {
		this.basePostRepository = basePostRepository;
	}
	
	@Autowired(required = true)
	public void setEmployeeInfoRepository(
			EmployeeInfoRepository employeeInfoRepository) {
		this.employeeInfoRepository = employeeInfoRepository;
	}
	
	@Autowired(required = true)
	public void setEmployeeRoleRepository(
			EmployeeRoleRepository employeeRoleRepository) {
		this.employeeRoleRepository = employeeRoleRepository;
	}
	
	private ProjectInfoRepository		projectInfoRepository;
	private TemplateRoleRepository		templateRoleRepository;
	private TemplateContentRepository	templateContentRepository;
	private BasePostRepository			basePostRepository;
	private EmployeeInfoRepository		employeeInfoRepository;
	private EmployeeRoleRepository		employeeRoleRepository;
	
}
