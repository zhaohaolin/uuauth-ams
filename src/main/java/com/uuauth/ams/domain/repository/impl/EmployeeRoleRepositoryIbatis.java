/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    EmployeeRoleRepositoryIbatis.java
 * Creator:     joe.zhao
 * Create-Date: 上午09:17:44
 */
package com.auth.server.infrastructure.ibatis;

import java.util.List;

import org.commons.persistence.BaseDAO;

import com.auth.server.domain.model.EmployeeRole;
import com.auth.server.domain.repository.EmployeeRoleRepository;

/**
 * TODO
 * @author joe.zhao
 * @version $Id: EmployeeRoleRepositoryIbatis, v 0.1 2012-5-9 上午09:17:44 Exp $
 */
public class EmployeeRoleRepositoryIbatis extends BaseDAO implements EmployeeRoleRepository {

    @Override
    public void add(EmployeeRole role) {
        //添加前先检查是否存在，如果存在就不写入
        if (checkExist(role))
            return;
        super.insert("ADD", role);
    }

    @Override
    public void del(int id) {
        super.delete("DEL_BY_EMPID", id);
    }

    @Override
    public void delById(int id) {
        super.delete("DEL_BY_ID", id);
    }

    public boolean checkExist(EmployeeRole role) {
        List<EmployeeRole> list = super.queryForList("checkExist", role);
        if (null == list || list.isEmpty())
            return false;
        return true;
    }

    @Override
    public List<EmployeeRole> listByEmployeeIdAndToken(int empId, String token) {
        EmployeeRole role = new EmployeeRole();
        role.setEmpId(empId);
        role.setProToken(token);
        return super.queryForList("LIST_BY_EMPLOYEEID_AND_TOKEN", role);
    }

    @Override
    public List<EmployeeRole> listByTokenAndRoleName(String token, String roleName) {
        EmployeeRole role = new EmployeeRole();
        role.setProToken(token);
        role.setRoleName(roleName);
        return super.queryForList("LIST_BY_TOKEN_AND_ROLENAME", role);
    }

}
