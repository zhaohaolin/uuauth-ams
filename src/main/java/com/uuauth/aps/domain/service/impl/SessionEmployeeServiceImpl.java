/*
 * CopyRight (c) 2005-2012 SHOWWA Co, Ltd. All rights reserved.
 * Filename:    SessionEmployeeServiceImpl.java
 * Creator:     joe.zhao
 * Create-Date: 下午11:39:11
 */
package com.uuauth.aps.domain.service.impl;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.uuauth.aps.domain.service.SessionEmployeeService;

/**
 * TODO
 * 
 * @author joe.zhao
 * @version $Id: SessionEmployeeServiceImpl, v 0.1 2012-6-3 下午11:39:11 Exp $
 */
@Service
public class SessionEmployeeServiceImpl implements SessionEmployeeService {
	
	@Override
	public <T> boolean add(String key, T value) {
		cache.put(new Element(key, value));
		if (logger.isDebugEnabled()) {
			logger.debug("put key=[{}], vlaue=[{}] to ehcache=[{}].",
					new Object[] { key, value, cache.getName() });
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(String key) {
		Element e = cache.get(key);
		if (null != e) {
			T value = (T) e.getValue();
			if (logger.isDebugEnabled()) {
				logger.debug("get key=[{}], vlaue=[{}] from ehcache=[{}].",
						new Object[] { key, value, cache.getName() });
			}
			return value;
		}
		return null;
	}
	
	/**
	 * 根据key删除缓存
	 * 
	 * @param key
	 * @return
	 */
	public boolean del(String key) {
		return cache.remove(key);
	}
	
	public void setCache(Ehcache cache) {
		this.cache = cache;
	}
	
	private Ehcache	cache;
	private Logger	logger	= LoggerFactory.getLogger(getClass());
	
}
