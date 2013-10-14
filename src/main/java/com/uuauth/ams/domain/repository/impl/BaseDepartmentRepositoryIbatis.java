/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    BaseDepartmentRepositoryIbatis.java
 * Creator:     joe.zhao
 * Create-Date: 下午02:58:44
 */
package com.uuauth.ams.domain.repository.impl;

import java.util.List;

import com.avaje.ebean.Ebean;
import com.uuauth.ams.domain.entity.BaseDepartment;
import com.uuauth.ams.domain.repository.BaseDepartmentRepository;

/**
 * TODO
 * 
 * @author joe.zhao
 * @version $Id: BaseDepartmentRepositoryIbatis, v 0.1 2012-5-8 下午02:58:44 Exp $
 */
public class BaseDepartmentRepositoryIbatis implements BaseDepartmentRepository {
	
	/**
	 * init memory cache for app init
	 */
	public void init() {
		BaseDepartment.list.clear();
		List<BaseDepartment> list = list();
		if (null != list && !list.isEmpty()) {
			for (BaseDepartment department : list) {
				BaseDepartment.list.put(department.getId(),
						department.getName());
			}
		}
	}
	
	@Override
	public List<BaseDepartment> list() {
		return Ebean.find(BaseDepartment.class).findList();
	}
	
	@Override
	public BaseDepartment getOne(int id) {
		List<BaseDepartment> list = Ebean.find(BaseDepartment.class).where()
				.eq("id", id).findList();
		if (null == list || list.isEmpty())
			return null;
		return list.get(0);
	}
	
}
