/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    ProjectService.java
 * Creator:     joe.zhao
 * Create-Date: 下午10:55:25
 */
package com.uuauth.ams.domain.service;

import java.util.List;

import com.uuauth.ams.domain.entity.EmployeeInfo;
import com.uuauth.ams.domain.entity.ProjectInfo;

/**
 * TODO
 * @author joe.zhao
 * @version $Id: ProjectService, v 0.1 2012-5-9 下午10:55:25 Exp $
 */
public interface ProjectService {

    List<ProjectInfo> getSystemList(int uid);

    List<EmployeeInfo> getUserList(String token);

}
