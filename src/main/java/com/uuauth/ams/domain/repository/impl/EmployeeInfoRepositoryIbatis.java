/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    EmployeeInfoRepositoryIbatis.java
 * Creator:     joe.zhao
 * Create-Date: 下午08:42:06
 */
package com.uuauth.ams.domain.repository.impl;

import java.util.List;

import com.avaje.ebean.Ebean;
import com.uuauth.ams.domain.entity.EmployeeInfo;
import com.uuauth.ams.domain.repository.EmployeeInfoRepository;

/**
 * TODO
 * 
 * @author joe.zhao
 * @version $Id: EmployeeInfoRepositoryIbatis, v 0.1 2012-5-8 下午08:42:06 Exp $
 */
public class EmployeeInfoRepositoryIbatis implements EmployeeInfoRepository {
	
	public void init() {
		EmployeeInfo.list.clear();
		List<EmployeeInfo> list = list();
		if (null != list && !list.isEmpty()) {
			for (EmployeeInfo info : list) {
				EmployeeInfo.list.put(info.getId(), info.getName());
			}
		}
	}
	
	public List<EmployeeInfo> list() {
		return Ebean.find(EmployeeInfo.class).findList();
	}
	
	@Override
	public List<EmployeeInfo> list(String name, int depid, int postid,
			int status) {
		EmployeeInfo employee = new EmployeeInfo();
		employee.setName(name);
		employee.setDepid(depid);
		employee.setPostid(postid);
		employee.setStatus(status);
		return super.queryForList("list", employee);
	}
	
	@Override
	public EmployeeInfo getUserInfo(String uname) {
		List<EmployeeInfo> list = super.queryForList("getUserInfo", uname);
		if (null == list || list.isEmpty())
			return null;
		return list.get(0);
	}
	
	@Override
	public boolean updateLoginInfo(int empId, String clientIp) {
		EmployeeInfo employee = new EmployeeInfo();
		employee.setLastLoginIp(clientIp);
		employee.setLastLoginTime(DTUtil.getTodayLongTime());
		employee.setId(empId);
		super.update("updateLoginInfo", employee);
		return true;
	}
	
	@Override
	public EmployeeInfo getOne(int id) {
		List<EmployeeInfo> list = super.queryForList("getOne", id);
		if (null == list || list.isEmpty())
			return null;
		return list.get(0);
	}
	
	@Override
	public List<EmployeeInfo> getNeedTemplates() {
		return super.queryForList("getNeedTemplates");
	}
	
	@Override
	public void modifypwd(int id, String pwd) {
		EmployeeInfo employee = new EmployeeInfo();
		employee.setId(id);
		employee.setLoginPassword(pwd);
		super.update("modifypwd", employee);
	}
	
	@Override
	public void setstatus(int id, int status) {
		status = 2 - status / 2;
		EmployeeInfo employee = new EmployeeInfo();
		employee.setId(id);
		employee.setStatus(status);
		super.update("setstatus", employee);
	}
	
}
