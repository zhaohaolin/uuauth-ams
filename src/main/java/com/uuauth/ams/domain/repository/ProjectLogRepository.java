/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    ProjectLogRepository.java
 * Creator:     joe.zhao
 * Create-Date: 下午02:06:54
 */
package com.uuauth.ams.domain.repository;

import java.util.List;

import com.uuauth.ams.domain.entity.ProjectLog;

/**
 * TODO
 * @author joe.zhao
 * @version $Id: ProjectLogRepository, v 0.1 2012-5-9 下午02:06:54 Exp $
 */
public interface ProjectLogRepository {

    List<ProjectLog> list(int empid, int num);

    boolean add(ProjectLog log);

}
