/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    ProjectLogRepositoryIbatis.java
 * Creator:     joe.zhao
 * Create-Date: 下午02:08:20
 */
package com.auth.server.infrastructure.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.commons.persistence.BaseDAO;

import com.auth.server.domain.model.ProjectLog;
import com.auth.server.domain.repository.ProjectLogRepository;

/**
 * TODO
 * @author joe.zhao
 * @version $Id: ProjectLogRepositoryIbatis, v 0.1 2012-5-9 下午02:08:20 Exp $
 */
public class ProjectLogRepositoryIbatis extends BaseDAO implements ProjectLogRepository {

    @Override
    public List<ProjectLog> list(int empid, int num) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("empId", empid);
        param.put("num", num);
        return queryForList("list", param);
    }

    @Override
    public boolean add(ProjectLog log) {
        super.insert("add", log);
        return true;
    }

}
