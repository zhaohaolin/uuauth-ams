/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    TemplateRoleRepositoryIbatis.java
 * Creator:     joe.zhao
 * Create-Date: 下午12:58:07
 */
package com.auth.server.infrastructure.ibatis;

import java.util.List;

import org.commons.persistence.BaseDAO;

import com.auth.server.domain.model.TemplateRole;
import com.auth.server.domain.repository.TemplateRoleRepository;

/**
 * TODO
 * @author joe.zhao
 * @version $Id: TemplateRoleRepositoryIbatis, v 0.1 2012-5-9 下午12:58:07 Exp $
 */
public class TemplateRoleRepositoryIbatis extends BaseDAO implements TemplateRoleRepository {

    public void init() {
        List<TemplateRole> list = list();
        if (null == list || list.isEmpty())
            return;
        TemplateRole.list.clear();
        for (TemplateRole temp : list) {
            TemplateRole.list.put(temp.getId(), temp.getTemplateName());
        }
    }

    @Override
    public List<TemplateRole> list() {
        return super.queryForList("LIST");
    }

    @Override
    public int add(TemplateRole templateRole) {
        Object obj = super.insert("ADD", templateRole);
        return (Integer) obj;
    }

    @Override
    public void modify(TemplateRole templateRole) {
        super.update("MODIFY", templateRole);
    }

    @Override
    public void del(int id) {
        super.delete("DEL", id);
    }

    @Override
    public TemplateRole getOne(int id) {
        List<TemplateRole> list = super.queryForList("GET_ONE", id);
        if (null == list || list.isEmpty())
            return null;
        return list.get(0);
    }

}
