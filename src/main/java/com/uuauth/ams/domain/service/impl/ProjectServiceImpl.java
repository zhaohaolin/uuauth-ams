/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    ProjectServiceImpl.java
 * Creator:     joe.zhao
 * Create-Date: 下午10:56:27
 */
package com.uuauth.ams.domain.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uuauth.ams.domain.entity.BasePost;
import com.uuauth.ams.domain.entity.EmployeeInfo;
import com.uuauth.ams.domain.entity.ProjectInfo;
import com.uuauth.ams.domain.repository.BasePostRepository;
import com.uuauth.ams.domain.repository.EmployeeInfoRepository;
import com.uuauth.ams.domain.repository.EmployeeRoleRepository;
import com.uuauth.ams.domain.repository.ProjectInfoRepository;
import com.uuauth.ams.domain.repository.TemplateContentRepository;
import com.uuauth.ams.domain.service.BaseService;
import com.uuauth.ams.domain.service.ProjectService;

/**
 * TODO
 * 
 * @author joe.zhao
 * @version $Id: ProjectServiceImpl, v 0.1 2012-5-9 下午10:56:27 Exp $
 */
@Service
public class ProjectServiceImpl extends BaseService implements ProjectService {
	
	// private:
	@Autowired(required = true)
	private EmployeeInfoRepository		employeeInfoRepository;
	
	@Autowired(required = true)
	private EmployeeRoleRepository		employeeRoleRepository;
	
	@Autowired(required = true)
	private TemplateContentRepository	templateContentRepository;
	
	@Autowired(required = true)
	private BasePostRepository			basePostRepository;
	
	@Autowired(required = true)
	private ProjectInfoRepository		projectInfoRepository;
	
	// public:
	@Override
	public List<ProjectInfo> getSystemList(int uid) {
		int templateId = 0;
		List<ProjectInfo> list = new ArrayList<ProjectInfo>();
		EmployeeInfo employee = employeeInfoRepository.getOne(uid);
		
		if (null == employee) {
			logger.warn("employee is null. uid=[{}]", uid);
			return null;
		}
		
		templateId = basePostRepository.getOne(employee.getPostid())
				.getRoleTmpId();
		Map<String, ProjectInfo> tempMap = null;
		
		if (templateId != 0) {
			tempMap = projectInfoRepository.getProjectByTemId(templateId);
		}
		
		Map<String, ProjectInfo> employeerole = projectInfoRepository
				.getProjectByEmpId(uid);
		
		if (tempMap != null && tempMap.size() > 0) {
			for (String key : tempMap.keySet()) {
				if (!employeerole.containsKey(key)) {
					employeerole.put(key, tempMap.get(key));
				}
			}
		}
		
		for (String key : employeerole.keySet()) {
			list.add(employeerole.get(key));
		}
		return list;
	}
	
	@Override
	public List<EmployeeInfo> getUserList(String token) {
		List<EmployeeInfo> userlist = new ArrayList<EmployeeInfo>();
		List<EmployeeInfo> list = employeeInfoRepository.list(null, 0, 0, 0);
		for (EmployeeInfo info : list) {
			BasePost basePost = basePostRepository.getOne(info.getPostid());
			int templateId = 0;
			if (null != basePost)
				templateId = basePost.getRoleTmpId();
			if (templateId != 0) {
				if (templateContentRepository.listByTemplateIdAndToken(
						templateId, token).size() > 0)
					userlist.add(info);
			} else {
				if (employeeRoleRepository.listByEmployeeIdAndToken(
						info.getId(), token).size() > 0)
					userlist.add(info);
			}
		}
		return userlist;
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
	
	public void setProjectInfoRepository(
			ProjectInfoRepository projectInfoRepository) {
		this.projectInfoRepository = projectInfoRepository;
	}
	
}
