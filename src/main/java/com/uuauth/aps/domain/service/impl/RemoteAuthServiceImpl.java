/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    RemoteAuthServiceImpl.java
 * Creator:     joe.zhao
 * Create-Date: 下午03:12:55
 */
package com.uuauth.aps.domain.service.impl;

import java.util.List;

import com.uuauth.ams.domain.entity.EmployeeInfo;
import com.uuauth.ams.domain.entity.EmployeeRole;
import com.uuauth.ams.domain.entity.ProjectInfo;
import com.uuauth.ams.domain.entity.ProjectRole;
import com.uuauth.ams.domain.entity.TemplateContent;
import com.uuauth.ams.domain.repository.BasePostRepository;
import com.uuauth.ams.domain.repository.EmployeeInfoRepository;
import com.uuauth.ams.domain.repository.EmployeeRoleRepository;
import com.uuauth.ams.domain.repository.ProjectInfoRepository;
import com.uuauth.ams.domain.repository.ProjectRoleRepository;
import com.uuauth.ams.domain.repository.TemplateContentRepository;
import com.uuauth.aps.domain.service.RemoteAuthService;

/**
 * TODO
 * 
 * @author joe.zhao
 * @version $Id: RemoteAuthServiceImpl, v 0.1 2012-5-9 下午03:12:55 Exp $
 */
public class RemoteAuthServiceImpl implements RemoteAuthService {
	
	private ProjectInfoRepository		projectInfoRepository;
	private ProjectRoleRepository		projectRoleRepository;
	private EmployeeInfoRepository		employeeInfoRepository;
	private EmployeeRoleRepository		employeeRoleRepository;
	private TemplateContentRepository	templateContentRepository;
	private BasePostRepository			basePostRepository;
	private String						domain	= ".globe.com";
	
	@Override
	public ProjectInfo getProjectInfo(String proToken) {
		ProjectInfo info = projectInfoRepository.getOne(proToken);
		return info;
	}
	
	@Override
	public EmployeeInfo getUserInfo(String uname) {
		EmployeeInfo info = employeeInfoRepository.getUserInfo(uname);
		return info;
	}
	
	@Override
	public String getUserRole(ProjectInfo pro, int uid) {
		// 查询指定token的系统的详细信息
		ProjectInfo project = projectInfoRepository.getOne(pro.getToken());
		if (null == project) {
			return "";
		}
		// 设置工程信息
		pro.setDescription(project.getDescription());
		pro.setHomepage(project.getHomepage());
		pro.setLoginpassword(project.getLoginpassword());
		pro.setLoginurl(project.getLoginurl());
		pro.setName(project.getName());
		// 设置角色信息
		String roleList = "|";
		int templateId = 0;
		// 查询当前uid的用户详细信息
		EmployeeInfo employee = employeeInfoRepository.getOne(uid);
		if (null == employee)
			return "";
		
		templateId = basePostRepository.getOne(employee.getPostid())
				.getRoleTmpId();
		
		if (0 != templateId) {
			List<TemplateContent> template = templateContentRepository
					.listByTemplateIdAndToken(templateId, pro.getToken());
			if (null != template && !template.isEmpty()) {
				for (TemplateContent content : template) {
					roleList += content.getRoleName() + "|";
				}
			}
		}
		
		List<EmployeeRole> employeeRoleList = employeeRoleRepository
				.listByEmployeeIdAndToken(uid, pro.getToken());
		if (null != employeeRoleList && !employeeRoleList.isEmpty()) {
			for (EmployeeRole role : employeeRoleList) {
				roleList += role.getRoleName() + "|";
			}
		}
		
		return roleList;
	}
	
	@Override
	public List<ProjectRole> getProjRoleList(String token) {
		return projectRoleRepository.list(token);
	}
	
	@Override
	public EmployeeInfo getUserInfoByNick(String nick) {
		return employeeInfoRepository.getUserInfoByNick(nick);
	}
	
	@Override
	public String getDomain() {
		return this.domain;
	}
	
	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	public void setProjectRoleRepository(
			ProjectRoleRepository projectRoleRepository) {
		this.projectRoleRepository = projectRoleRepository;
	}
	
	public void setProjectInfoRepository(
			ProjectInfoRepository projectInfoRepository) {
		this.projectInfoRepository = projectInfoRepository;
	}
	
	public void setEmployeeInfoRepository(
			EmployeeInfoRepository employeeInfoRepository) {
		this.employeeInfoRepository = employeeInfoRepository;
	}
	
	public void setEmployeeRoleRepository(
			EmployeeRoleRepository employeeRoleRepository) {
		this.employeeRoleRepository = employeeRoleRepository;
	}
	
	public void setTemplateContentRepository(
			TemplateContentRepository templateContentRepository) {
		this.templateContentRepository = templateContentRepository;
	}
	
	public void setBasePostRepository(BasePostRepository basePostRepository) {
		this.basePostRepository = basePostRepository;
	}
	
}
