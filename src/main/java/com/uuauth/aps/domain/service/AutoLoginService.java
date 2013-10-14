/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    AutoLoginService.java
 * Creator:     joe.zhao
 * Create-Date: 下午01:54:45
 */
package com.uuauth.aps.domain.service;

import com.uuauth.ams.domain.entity.EmployeeInfo;
import com.uuauth.ams.domain.entity.ProjectInfo;

/**
 * TODO
 * 
 * @author joe.zhao
 * @version $Id: AutoLoginService, v 0.1 2012-5-9 下午01:54:45 Exp $
 */
public interface AutoLoginService {
	
	String getLoginUrl(EmployeeInfo emInfo, ProjectInfo pro, String role,
			String ip);
	
}
