/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    ProjectController.java
 * Creator:     joe.zhao
 * Create-Date: 下午09:12:35
 */
package com.uuauth.ams.controller;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.toolkit.lang.SecurityUtil;
import com.uuauth.ams.PmsConst;
import com.uuauth.ams.domain.entity.Employee;
import com.uuauth.ams.domain.entity.EmployeeInfo;
import com.uuauth.ams.domain.entity.ProjectInfo;
import com.uuauth.ams.domain.repository.ProjectInfoRepository;
import com.uuauth.ams.domain.service.ProjectService;

/**
 * 工程操作控制器
 * 
 * @author joe.zhao
 * @version $Id: ProjectController, v 0.1 2012-5-9 下午09:12:35 Exp $
 */
@Controller
@RequestMapping("/project")
public class ProjectController extends BaseController implements WebConstants,
		AuthConstants {
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		List<ProjectInfo> list = projectInfoRepository.list();
		setValue(model, "list", list);
		return forward("/project/list");
	}
	
	/**
	 * 我的工作平台下面的能登录的子系统列表
	 * 
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/syslist", method = RequestMethod.GET)
	public String syslist(HttpServletRequest req, Model model) {
		Employee employee = (Employee) req.getSession().getAttribute(
				ApsPmsConst.sessionUser);
		if (null != employee) {
			List<ProjectInfo> list = projectService.getSystemList(employee
					.getId());
			setValue(model, "list", list);
		}
		return forward("/project/syslist");
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model) {
		ProjectInfo project = new ProjectInfo();
		project.setToken("");
		String strRand = "";
		do {
			Random rdm = new Random();
			strRand = SecurityUtil.MD5(String.valueOf(rdm.nextLong())
					+ String.valueOf(rdm.nextLong()));
			if (projectInfoRepository.getOne(strRand) == null)
				break;// 保证工程代号不重复
		} while (true);
		
		project.setToken(strRand);
		project.setLoginpassword(strRand.substring(2, 10));
		setValue(model, "project", project);
		return forward("/project/add");
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@ModelAttribute("project") ProjectInfo project,
			Model model) {
		projectInfoRepository.add(project);
		projectInfoRepository.init();
		return redirect("/project/list");
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(@RequestParam("token") String token, Model model) {
		ProjectInfo project = projectInfoRepository.getOne(token);
		setValue(model, "project", project);
		return forward("/project/edit");
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String edit(@ModelAttribute("project") ProjectInfo project,
			Model model) {
		if (!StringUtils.isEmpty(project.getToken())) {
			projectInfoRepository.modify(project);
			projectInfoRepository.init();
		}
		return redirect("/project/list");
	}
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(@RequestParam("token") String token, Model model) {
		ProjectInfo project = projectInfoRepository.getOne(token);
		setValue(model, "project", project);
		List<EmployeeInfo> elist = projectService.getUserList(token);
		setValue(model, "elist", elist);
		return forward("/project/view");
	}
	
	@RequestMapping(value = "/del", method = RequestMethod.GET)
	public String del(@RequestParam("token") String token, Model model) {
		List<EmployeeInfo> list = projectService.getUserList(token);
		if (null == list || list.isEmpty()) {
			projectInfoRepository.del(token);
			projectInfoRepository.init();
		} else {
			setValue(model, "msg", "undel");
		}
		return redirect("/project/list");
	}
	
	// 从auto login登录子系统
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public void login(HttpServletRequest req, HttpServletResponse resp,
			@RequestParam("token") String token, Model model)
			throws IOException {
		long startTime = System.currentTimeMillis();
		if (logger.isInfoEnabled()) {
			logger.info("start time:" + dateformat.format(new Date()));
		}
		ProjectInfo project = projectInfoRepository.getOne(token);
		String url = authApiService.getApiUrl()
				+ "/remoteauth/check_login?proj=" + token + "&url=";
		if (logger.isInfoEnabled()) {
			logger.info("project=[{}], url=[{}]",
					new Object[] { project.getToken(), url });
		}
		
		HttpSession session = req.getSession();
		String sessionToken = (String) session.getAttribute(COOKIE_TOKEN);
		if (logger.isDebugEnabled()) {
			logger.debug(COOKIE_TOKEN + "=" + sessionToken);
		}
		resp.sendRedirect(resp.encodeRedirectURL(url));
		long endTime = System.currentTimeMillis();
		if (logger.isInfoEnabled()) {
			logger.info("use time:"
					+ nf.format(((endTime - startTime) / 1000.0)));
			logger.info("end time:" + dateformat.format(new Date()));
		}
		return;
	}
	
	@Autowired(required = true)
	public void setProjectInfoRepository(
			ProjectInfoRepository projectInfoRepository) {
		this.projectInfoRepository = projectInfoRepository;
	}
	
	@Autowired(required = true)
	public void setProjectService(ProjectService projectService) {
		this.projectService = projectService;
	}
	
	private ProjectInfoRepository			projectInfoRepository;
	private ProjectService					projectService;
	private UUAuthAPIService				authApiService	= UUAuthAPIService
																	.getInstance();
	private Logger							logger			= LoggerFactory
																	.getLogger(getClass());
	private static final SimpleDateFormat	dateformat		= new SimpleDateFormat(
																	"yyyy-MM-dd HH:mm:sss");
	private static final NumberFormat		nf				= new DecimalFormat(
																	"#.####");
	
}
