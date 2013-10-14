/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    UserMapService.java
 * Creator:     joe.zhao
 * Create-Date: 下午02:12:36
 */
package com.uuauth.aps.domain.service;

import java.util.List;
import java.util.Map;

import com.uuauth.ams.domain.entity.EmployeeInfo;

/**
 * TODO
 * 
 * @author joe.zhao
 * @version $Id: UserMapService, v 0.1 2012-5-9 下午02:12:36 Exp $
 */
public interface UserMapService {
	
	void init();
	
	void cacheToken(String name, String token, String role, String id);
	
	Map<String, String> checkToken(String name, String token);
	
	boolean addUser(String id);
	
	List<EmployeeInfo> getAllUserList();
	
	List<EmployeeInfo> getWorkingUserList();
	
	List<EmployeeInfo> getLeaveUserList();
	
	EmployeeInfo getUser(Integer id);
	
}
