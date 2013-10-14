/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    TemplateRoleRepository.java
 * Creator:     joe.zhao
 * Create-Date: 下午12:55:32
 */
package com.uuauth.ams.domain.repository;

import java.util.List;

import com.uuauth.ams.domain.entity.TemplateRole;

/**
 * TODO
 * @author joe.zhao
 * @version $Id: TemplateRoleRepository, v 0.1 2012-5-9 下午12:55:32 Exp $
 */
public interface TemplateRoleRepository {

    void init();

    List<TemplateRole> list();

    int add(TemplateRole templateRole);

    void modify(TemplateRole templateRole);

    void del(int id);

    TemplateRole getOne(int id);

}
