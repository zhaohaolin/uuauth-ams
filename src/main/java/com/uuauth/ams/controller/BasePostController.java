/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    BasePostController.java
 * Creator:     joe.zhao
 * Create-Date: 下午07:30:10
 */
package com.uuauth.ams.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uuauth.ams.domain.entity.BaseDepartment;
import com.uuauth.ams.domain.entity.BasePost;
import com.uuauth.ams.domain.entity.EmployeeInfo;
import com.uuauth.ams.domain.entity.TemplateRole;
import com.uuauth.ams.domain.repository.BasePostRepository;
import com.uuauth.ams.domain.repository.EmployeeInfoRepository;

/**
 * 岗位操作控制器
 * 
 * @author joe.zhao
 * @version $Id: BasePostController, v 0.1 2012-5-9 下午07:30:10 Exp $
 */
@Controller
@RequestMapping("/post")
public class BasePostController extends BaseController {
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		List<BasePost> list = basePostRepository.list();
		setValue(model, "list", list);
		return forward("/post/list");
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		setValue(model, "post", new BasePost());
		Map<Integer, String> depMap = BaseDepartment.list;
		setValue(model, "depMap", depMap);
		Map<Integer, String> tempMap = TemplateRole.list;
		setValue(model, "tempMap", tempMap);
		return forward("/post/add");
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@ModelAttribute("post") BasePost post, Model model) {
		basePostRepository.insert(post);
		basePostRepository.init();
		return redirect("/post/list");
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(@RequestParam("id") Integer id, Model model) {
		BasePost post = basePostRepository.getOne(id);
		setValue(model, "post", post);
		Map<Integer, String> depMap = BaseDepartment.list;
		setValue(model, "depMap", depMap);
		Map<Integer, String> tempMap = TemplateRole.list;
		setValue(model, "tempMap", tempMap);
		return forward("/post/edit");
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String edit(@ModelAttribute("post") BasePost post, Model model) {
		if (post.getId() != 0) {
			basePostRepository.update(post);
			basePostRepository.init();
		}
		return redirect("/post/list");
	}
	
	@RequestMapping(value = "/del", method = RequestMethod.GET)
	public String del(@RequestParam("id") Integer id, Model model) {
		List<EmployeeInfo> elist = employeeInfoRepository.list(null, 0, id, 0);
		if (null == elist || elist.isEmpty()) {
			basePostRepository.delete(id);
			basePostRepository.init();
		} else {
			setValue(model, "msg", "undel");
		}
		return redirect("/post/list");
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
	
	private BasePostRepository		basePostRepository;
	private EmployeeInfoRepository	employeeInfoRepository;
	
}
