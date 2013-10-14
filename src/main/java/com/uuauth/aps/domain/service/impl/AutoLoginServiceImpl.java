/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    AutoLoginServiceImpl.java
 * Creator:     joe.zhao
 * Create-Date: 下午01:55:20
 */
package com.uuauth.aps.domain.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;

import com.toolkit.lang.SecurityUtil;
import com.toolkit.lang.WebClient;
import com.uuauth.ams.domain.entity.AuthType;
import com.uuauth.ams.domain.entity.EmployeeInfo;
import com.uuauth.ams.domain.entity.ProjectInfo;
import com.uuauth.ams.domain.entity.ProjectLog;
import com.uuauth.ams.domain.repository.ProjectLogRepository;
import com.uuauth.aps.domain.service.AutoLoginService;
import com.uuauth.aps.domain.service.BaseService;

/**
 * TODO
 * 
 * @author joe.zhao
 * @version $Id: AutoLoginServiceImpl, v 0.1 2012-5-9 下午01:55:20 Exp $
 */
public class AutoLoginServiceImpl extends BaseService implements
		AutoLoginService {
	
	@Override
	public String getLoginUrl(EmployeeInfo employee, ProjectInfo project,
			String role, String ip) {
		String loginUrl = "";
		String sessionToken = employee.getSessionToken();
		
		String suffix = "";
		if (!StringUtils.isEmpty(uuAuthAPIService.getSuffixFromCache(project
				.getToken())))
			suffix = uuAuthAPIService.getSuffixFromCache(project.getToken());
		
		// check=md5(loginName+role+uid+proPassword+projToken)
		// TODO 把登录后的sessionToken要传过去，保存到子系统memery cache中
		// 与子系统进行数据交换
		String redirUrl = project.getHomepage() + ("/login/get_login_token");
		
		if (!"null".equals(suffix) && !StringUtils.isEmpty(suffix))
			redirUrl += (suffix);
		
		String enRole = role;
		
		try {
			enRole = URLEncoder.encode(role, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		redirUrl += "?name="
				+ employee.getLoginName()
				+ "&role="
				+ enRole
				+ "&token="
				+ sessionToken
				+ "&id="
				+ String.valueOf(employee.getId())
				+ "&check="
				+ (SecurityUtil.MD5(employee.getLoginName() + role
						+ employee.getId() + project.getLoginpassword()
						+ project.getToken()));
		
		String check = WebClient.retrieveWebContent(redirUrl);
		
		if (check.length() == 32) {
			loginUrl = project.getHomepage() + "/login/auto_login";
			if (!"null".equals(suffix) && !StringUtils.isEmpty(suffix))
				loginUrl += suffix;
			loginUrl += "?name=" + employee.getLoginName() + "&check=" + check
					+ "&token=" + sessionToken;
			
			ProjectLog log = new ProjectLog();
			log.setEmpId(employee.getId());
			log.setProToken(project.getToken());
			log.setType(AuthType.NAME_PWD);
			log.setIp(ip);
			log.setLogTime((int) (System.currentTimeMillis() / 1000));
			projectLogRepository.add(log);
		}
		
		return loginUrl;
	}
	
	public void setProjectLogRepository(
			ProjectLogRepository projectLogRepository) {
		this.projectLogRepository = projectLogRepository;
	}
	
	private ProjectLogRepository	projectLogRepository;
	private UUAuthAPIService		uuAuthAPIService	= UUAuthAPIService
																.getInstance();
	
}
