/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    ProjectRoleRepository.java
 * Creator:     joe.zhao
 * Create-Date: 上午10:56:20
 */
package com.uuauth.ams.domain.repository;

import java.util.List;

import com.uuauth.ams.domain.entity.ProjectInfo;
import com.uuauth.ams.domain.entity.ProjectRole;

/**
 * TODO
 * @author joe.zhao
 * @version $Id: ProjectRoleRepository, v 0.1 2012-5-9 上午10:56:20 Exp $
 */
public interface ProjectRoleRepository {

    List<ProjectRole> list(String token);

    void add(ProjectRole role);

    void modify(ProjectRole role);

    void del(int id);

    ProjectRole getOne(int id);

    List<ProjectInfo> getSystemList(int uid);

}
