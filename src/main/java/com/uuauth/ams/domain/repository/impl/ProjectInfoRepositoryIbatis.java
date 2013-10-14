/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    ProductInfoRepositoryIbatis.java
 * Creator:     joe.zhao
 * Create-Date: 上午10:14:16
 */
package com.auth.server.infrastructure.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.commons.persistence.BaseDAO;

import com.auth.server.domain.model.ProjectInfo;
import com.auth.server.domain.repository.ProjectInfoRepository;

/**
 * TODO
 * @author joe.zhao
 * @version $Id: ProductInfoRepositoryIbatis, v 0.1 2012-5-9 上午10:14:16 Exp $
 */
public class ProjectInfoRepositoryIbatis extends BaseDAO implements ProjectInfoRepository {

    public void init() {
        List<ProjectInfo> list = list();
        if (null == list || list.isEmpty())
            return;
        ProjectInfo.list.clear();
        for (ProjectInfo info : list) {
            ProjectInfo.list.put(info.getToken(), info.getName());
        }
    }

    @Override
    public List<ProjectInfo> list() {
        return super.queryForList("LIST");
    }

    @Override
    public void add(ProjectInfo projectInfo) {
        super.insert("ADD", projectInfo);
    }

    @Override
    public void modify(ProjectInfo projectInfo) {
        super.update("MODIFY", projectInfo);
    }

    @Override
    public void del(String token) {
        super.delete("DEL", token);
    }

    @Override
    public ProjectInfo getOne(String token) {
        List<ProjectInfo> list = super.queryForList("GET_ONE", token);
        if (null == list || list.isEmpty())
            return null;
        return list.get(0);
    }

    @Override
    public Map<String, ProjectInfo> getProjectByEmpId(int empId) {
        Map<String, ProjectInfo> map = new HashMap<String, ProjectInfo>();

        List<ProjectInfo> list = super.queryForList("GET_PROJECT_BY_EMPID", empId);
        if (null != list && !list.isEmpty()) {
            for (ProjectInfo projectInfo : list) {
                map.put(projectInfo.getToken(), projectInfo);
            }
        }
        return map;
    }

    @Override
    public Map<String, ProjectInfo> getProjectByTemId(int templateId) {
        Map<String, ProjectInfo> map = new HashMap<String, ProjectInfo>();

        List<ProjectInfo> list = super.queryForList("GET_PROJECT_BY_TEMPID", templateId);
        if (null != list && !list.isEmpty()) {
            for (ProjectInfo projectInfo : list) {
                map.put(projectInfo.getToken(), projectInfo);
            }
        }
        return map;
    }

}
