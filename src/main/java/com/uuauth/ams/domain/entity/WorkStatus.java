package com.uuauth.ams.domain.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 使用情况
 * 
 * @author root
 * 
 */
public abstract class WorkStatus {
	public final static int						WORKING	= 1;								// 可用
	public final static int						WORKED	= 2;								// 停用
	public final static Map<Integer, String>	list	= new HashMap<Integer, String>();
	
	static {
		list.put(WORKING, "可用");
		list.put(WORKED, "停用");
	}
}
