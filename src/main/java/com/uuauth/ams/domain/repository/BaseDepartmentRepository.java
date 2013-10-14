/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    BaseDepartmentRepository.java
 * Creator:     joe.zhao
 * Create-Date: 下午02:53:50
 */
package com.uuauth.ams.domain.repository;

import java.util.List;

import com.uuauth.ams.domain.entity.BaseDepartment;

/**
 * TODO
 * 
 * @author joe.zhao
 * @version $Id: BaseDepartmentRepository, v 0.1 2012-5-8 下午02:53:50 Exp $
 */
public interface BaseDepartmentRepository {
	
	void init();
	
	List<BaseDepartment> list();
	
	BaseDepartment getOne(int id);
	
}
