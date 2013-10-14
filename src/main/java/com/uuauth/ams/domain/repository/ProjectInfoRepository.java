/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    ProductInfoRepository.java
 * Creator:     joe.zhao
 * Create-Date: 上午10:12:53
 */
package com.uuauth.ams.domain.repository;

import java.util.List;
import java.util.Map;

import com.uuauth.ams.domain.entity.ProjectInfo;

/**
 * TODO
 * @author joe.zhao
 * @version $Id: ProductInfoRepository, v 0.1 2012-5-9 上午10:12:53 Exp $
 */
public interface ProjectInfoRepository {

    void init();

    List<ProjectInfo> list();

    void add(ProjectInfo projectInfo);

    void modify(ProjectInfo projectInfo);

    void del(String token);

    ProjectInfo getOne(String token);

    Map<String, ProjectInfo> getProjectByTemId(int templateId);

    Map<String, ProjectInfo> getProjectByEmpId(int empId);

}
