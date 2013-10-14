package com.uuauth.ams.domain.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证类型
 * 
 * @author root
 * 
 */
public abstract class AuthType {
	
	public final static int						ALL				= 0;								// 全部
	public final static int						NAME_PWD		= 1;								// 用户名-密码
	public final static int						USBKEY_PWD		= 2;								// USB-密码
	public final static int						PCKEY_USBKEY	= 3;								// PC-USB
	public final static Map<Integer, String>	list			= new HashMap<Integer, String>();
	
	static {
		list.put(NAME_PWD, "用户名-密码");
		list.put(USBKEY_PWD, "USB-密码");
		list.put(PCKEY_USBKEY, "PC-USB");
	}
	
}
