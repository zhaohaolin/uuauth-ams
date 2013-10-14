/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    ProjectRoleRepositoryIbatis.java
 * Creator:     joe.zhao
 * Create-Date: 上午11:06:59
 */
package com.auth.server.infrastructure.ibatis;

import java.util.List;

import org.commons.persistence.BaseDAO;

import com.auth.server.domain.model.ProjectInfo;
import com.auth.server.domain.model.ProjectRole;
import com.auth.server.domain.repository.ProjectRoleRepository;

/**
 * TODO
 * @author joe.zhao
 * @version $Id: ProjectRoleRepositoryIbatis, v 0.1 2012-5-9 上午11:06:59 Exp $
 */
public class ProjectRoleRepositoryIbatis extends BaseDAO implements ProjectRoleRepository {

    @Override
    public List<ProjectRole> list(String token) {
        return super.queryForList("list", token);
    }

    @Override
    public void add(ProjectRole projectRole) {
        insert("add", projectRole);
    }

    @Override
    public void modify(ProjectRole projectRole) {
        update("modify", projectRole);
    }

    @Override
    public void del(int id) {
        super.delete("del", id);
    }

    @Override
    public ProjectRole getOne(int id) {
        List<ProjectRole> list = queryForList("getOne", id);
        if (null == list || list.isEmpty())
            return null;
        return list.get(0);
    }

    @Override
    public List<ProjectInfo> getSystemList(int uid) {
        return null;
    }

}
