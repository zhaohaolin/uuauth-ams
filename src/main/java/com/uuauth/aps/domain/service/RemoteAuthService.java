/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    RemoteAuthService.java
 * Creator:     joe.zhao
 * Create-Date: 下午03:12:02
 */
package com.uuauth.aps.domain.service;

import java.util.List;

import com.uuauth.ams.domain.entity.EmployeeInfo;
import com.uuauth.ams.domain.entity.ProjectInfo;
import com.uuauth.ams.domain.entity.ProjectRole;

/**
 * TODO
 * 
 * @author joe.zhao
 * @version $Id: RemoteAuthService, v 0.1 2012-5-9 下午03:12:02 Exp $
 */
public interface RemoteAuthService {
	
	/**
	 * 根据项目token返回项目详细信息
	 * 
	 * @param proToken
	 * @return
	 */
	ProjectInfo getProjectInfo(String proToken);
	
	/**
	 * 根据用户名称返回用户的详细信息
	 * 
	 * @param uname
	 * @return
	 */
	EmployeeInfo getUserInfo(String uname);
	
	/**
	 * 根据花名查询员工详细信息
	 * 
	 * @param nick
	 * @return
	 */
	EmployeeInfo getUserInfoByNick(String nick);
	
	/**
	 * 返回用户指定系统的角色，字符串示例：|root|admin|operate|
	 * 
	 * @param pro
	 * @param uid
	 * @return
	 */
	String getUserRole(final ProjectInfo pro, int uid);
	
	/**
	 * 返回项目的角色列表
	 * 
	 * @param token
	 * @return
	 */
	List<ProjectRole> getProjRoleList(String token);
	
	/**
	 * 得到配置的跨域配置
	 * 
	 * @return
	 */
	String getDomain();
	
}
