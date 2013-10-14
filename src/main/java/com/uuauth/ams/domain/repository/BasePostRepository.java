/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    BasePostRepository.java
 * Creator:     joe.zhao
 * Create-Date: 下午03:27:44
 */
package com.uuauth.ams.domain.repository;

import java.util.List;
import java.util.Map;

import com.uuauth.ams.domain.entity.BasePost;

/**
 * TODO
 * 
 * @author joe.zhao
 * @version $Id: BasePostRepository, v 0.1 2012-5-8 下午03:27:44 Exp $
 */
public interface BasePostRepository {
	
	void init();
	
	List<BasePost> list();
	
	void modifyTemplate(int templateId);
	
	BasePost getOne(int id);
	
	List<BasePost> listByTemplateId(int temId);
	
	List<BasePost> getByDepId(int depid);
	
	public Map<Integer, String> getPostsByDepId(int depid);
	
}
