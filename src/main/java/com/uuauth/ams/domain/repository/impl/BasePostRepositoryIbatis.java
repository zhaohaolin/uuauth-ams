/*
 * CopyRight (c) 2005-2012 GLOBE Co, Ltd. All rights reserved.
 * Filename:    BasePostRepositoryIbatis.java
 * Creator:     joe.zhao
 * Create-Date: 下午03:30:16
 */
package com.uuauth.ams.domain.repository.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.avaje.ebean.Ebean;
import com.uuauth.ams.domain.entity.BasePost;
import com.uuauth.ams.domain.repository.BasePostRepository;

/**
 * TODO
 * 
 * @author joe.zhao
 * @version $Id: BasePostRepositoryIbatis, v 0.1 2012-5-8 下午03:30:16 Exp $
 */
public class BasePostRepositoryIbatis implements BasePostRepository {
	
	public void init() {
		BasePost.list.clear();
		List<BasePost> list = list();
		if (null != list && !list.isEmpty()) {
			for (BasePost post : list) {
				BasePost.list.put(post.getId(), post.getName());
			}
		}
	}
	
	@Override
	public List<BasePost> list() {
		return Ebean.find(BasePost.class).findList();
	}
	
	@Override
	public void modifyTemplate(int templateId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("oldRoleTmpId", templateId);
		param.put("newRoleTmpId", 0);
		// TODO
	}
	
	@Override
	public BasePost getOne(int id) {
		return Ebean.find(BasePost.class, id);
	}
	
	@Override
	public List<BasePost> listByTemplateId(int tempId) {
		return Ebean.find(BasePost.class).where().eq("temp_id", tempId)
				.findList();
	}
	
	@Override
	public List<BasePost> getByDepId(int depid) {
		return Ebean.find(BasePost.class).where().eq("depid", depid).findList();
	}
	
	@Override
	public Map<Integer, String> getPostsByDepId(int depid) {
		Map<Integer, String> map = new HashMap<Integer, String>();
		List<BasePost> list = list();
		for (BasePost post : list) {
			if (post.getDepId().intValue() == depid)
				map.put(post.getId(), post.getName());
		}
		return map;
	}
	
}
