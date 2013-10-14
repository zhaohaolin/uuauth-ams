/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    EmployeeController.java
 * Creator:     joe.zhao
 * Create-Date: 下午09:59:53
 */
package com.uuauth.ams.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.toolkit.lang.DTUtil;
import com.toolkit.lang.SecurityUtil;
import com.uuauth.ams.controller.form.EmployeeForm;
import com.uuauth.ams.controller.form.EmployeePasswordForm;
import com.uuauth.ams.domain.entity.BaseDepartment;
import com.uuauth.ams.domain.entity.Employee;
import com.uuauth.ams.domain.entity.EmployeeInfo;
import com.uuauth.ams.domain.entity.EmployeeRole;
import com.uuauth.ams.domain.entity.ProjectInfo;
import com.uuauth.ams.domain.entity.WorkStatus;
import com.uuauth.ams.domain.repository.BasePostRepository;
import com.uuauth.ams.domain.repository.EmployeeInfoRepository;
import com.uuauth.ams.domain.repository.EmployeeRoleRepository;
import com.uuauth.ams.domain.repository.ProjectInfoRepository;

/**
 * 员工操作的控制器
 * 
 * @author joe.zhao
 * @version $Id: EmployeeController, v 0.1 2012-5-10 下午09:59:53 Exp $
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController extends BaseController implements WebConstants {
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(HttpServletRequest req, Model model) {
		EmployeeForm employeeForm = new EmployeeForm();
		String searchname = req.getParameter("searchname");
		String strsearchdepid = req.getParameter("searchdepid");
		String strsearchpostid = req.getParameter("searchpostid");
		String strsearchstatus = req.getParameter("searchstatus");
		int searchdepid = 0;
		int searchpostid = 0;
		int searchstatus = 0;
		
		if (!StringUtils.isEmpty(searchname)) {
			employeeForm.setSearchname(searchname);
		}
		if (!StringUtils.isEmpty(strsearchdepid)) {
			searchdepid = Integer.valueOf(strsearchdepid);
			employeeForm.setSearchdepid(searchdepid);
		}
		
		if (!StringUtils.isEmpty(strsearchpostid)) {
			searchpostid = Integer.valueOf(strsearchpostid);
			employeeForm.setSearchpostid(searchpostid);
		}
		
		if (!StringUtils.isEmpty(strsearchstatus)) {
			searchstatus = Integer.valueOf(strsearchstatus);
			employeeForm.setSearchstatus(searchstatus);
		}
		
		List<EmployeeInfo> list = employeeInfoRepository.list(searchname,
				searchdepid, searchpostid, searchstatus);
		setValue(model, "list", list);
		setValue(model, "employeeForm", employeeForm);
		initList(model);
		return forward("/employee/list");
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String list(
			@ModelAttribute("employeeForm") EmployeeForm employeeForm,
			Model model) {
		List<EmployeeInfo> list = employeeInfoRepository.list(
				employeeForm.getSearchname(), employeeForm.getSearchdepid(),
				employeeForm.getSearchpostid(), employeeForm.getSearchstatus());
		setValue(model, "list", list);
		setValue(model, "employeeForm", employeeForm);
		initList(model);
		return forward("/employee/list");
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		setValue(model, "employee", new EmployeeInfo());
		initList(model);
		return forward("/employee/add");
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@ModelAttribute("employee") EmployeeInfo employee,
			Model model) {
		employee.setLoginPassword(SecurityUtil.MD5("135246"));
		employee.setCreateTime((int) DTUtil.getTodayLongTime());
		employee.setStatus(WorkStatus.WORKING);
		employeeInfoRepository.insert(employee);
		employeeInfoRepository.init();
		return redirect("/employee/list");
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(@RequestParam("oldid") Integer oldid, Model model) {
		EmployeeInfo employee = employeeInfoRepository.getOne(oldid);
		if (null != employee) {
			employee.setOldid(oldid);
			Map<Integer, String> postlist = basePostRepository
					.getPostsByDepId(employee.getDepid());
			setValue(model, "postlist", postlist);
		}
		setValue(model, "employee", employee);
		initList(model);
		return forward("/employee/edit");
	}
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(@RequestParam("oldid") Integer oldid, Model model) {
		EmployeeInfo employee = employeeInfoRepository.getOne(oldid);
		setValue(model, "employee", employee);
		return forward("/employee/view");
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String edit(@ModelAttribute("employee") EmployeeInfo employee,
			Model model) {
		if (employee.isNeedinit()) {
			employee.setLoginPassword(SecurityUtil.MD5("135246"));
		}
		employeeInfoRepository.update(employee);
		employeeInfoRepository.init();
		// 只有变动岗位才会删除权限表里的权限
		return redirect("/employee/list");
	}
	
	@RequestMapping(value = "/del", method = RequestMethod.GET)
	public String del(@RequestParam("oldid") Integer id, Model model) {
		employeeInfoRepository.delete(id);
		employeeInfoRepository.init();
		employeeRoleRepository.del(id);
		// 删除权限表里的权限
		return redirect("/employee/list");
	}
	
	@RequestMapping(value = "/setstatus", method = RequestMethod.GET)
	public String setstatus(@RequestParam("oldid") Integer id,
			@RequestParam("status") Integer status, Model model) {
		employeeInfoRepository.setstatus(id, status);
		return redirect("/employee/list");
	}
	
	@RequestMapping(value = "/assign", method = RequestMethod.GET)
	public String assign(HttpServletRequest req,
			@RequestParam("oldid") Integer oldId, Model model) {
		List<ProjectInfo> list = projectInfoRepository.list();
		setValue(model, "list", list);
		EmployeeInfo employee = employeeInfoRepository.getOne(oldId);
		employee.setOldid(oldId);
		setValue(model, "employee", employee);
		return forward("/employee/assign");
	}
	
	@RequestMapping(value = "/assign", method = RequestMethod.POST)
	public String assign(HttpServletRequest req,
			@RequestParam("contentList") String contentList,
			@RequestParam("oldid") Integer oldId,
			@RequestParam("postid") Integer postid, Model model) {
		if (contentList.indexOf(";") != -1) {
			String[] contents = contentList.split(";");
			EmployeeRole role = new EmployeeRole();
			for (int i = 0; i < contents.length; i++) {
				String[] templates = contents[i].split("-");
				role.setEmpId(oldId);
				role.setProToken(templates[0]);
				role.setRoleName(templates[1]);
				employeeRoleRepository.add(role);
			}
		}
		setValue(model, "oldid", oldId);
		setValue(model, "postid", postid);
		return redirect("/employee/assign");
	}
	
	@RequestMapping(value = "/modifypwd", method = RequestMethod.GET)
	public String modifypwd(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession();
		int eid = ((Employee) session.getAttribute(ApsPmsConst.sessionUser))
				.getId();
		EmployeeInfo employee = employeeInfoRepository.getOne(eid);
		setValue(model, "employee", employee);
		EmployeePasswordForm employeePasswordForm = new EmployeePasswordForm();
		employeePasswordForm.setId(employee.getId());
		employeePasswordForm.setPwdOld(employee.getLoginPassword());
		setValue(model, "employeePasswordForm", employeePasswordForm);
		return forward("/employee/modifypwd");
	}
	
	@RequestMapping(value = "/modifypwd", method = RequestMethod.POST)
	public String modifypwd(
			@ModelAttribute("employeePasswordForm") EmployeePasswordForm employeePasswordForm,
			Model model) {
		int eid = employeePasswordForm.getId();
		String pwdNew = employeePasswordForm.getPwdNew();
		String pwdAgain = employeePasswordForm.getPwdAgain();
		String pwdOld = employeePasswordForm.getPwdOld();
		if (SecurityUtil.MD5(pwdNew).equals(SecurityUtil.MD5(pwdAgain))) {
			EmployeeInfo employee = employeeInfoRepository
					.getOne(employeePasswordForm.getId());
			if (employee.getLoginPassword().equals(SecurityUtil.MD5(pwdOld))) {
				employeeInfoRepository.modifypwd(eid, SecurityUtil.MD5(pwdNew));
			} else {
				setValue(model, "msg", "您的原始密码输入有误,请再次输入");
			}
		} else {
			setValue(model, "msg", "两次密码不一致");
		}
		return redirect("/employee/modifypwd");
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
	
	@Autowired(required = true)
	public void setBasePostRepository(BasePostRepository basePostRepository) {
		this.basePostRepository = basePostRepository;
	}
	
	@Autowired(required = true)
	public void setProjectInfoRepository(
			ProjectInfoRepository projectInfoRepository) {
		this.projectInfoRepository = projectInfoRepository;
	}
	
	private void initList(Model model) {
		Map<Integer, String> deplist = BaseDepartment.list;
		setValue(model, "deplist", deplist);
		Map<Integer, String> workstatuslist = WorkStatus.list;
		setValue(model, "workstatuslist", workstatuslist);
	}
	
	private EmployeeInfoRepository	employeeInfoRepository;
	private EmployeeRoleRepository	employeeRoleRepository;
	private BasePostRepository		basePostRepository;
	private ProjectInfoRepository	projectInfoRepository;
	
}
