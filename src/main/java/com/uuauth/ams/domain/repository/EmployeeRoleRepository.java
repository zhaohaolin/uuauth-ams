/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    EmployeeRoleRepository.java
 * Creator:     joe.zhao
 * Create-Date: 上午09:13:22
 */
package com.uuauth.ams.domain.repository;

import java.util.List;

import com.uuauth.ams.domain.entity.EmployeeRole;

/**
 * TODO
 * @author joe.zhao
 * @version $Id: EmployeeRoleRepository, v 0.1 2012-5-9 上午09:13:22 Exp $
 */
public interface EmployeeRoleRepository {

    void add(EmployeeRole role);

    void del(int id);

    void delById(int id);

    List<EmployeeRole> listByEmployeeIdAndToken(int empId, String token);

    List<EmployeeRole> listByTokenAndRoleName(String token, String roleName);

}
