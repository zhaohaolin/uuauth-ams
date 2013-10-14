/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    EmployeeInfoRepository.java
 * Creator:     joe.zhao
 * Create-Date: 下午08:39:01
 */
package com.uuauth.ams.domain.repository;

import java.util.List;

import com.uuauth.ams.domain.entity.EmployeeInfo;

/**
 * TODO
 * 
 * @author joe.zhao
 * @version $Id: EmployeeInfoRepository, v 0.1 2012-5-8 下午08:39:01 Exp $
 */
public interface EmployeeInfoRepository {
	
	void init();
	
	List<EmployeeInfo> list();
	
	List<EmployeeInfo> list(String name, int dep, int post, int status);
	
	EmployeeInfo getUserInfo(String uname);
	
	boolean updateLoginInfo(int empId, String clientIp);
	
	EmployeeInfo getOne(int id);
	
	List<EmployeeInfo> getNeedTemplates();
	
	void modifypwd(int id, String pwd);
	
	void setstatus(int id, int status);
	
}
