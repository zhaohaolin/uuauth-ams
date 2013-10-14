/*
 * CopyRight (c) 2005-2012 SHOWWA Co, Ltd. All rights reserved.
 * Filename:    SessionEmployeeService.java
 * Creator:     joe.zhao
 * Create-Date: 下午11:30:37
 */
package com.uuauth.aps.domain.service;

/**
 * TODO
 * 
 * @author joe.zhao
 * @version $Id: SessionEmployeeService, v 0.1 2012-6-3 下午11:30:37 Exp $
 */
public interface SessionEmployeeService {
	
	<T> boolean add(String key, T value);
	
	<T> T get(String key);
	
	<T> boolean del(String key);
	
}
