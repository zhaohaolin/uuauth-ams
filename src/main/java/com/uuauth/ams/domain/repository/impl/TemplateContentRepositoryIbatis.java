/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    TemplateContentRepository.java
 * Creator:     joe.zhao
 * Create-Date: 下午03:55:42
 */
package com.auth.server.infrastructure.ibatis;

import java.util.List;

import org.commons.persistence.BaseDAO;

import com.auth.server.domain.model.TemplateContent;
import com.auth.server.domain.repository.TemplateContentRepository;

/**
 * TODO
 * @author joe.zhao
 * @version $Id: TemplateContentRepository, v 0.1 2012-5-9 下午03:55:42 Exp $
 */
public class TemplateContentRepositoryIbatis extends BaseDAO implements TemplateContentRepository {

    @Override
    public void add(TemplateContent content) {
        //先检查是否存在
        if (!checkExist(content))
            super.insert("add", content);
    }

    public boolean checkExist(TemplateContent content) {
        List<TemplateContent> list = super.queryForList("checkExist", content);
        if (null == list || list.isEmpty())
            return false;
        return true;
    }

    @Override
    public List<TemplateContent> listByTemplateIdAndToken(int templateId, String token) {
        TemplateContent content = new TemplateContent();
        content.setTmpId(templateId);
        content.setProToken(token);
        return super.queryForList("listByTemplateIdAndToken", content);
    }

    @Override
    public List<TemplateContent> listByTemplateId(int templateId) {
        return super.queryForList("listByTemplateId", templateId);
    }

    @Override
    public List<TemplateContent> listByTokenAndRoleName(String token, String roleName) {
        TemplateContent content = new TemplateContent();
        content.setProToken(token);
        content.setRoleName(roleName);
        return super.queryForList("listByTokenAndRoleName", content);
    }

    @Override
    public void del(int id) {
        super.delete("del", id);
    }

}
