package com.uuauth.ams.domain.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 性别
 * 
 * @author root
 * 
 */
public class Sex {
	public final static int						ALL		= 0;								// 全部
	public final static int						MALE	= 1;								// 男
	public final static int						FEMALE	= 2;								// 女
	public final static Map<Integer, String>	list	= new HashMap<Integer, String>();
	
	static {
		list.put(MALE, "男");
		list.put(FEMALE, "女");
	}
}
