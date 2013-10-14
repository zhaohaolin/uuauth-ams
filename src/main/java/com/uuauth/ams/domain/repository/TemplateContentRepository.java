/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    TemplateContentRepository.java
 * Creator:     joe.zhao
 * Create-Date: 下午01:29:59
 */
package com.uuauth.ams.domain.repository;

import java.util.List;

import com.uuauth.ams.domain.entity.TemplateContent;

/**
 * TODO
 * @author joe.zhao
 * @version $Id: TemplateContentRepository, v 0.1 2012-5-9 下午01:29:59 Exp $
 */
public interface TemplateContentRepository {

    void add(TemplateContent content);

    boolean checkExist(TemplateContent content);

    List<TemplateContent> listByTemplateIdAndToken(int templateId, String token);

    List<TemplateContent> listByTemplateId(int templateId);

    List<TemplateContent> listByTokenAndRoleName(String token, String roleName);

    void del(int id);

}
