/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    BaseDepartmentController.java
 * Creator:     joe.zhao
 * Create-Date: 下午02:13:44
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

import com.uuauth.ams.domain.entity.BaseDepartment;
import com.uuauth.ams.domain.entity.BasePost;
import com.uuauth.ams.domain.repository.BaseDepartmentRepository;
import com.uuauth.ams.domain.repository.BasePostRepository;

/**
 * 部门操作控制器
 * 
 * @author joe.zhao
 * @version $Id: BaseDepartmentController, v 0.1 2012-5-9 下午02:13:44 Exp $
 */
@Controller
@RequestMapping("/department")
public class BaseDepartmentController extends BaseController {
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		List<BaseDepartment> list = baseDepartmentRepository.list();
		setValue(model, "list", list);
		return forward("/department/list");
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		setValue(model, "department", new BaseDepartment());
		setValue(model, "departlist", BaseDepartment.list);
		return forward("/department/add");
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@ModelAttribute("department") BaseDepartment department,
			Model model) {
		baseDepartmentRepository.insert(department);
		baseDepartmentRepository.init();
		return redirect("/department/list");
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(@RequestParam("id") Integer id, Model model) {
		BaseDepartment department = baseDepartmentRepository.getOne(id);
		setValue(model, "department", department);
		setValue(model, "departlist", BaseDepartment.list);
		return forward("/department/edit");
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String edit(@ModelAttribute("department") BaseDepartment department,
			Model model) {
		if (department.getId().intValue() == department.getParentId()
				.intValue()) {
			// 说明选择父部门为自己，不允许操作
			setValue(model, "msg", "操作失败，不允许选择本部门为父部门.");
		} else if (department.getId() != 0) {
			baseDepartmentRepository.update(department);
			baseDepartmentRepository.init();
		}
		return redirect("/department/list");
	}
	
	@RequestMapping(value = "/del", method = RequestMethod.GET)
	public String del(@RequestParam("id") Integer id, Model model) {
		List<BasePost> posts = basePostRepository.getByDepId(id);
		if (null == posts || posts.isEmpty()) {
			baseDepartmentRepository.delete(id);
			baseDepartmentRepository.init();
		} else {
			setValue(model, "msg", "undel");
		}
		return redirect("/department/list");
	}
	
	@Autowired(required = true)
	public void setBaseDepartmentRepository(
			BaseDepartmentRepository baseDepartmentRepository) {
		this.baseDepartmentRepository = baseDepartmentRepository;
	}
	
	@Autowired(required = true)
	public void setBasePostRepository(BasePostRepository basePostRepository) {
		this.basePostRepository = basePostRepository;
	}
	
	private BaseDepartmentRepository	baseDepartmentRepository;
	private BasePostRepository			basePostRepository;
	
}
